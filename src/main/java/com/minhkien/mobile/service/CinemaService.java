package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.CinemaResponse;
import com.minhkien.mobile.entity.Cinema;
import com.minhkien.mobile.mapper.CinemaMapper;
import com.minhkien.mobile.responsitory.CinemaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaService {

    CinemaRepository cinemaRepository;
    CinemaMapper cinemaMapper;

    public List<CinemaResponse> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemaMapper.toResponseList(cinemas);
    }
}
