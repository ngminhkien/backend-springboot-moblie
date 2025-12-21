package com.minhkien.mobile.controller;


import com.minhkien.mobile.dto.response.SeatConfigResponse;
import com.minhkien.mobile.service.SeatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SeatController {

    SeatService seatService;

    @GetMapping()
    public ResponseEntity<List<SeatConfigResponse>> getConfigs() {
        return ResponseEntity.ok(seatService.getAllSeatConfigs());
    }

    @GetMapping("/booked/{maSuatChieu}")
    public ResponseEntity<List<String>> getBookedSeats(@PathVariable Long maSuatChieu) {
        return ResponseEntity.ok(seatService.getBookedSeats(maSuatChieu));
    }
}
