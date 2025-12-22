package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.film.FilmCreationRequest;
import com.minhkien.mobile.dto.request.film.FilmUpdateRequest;
import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Genre;
import com.minhkien.mobile.entity.InvoiceDetail;
import com.minhkien.mobile.enums.MovieStatus;
import com.minhkien.mobile.mapper.FilmMapper;
import com.minhkien.mobile.responsitory.FilmRepository;
import com.minhkien.mobile.responsitory.GenreRepository;
import com.minhkien.mobile.responsitory.InvoiceDetailRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FilmService {

    GenreRepository genreRepo;
    FilmMapper filmMapper;
    FilmRepository filmRepository;
    InvoiceDetailRepository invoiceDetailRepository;

    @Transactional
    public FilmResponse update(String filmId, FilmUpdateRequest request) {
        Film existingFilm = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại: " + filmId));
        filmMapper.updateFilmFromRequest(request, existingFilm);
        if (request.getGenresId() != null) {
            List<Genre> newGenres = genreRepo.findAllById(request.getGenresId());
            existingFilm.setGenres(new HashSet<>(newGenres));
        }
        if (request.getNgayCongChieu() != null || request.getNgayKTChieu() != null) {
            if (request.getTrangThai() == null) {
                existingFilm.setTrangThai(calculateStatus(existingFilm));
            }
        }

        return filmMapper.toResponse(filmRepository.save(existingFilm));
    }

    @Transactional
    public void delete(String filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại: " + filmId));

        film.setTrangThai(MovieStatus.ENDED);
        filmRepository.save(film);
    }

    public FilmResponse create(FilmCreationRequest request) {

        Film movie = Film.builder()
                .tenPhim(request.getTenPhim())
                .moTa(request.getMoTa())
                .daoDien(request.getDaoDien())
                .dienVien(request.getDienVien())
                .anhPosterNgang(request.getAnhPosterNgang())
                .anhPosterDoc(request.getAnhPosterDoc())
                .trailerUrl(request.getTrailerUrl())
                .doTuoi(request.getDoTuoi())
                .ngayCongChieu(request.getNgayCongChieu())
                .ngayKTChieu(request.getNgayKTChieu())
                .thoiLuong(request.getThoiLuong())
                .trangThai(MovieStatus.UPCOMING)
                .build();

        Set<Genre> genres = genreRepo.findAllById(request.getGenresId())
                .stream()
                .collect(Collectors.toSet());

        movie.setGenres(genres);

        return filmMapper.toFilmResponse(filmRepository.save(movie));
    }

    //hàm lấy film hot đơn giản nhưng không hiệu quả nếu invoice detail quá lớn
    // ======= 1. Phim đang chiếu =======
    public List<FilmResponse> getNowShowing() {
        return filmRepository.findByTrangThai(MovieStatus.NOW_SHOWING)
                .stream()
                .map(filmMapper::toFilmResponse)
                .collect(Collectors.toList());
    }

    // ======= 2. Phim sắp chiếu =======
    public List<FilmResponse> getUpcoming() {
        return filmRepository.findByTrangThai(MovieStatus.UPCOMING)
                .stream()
                .map(filmMapper::toFilmResponse)
                .collect(Collectors.toList());
    }

    // ======= 4. Phim hot (STREAM, không JPQL) =======
    public List<FilmResponse> getHotFilms() {

        // Lấy toàn bộ invoice detail (mỗi detail = 1 ghế = 1 người xem)
        List<InvoiceDetail> details = invoiceDetailRepository.findAll();

        // Gom nhóm theo Film rồi đếm số ghế (view count)
        Map<Film, Long> countByFilm = details.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getHoaDon().getShowtime().getFilm(),
                        Collectors.counting()
                ));

        // Sắp giảm dần theo số lượt xem
        List<Film> sorted = countByFilm.entrySet().stream()
                .sorted(Map.Entry.<Film, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Convert sang response bằng MapStruct
        return sorted.stream()
                .map(filmMapper::toFilmResponse)
                .collect(Collectors.toList());
    }

    //tìm kiếm
    public Page<FilmResponse> searchFilms(
            String keyword,
            String genre,
            Integer minAge,
            int page,
            int size,
            String sort
    ) {
        // Xử lý sort: "tenPhim,asc" / "ngayCongChieu,desc"
        String[] sortArr = sort.split(",");
        String sortField = sortArr[0];
        Sort.Direction direction = sortArr.length > 1 && sortArr[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<Film> filmPage = filmRepository.searchFilms(
                keyword,
                genre,
                minAge,
                pageable
        );

        return filmPage.map(filmMapper::toFilmResponse);
    }

    //getALL
    public java.util.List<FilmResponse> getAll() {
        return filmRepository.findAll()
                .stream()
                .map(filmMapper::toFilmResponse)
                .toList();
    }

    //update Status
    public MovieStatus calculateStatus(Film movie) {
        LocalDate today = LocalDate.now();

        if (movie.getNgayCongChieu().isAfter(today)) {
            return MovieStatus.UPCOMING;
        }

        if ((movie.getNgayCongChieu().isEqual(today) || movie.getNgayCongChieu().isBefore(today))
                && movie.getNgayKTChieu().isAfter(today)) {
            return MovieStatus.NOW_SHOWING;
        }

        return MovieStatus.ENDED;
    }

}
