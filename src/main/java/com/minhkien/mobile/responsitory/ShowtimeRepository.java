package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.dto.response.ShowtimeResponse;
import com.minhkien.mobile.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findByFilm_MaPhimAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(String maPhim, LocalDateTime now);

    List<Showtime> findByCinema_MaRapAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(
            Long maRap,
            LocalDateTime tgBatDau
    );
}
