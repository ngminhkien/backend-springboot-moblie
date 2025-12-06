package com.minhkien.mobile.dto.request;

import com.minhkien.mobile.enums.MovieStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //tạo đối tượng từ bất kì field nào
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmCreationRequest {

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
    //MovieStatus trangThai;

    Set<String> genresId;
}
