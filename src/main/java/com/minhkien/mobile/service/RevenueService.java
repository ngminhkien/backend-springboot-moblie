package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.RevenueReponse.RevenueByCinemaResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.RevenueByDayResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.RevenueSummaryResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.TopMovieRevenueResponse;
import com.minhkien.mobile.responsitory.InvoiceDetailRepository;
import com.minhkien.mobile.responsitory.InvoiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RevenueService {

    InvoiceRepository invoiceRepo;
    InvoiceDetailRepository invoiceDetailRepo;

    public List<RevenueByCinemaResponse> getRevenueByCinema() {
        List<Object[]> rows = invoiceRepo.revenueByCinema();
        return rows.stream()
                .map(r -> RevenueByCinemaResponse.builder()
                        .cinemaId((Long) r[0])
                        .cinema((String) r[1])
                        .revenue(Math.round((Double) r[2]))
                        .build())
                .toList();
    }

    public List<TopMovieRevenueResponse> getTopMovies(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be > 0");
        }

        List<Object[]> rows = invoiceRepo.topMoviesByRevenueAndTickets();
        List<TopMovieRevenueResponse> mapped = rows.stream()
                .map(r -> TopMovieRevenueResponse.builder()
                        .movieId((String) r[0])
                        .movieName((String) r[1])
                        .tickets(((Number) r[2]).longValue())
                        .revenue(Math.round((Double) r[3]))
                        .build())
                .sorted(Comparator.comparingLong(TopMovieRevenueResponse::getTickets).reversed()
                        .thenComparingLong(TopMovieRevenueResponse::getRevenue).reversed())
                .limit(limit)
                .toList();
        return mapped;
    }

    public List<RevenueByDayResponse> getRevenueByDay(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("from/to must not be null");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must be before or equal to to");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        List<Object[]> rows = invoiceRepo.sumRevenueByDayBetween(start, end);
        Map<LocalDate, Long> revenueMap = rows.stream()
                .collect(Collectors.toMap(
                        r -> ((java.sql.Date) r[0]).toLocalDate(),
                        r -> Math.round((Double) r[1])));

        List<RevenueByDayResponse> result = new ArrayList<>();
        LocalDate cursor = from;
        while (!cursor.isAfter(to)) {
            long revenue = revenueMap.getOrDefault(cursor, 0L);
            result.add(RevenueByDayResponse.builder()
                    .date(cursor)
                    .revenue(revenue)
                    .build());
            cursor = cursor.plusDays(1);
        }
        return result;
    }

    public RevenueSummaryResponse getRevenueSummary() {
        LocalDate today = LocalDate.now();

        // Khoảng ngày hôm nay
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        // Khoảng đầu tháng - cuối tháng
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDateTime startOfMonth = firstDayOfMonth.atStartOfDay();
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(LocalTime.MAX);

        // Khoảng đầu năm - cuối năm
        LocalDate firstDayOfYear = today.withDayOfYear(1);
        LocalDateTime startOfYear = firstDayOfYear.atStartOfDay();
        LocalDateTime endOfYear = today.withDayOfYear(today.lengthOfYear()).atTime(LocalTime.MAX);

        double todayRevenue = invoiceRepo.sumRevenueBetween(startOfToday, endOfToday);
        double monthRevenue = invoiceRepo.sumRevenueBetween(startOfMonth, endOfMonth);
        double yearRevenue = invoiceRepo.sumRevenueBetween(startOfYear, endOfYear);

        // Tổng số vé: đếm số InvoiceDetail (ghế) đã bán
        long totalTickets = invoiceDetailRepo.countByHoaDon_TrangThaiTrue();

        return RevenueSummaryResponse.builder()
                .todayRevenue(Math.round(todayRevenue))
                .monthRevenue(Math.round(monthRevenue))
                .yearRevenue(Math.round(yearRevenue))
                .totalTickets(totalTickets)
                .build();
    }
}
