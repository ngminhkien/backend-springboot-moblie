package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.CinemaResponse;
import com.minhkien.mobile.dto.response.RoomResponse;
import com.minhkien.mobile.entity.Cinema;
import com.minhkien.mobile.entity.Room;
import com.minhkien.mobile.mapper.CinemaMapper;
import com.minhkien.mobile.mapper.RoomMapper;
import com.minhkien.mobile.responsitory.CinemaRepository;
import com.minhkien.mobile.responsitory.RoomRepository;
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
    RoomRepository roomRepository;
    CinemaMapper cinemaMapper;
    RoomMapper roomMapper;

    public List<CinemaResponse> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemaMapper.toResponseList(cinemas);
    }

    // Lấy danh sách phòng theo mã rạp
    public List<RoomResponse> getRoomsByCinema(Long maRap) {
        List<Room> rooms = roomRepository.findByCinema_MaRap(maRap);
        return roomMapper.toResponseList(rooms);
    }
}
