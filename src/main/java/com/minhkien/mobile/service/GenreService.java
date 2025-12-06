package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.GenreResponse;
import com.minhkien.mobile.mapper.GenreMapper;
import com.minhkien.mobile.responsitory.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GenreService {

    GenreRepository genreRepo;
    GenreMapper mapper;

    public List<GenreResponse> getAllGenres() {
        return mapper.toGenreResponseList(genreRepo.findAll());
    }
}
