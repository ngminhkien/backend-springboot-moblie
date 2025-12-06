package com.minhkien.mobile.entity;

import com.minhkien.mobile.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "film")
public class Film {

    @Id
    @Column(name = "ma_phim")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String maPhim;

    @Column(name = "ten_phim", nullable = false)
    private String tenPhim;

    @Column(name = "ngay_cong_chieu")
    private LocalDate ngayCongChieu;

    @Column(name = "ngay_kt_chieu")
    private LocalDate ngayKTChieu;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "dao_dien")
    private String daoDien; // Thêm trường Đạo diễn (String)

    @Column(name = "dien_vien", columnDefinition = "TEXT")
    private String dienVien; // Thêm trường Diễn viên (TEXT)

    @Column(name = "thoi_luong")
    private Integer thoiLuong;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "anh_poster_doc")
    private String anhPosterDoc;

    @Column(name = "anh_poster_ngang")
    private String anhPosterNgang;

    @Column(name = "do_tuoi")
    private Integer doTuoi;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private MovieStatus trangThai;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "phim_genre", // Tên bảng trung gian (Junction Table)
            joinColumns = @JoinColumn(name = "ma_phim"), // Khóa ngoại từ bảng Phim
            inverseJoinColumns = @JoinColumn(name = "ma_genre") // Khóa ngoại từ bảng Genre
    )
    private Set<Genre> genres = new HashSet<>();
}
