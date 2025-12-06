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
