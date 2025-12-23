package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.showTime.AutoCreateShowtimeRequest;
import com.minhkien.mobile.dto.request.showTime.ShowtimeRequest;
import com.minhkien.mobile.dto.response.ShowtimeResponse;
import com.minhkien.mobile.mapper.ShowtimeMapper;
import com.minhkien.mobile.service.ShowtimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    // Lấy suất chiếu theo rạp, lọc theo ngày (admin timeline)
    @GetMapping("/admin")
    public ResponseEntity<List<ShowtimeResponse>> getShowtimesByCinema(
            @RequestParam Long maRap,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(showtimeService.getShowtimesForCinema(maRap, date));
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
    public ResponseEntity<ShowtimeResponse> create(@RequestBody ShowtimeRequest req) {
        return ResponseEntity.ok(showtimeService.createShowtime(req));
    }

    //cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<ShowtimeResponse> update(@PathVariable Long id, @RequestBody ShowtimeRequest req) {
        return ResponseEntity.ok(showtimeService.updateShowtime(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok("Xóa suất chiếu thành công");
    }

    @GetMapping
    public List<ShowtimeResponse> getAllShowtimes() {
        return showtimeMapper.toResponseList(showtimeService.getAllShowtimes());
    }

    @PostMapping("/auto-generate/cinema")
    public ResponseEntity<List<ShowtimeResponse>> autoGenerateCinema(@RequestBody AutoCreateShowtimeRequest req) {
        // Set default values...
        if (req.getGioMoCua() == null) req.setGioMoCua(LocalTime.of(8, 0));
        if (req.getGioDongCua() == null) req.setGioDongCua(LocalTime.of(23, 0));
        if (req.getThoiGianNghi() <= 0) req.setThoiGianNghi(15);

        return ResponseEntity.ok(showtimeService.autoGenerateShowtimesForCinema(req));
    }
}
