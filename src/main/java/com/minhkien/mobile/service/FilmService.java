package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.FilmCreationRequest;
import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Genre;
import com.minhkien.mobile.enums.MovieStatus;
import com.minhkien.mobile.mapper.FilmMapper;
import com.minhkien.mobile.responsitory.FilmRepository;
import com.minhkien.mobile.responsitory.GenreRepository;
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
import java.util.List;
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

    //tạo
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
