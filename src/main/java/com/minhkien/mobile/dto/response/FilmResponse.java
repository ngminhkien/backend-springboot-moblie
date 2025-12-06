package com.minhkien.mobile.dto.response;

import com.minhkien.mobile.enums.MovieStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmResponse {

    String tenPhim;
    LocalDate ngayCongChieu;
    LocalDate ngayKTChieu;
    String moTa;
    String daoDien;
    String dienVien;
    Integer thoiLuong;
    String trailerUrl;
    String anhPosterDoc;
    String anhPosterNgang;
    Integer doTuoi;
    MovieStatus trangThai;

    Set<String> genres;
}
