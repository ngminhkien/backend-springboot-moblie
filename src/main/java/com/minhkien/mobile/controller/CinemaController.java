package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.response.CinemaResponse;
import com.minhkien.mobile.dto.response.RoomResponse;
import com.minhkien.mobile.service.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cinemas")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CinemaController {

    CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<CinemaResponse>> getAllCinemas() {
        return ResponseEntity.ok(cinemaService.getAllCinemas());
    }

    // Lấy danh sách phòng của một rạp
    @GetMapping("/{maRap}/rooms")
    public ResponseEntity<List<RoomResponse>> getRoomsByCinema(@PathVariable Long maRap) {
        return ResponseEntity.ok(cinemaService.getRoomsByCinema(maRap));
    }
}
