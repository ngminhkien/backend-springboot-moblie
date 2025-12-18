package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.ShowtimeRequest;
import com.minhkien.mobile.dto.response.ShowtimeResponse;
import com.minhkien.mobile.entity.Showtime;
import com.minhkien.mobile.mapper.ShowtimeMapper;
import com.minhkien.mobile.service.ShowtimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShowtimeController {

    ShowtimeService showtimeService;
    ShowtimeMapper showtimeMapper;

    @GetMapping("/search")
    public ResponseEntity<List<ShowtimeResponse>> getShowtimes(
            @RequestParam(required = false) String maPhim,
            @RequestParam(required = false) Long maRap) {

        return ResponseEntity.ok(showtimeService.searchShowtimes(maPhim, maRap));
    }

//    @GetMapping("/cinema/{maRap}")
//    public ResponseEntity<List<ShowtimeResponse>> getShowtimesByCinema(@PathVariable Long maRap) {
//        return ResponseEntity.ok(showtimeService.getShowtimesByCinema(maRap));
//    }

//    @GetMapping("/film/{maPhim}")
//    public ResponseEntity<List<ShowtimeResponse>> getShowtimesByFilm(@PathVariable String maPhim) {
//        List<ShowtimeResponse> list = showtimeService.getShowtimesByFilm(maPhim);
//        return ResponseEntity.ok(list);
//    }

    @PostMapping
    public ShowtimeResponse createShowtime(@RequestBody ShowtimeRequest req) {
        Showtime showtime = showtimeService.createShowtime(req);
        return showtimeMapper.toResponse(showtime);
    }

    @GetMapping
    public List<ShowtimeResponse> getAllShowtimes() {
        return showtimeMapper.toResponseList(showtimeService.getAllShowtimes());
    }
}
