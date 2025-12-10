package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.ShowtimeRequest;
import com.minhkien.mobile.dto.response.ShowtimeResponse;
import com.minhkien.mobile.entity.Cinema;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Room;
import com.minhkien.mobile.entity.Showtime;
import com.minhkien.mobile.mapper.ShowtimeMapper;
import com.minhkien.mobile.responsitory.CinemaRepository;
import com.minhkien.mobile.responsitory.FilmRepository;
import com.minhkien.mobile.responsitory.RoomRepository;
import com.minhkien.mobile.responsitory.ShowtimeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShowtimeService {

    ShowtimeRepository showtimeRepo;
    FilmRepository filmRepository;
    CinemaRepository  cinemaRepository;
    RoomRepository roomRepo;
    ShowtimeMapper  mapper;

    //hàm nhận vào mã Phim trả ra phim suất chiếu của phim đó (từ now)
    public List<ShowtimeResponse> getShowtimesByFilm(String maPhim) {

        LocalDateTime now = LocalDateTime.now();

        List<Showtime> list = showtimeRepo
                .findByFilm_MaPhimAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(maPhim, now);

        return mapper.toResponseList(list);
    }

    //hàm nhận vào mã Rạp trả ra phim suất chiếu của rạp đó (từ now)
    public List<ShowtimeResponse> getShowtimesByCinema(Long maRap) {
        LocalDateTime now = LocalDateTime.now();

        List<Showtime> list = showtimeRepo
                .findByCinema_MaRapAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(maRap, now);

        return mapper.toResponseList(list);
    }

    public Showtime createShowtime(ShowtimeRequest req) {
        Film film = filmRepository.findById(req.getMaPhim())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Cinema cinema = cinemaRepository.findById(req.getMaRap())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));
        Room room = roomRepo.findById(req.getMaPhong())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Showtime showtime = new Showtime();
        showtime.setFilm(film);
        showtime.setCinema(cinema);
        showtime.setRoom(room);
        showtime.setTgBatDau(req.getTgBatDau());

        // Tính endTime dựa trên duration của phim
        LocalDateTime end = req.getTgBatDau().plusMinutes(film.getThoiLuong());
        showtime.setTgKetThuc(end);

        return showtimeRepo.save(showtime);
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepo.findAll();
    }
}
