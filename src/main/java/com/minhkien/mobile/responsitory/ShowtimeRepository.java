package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

//    List<Showtime> findByFilm_MaPhimAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(String maPhim, LocalDateTime now);
//
//    List<Showtime> findByCinema_MaRapAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(
//            Long maRap,
//            LocalDateTime tgBatDau
//    );

    @Query("SELECT s FROM Showtime s WHERE " +
            "s.tgBatDau >= :now " +
            "AND (:maPhim IS NULL OR s.film.maPhim = :maPhim) " +
            "AND (:maRap IS NULL OR s.cinema.maRap = :maRap) " +
            "ORDER BY s.tgBatDau ASC")
    List<Showtime> findShowtimesDynamic(
            @Param("maPhim") String maPhim,
            @Param("maRap") Long maRap,
            @Param("now") LocalDateTime now
    );

    @Query("SELECT s FROM Showtime s WHERE s.room.maPhong = :roomId " +
            "AND ((:newStart < s.tgKetThuc) AND (:newEnd > s.tgBatDau))")
    List<Showtime> checkOverlap(Long roomId, LocalDateTime newStart, LocalDateTime newEnd);

    // Lấy tất cả suất chiếu của một rạp, sắp xếp theo thời gian bắt đầu
    List<Showtime> findByCinema_MaRapOrderByTgBatDauAsc(Long maRap);

    // Lọc suất chiếu theo rạp và theo ngày (start <= tgBatDau < end)
    @Query("SELECT s FROM Showtime s WHERE s.cinema.maRap = :maRap " +
            "AND s.tgBatDau >= :start AND s.tgBatDau < :end " +
            "ORDER BY s.tgBatDau ASC")
    List<Showtime> findByCinemaAndDateRange(@Param("maRap") Long maRap,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);
}
