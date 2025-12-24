package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByUser_MaUserOrderByNgayTaoDesc(String maUser);

    @Query("SELECT COALESCE(SUM(i.tongTien), 0) FROM Invoice i WHERE i.trangThai = true AND i.ngayTao BETWEEN :start AND :end")
    Double sumRevenueBetween(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query("SELECT FUNCTION('DATE', i.ngayTao) as day, COALESCE(SUM(i.tongTien), 0) as revenue " +
            "FROM Invoice i " +
            "WHERE i.trangThai = true AND i.ngayTao BETWEEN :start AND :end " +
            "GROUP BY FUNCTION('DATE', i.ngayTao) " +
            "ORDER BY FUNCTION('DATE', i.ngayTao)")
    List<Object[]> sumRevenueByDayBetween(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("SELECT i.showtime.film.maPhim as filmId, i.showtime.film.tenPhim as filmName, " +
            "SUM(size(i.chiTietList)) as tickets, COALESCE(SUM(i.tongTien), 0) as revenue " +
            "FROM Invoice i " +
            "WHERE i.trangThai = true " +
            "GROUP BY i.showtime.film.maPhim, i.showtime.film.tenPhim " +
            "ORDER BY tickets DESC, revenue DESC")
    List<Object[]> topMoviesByRevenueAndTickets();

    @Query("SELECT i.showtime.cinema.maRap as cinemaId, i.showtime.cinema.tenRap as cinemaName, COALESCE(SUM(i.tongTien), 0) as revenue " +
            "FROM Invoice i " +
            "WHERE i.trangThai = true " +
            "GROUP BY i.showtime.cinema.maRap, i.showtime.cinema.tenRap " +
            "ORDER BY revenue DESC")
    List<Object[]> revenueByCinema();
}
