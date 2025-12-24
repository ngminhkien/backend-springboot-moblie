package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.response.RevenueReponse.RevenueByCinemaResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.RevenueByDayResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.RevenueSummaryResponse;
import com.minhkien.mobile.dto.response.RevenueReponse.TopMovieRevenueResponse;
import com.minhkien.mobile.service.RevenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/revenue")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RevenueController {

    RevenueService revenueService;

    @GetMapping("/summary")
    public RevenueSummaryResponse getRevenueSummary() {
        return revenueService.getRevenueSummary();
    }

    @GetMapping("/by-day")
    public List<RevenueByDayResponse> getRevenueByDay(@RequestParam("from") LocalDate from,
                                                      @RequestParam("to") LocalDate to) {
        return revenueService.getRevenueByDay(from, to);
    }

    @GetMapping("/top-movies")
    public List<TopMovieRevenueResponse> getTopMovies(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        return revenueService.getTopMovies(limit);
    }

    @GetMapping("/by-cinema")
    public List<RevenueByCinemaResponse> getRevenueByCinema() {
        return revenueService.getRevenueByCinema();
    }
}
