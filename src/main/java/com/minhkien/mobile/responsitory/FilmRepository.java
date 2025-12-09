package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.enums.MovieStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {

    @Query("""
        SELECT f FROM Film f
        LEFT JOIN f.genres g
        WHERE (:keyword IS NULL OR LOWER(f.tenPhim) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:genre IS NULL OR g.tenGenre = :genre)
          AND (:minAge IS NULL OR f.doTuoi >= :minAge)
    """)
    Page<Film> searchFilms(
            @Param("keyword") String keyword,
            @Param("genre") String genre,
            @Param("minAge") Integer minAge,
            Pageable pageable
    );

    List<Film> findByTrangThai(MovieStatus trangThai);
}
