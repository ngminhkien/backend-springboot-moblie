package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.FilmCreationRequest;
import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.enums.MovieStatus;
import com.minhkien.mobile.responsitory.FilmRepository;
import com.minhkien.mobile.service.FilmService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FilmController {

    FilmService filmService;
    FilmRepository filmRepository;

    //api tạo film mới
    @PostMapping
    public FilmResponse createMovie(@RequestBody FilmCreationRequest request) {
        return filmService.create(request);
    }

    //api tìm kiếm nhập vào params
    @GetMapping("/search")
    public ResponseEntity<Page<FilmResponse>> searchFilms(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "tenPhim,asc") String sort
    ) {
        return ResponseEntity.ok(
                filmService.searchFilms(keyword, genre, minAge, page, size, sort)
        );
    }

    @GetMapping
    public List<FilmResponse> getAllMovies() {
        return filmService.getAll();
    }

    //api cập kiểm tra và cập nhật trạng thái film tự động
    @PostMapping("/update-status")
    public void updateMovieStatuses() {
        LocalDate today = LocalDate.now();

        List<Film> movies = filmRepository.findAll();
        for (Film movie : movies) {
            MovieStatus newStatus = filmService.calculateStatus(movie);
            if (movie.getTrangThai() != newStatus) {
                movie.setTrangThai(newStatus);
                filmRepository.save(movie);
            }
        }
    }

}
