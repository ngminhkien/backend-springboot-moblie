package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.showTime.AutoCreateShowtimeRequest;
import com.minhkien.mobile.dto.request.showTime.ShowtimeRequest;
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
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private static final int CLEANING_TIME_MINUTES = 15;

    //hàm chuẩn hóa cả 3 th: maPhim, maRap, cả2
    public List<ShowtimeResponse> searchShowtimes(String maPhim, Long maRap) {
        if (maPhim != null && maPhim.trim().isEmpty()) {
            maPhim = null;
        }

        LocalDateTime now = LocalDateTime.now();

        // Gọi hàm Query Dynamic
        List<Showtime> list = showtimeRepo.findShowtimesDynamic(maPhim, maRap, now);

        return mapper.toResponseList(list);
    }

    //hàm nhận vào mã Phim trả ra phim suất chiếu của phim đó (từ now)
//    public List<ShowtimeResponse> getShowtimesByFilm(String maPhim) {
//
//        LocalDateTime now = LocalDateTime.now();
//
//        List<Showtime> list = showtimeRepo
//                .findByFilm_MaPhimAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(maPhim, now);
//
//        return mapper.toResponseList(list);
//    }

    //hàm nhận vào mã Rạp trả ra phim suất chiếu của rạp đó (từ now)
//    public List<ShowtimeResponse> getShowtimesByCinema(Long maRap) {
//        LocalDateTime now = LocalDateTime.now();
//
//        List<Showtime> list = showtimeRepo
//                .findByCinema_MaRapAndTgBatDauGreaterThanEqualOrderByTgBatDauAsc(maRap, now);
//
//        return mapper.toResponseList(list);
//    }

    public ShowtimeResponse createShowtime(ShowtimeRequest req) {

        Film film = filmRepository.findById(req.getMaPhim())
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));
        Cinema cinema = cinemaRepository.findById(req.getMaRap())
                .orElseThrow(() -> new RuntimeException("Rạp không tồn tại"));
        Room room = roomRepo.findById(req.getMaPhong())
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));

        LocalDateTime start = req.getTgBatDau();
        LocalDateTime end = start.plusMinutes(film.getThoiLuong());

        LocalDateTime freeTime = end.plusMinutes(CLEANING_TIME_MINUTES);

        List<Showtime> overlaps = showtimeRepo.checkOverlap(room.getMaPhong(), start, freeTime);
        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Phòng chiếu bị trùng lịch với suất chiếu khác! (Vui lòng cách ít nhất 15p dọn dẹp)");
        }

        Showtime showtime = Showtime.builder()
                .film(film)
                .cinema(cinema)
                .room(room)
                .tgBatDau(start)
                .tgKetThuc(end)
                .build();

        Showtime saved = showtimeRepo.save(showtime);
        return mapper.toResponse(saved);
    }


    public ShowtimeResponse updateShowtime(Long id, ShowtimeRequest req) {
        Showtime current = showtimeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy suất chiếu"));

        // Lấy giá trị hiện tại làm mặc định
        Film effectiveFilm = current.getFilm();
        Cinema effectiveCinema = current.getCinema();
        Room effectiveRoom = current.getRoom();
        LocalDateTime effectiveStart = current.getTgBatDau();

        // Nếu có thay đổi phim -> cập nhật effectiveFilm
        if (req.getMaPhim() != null) {
            effectiveFilm = filmRepository.findById(req.getMaPhim())
                    .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));
            current.setFilm(effectiveFilm);
        }

        // Nếu có thay đổi rạp -> cập nhật effectiveCinema
        if (req.getMaRap() != null) {
            effectiveCinema = cinemaRepository.findById(req.getMaRap())
                    .orElseThrow(() -> new RuntimeException("Rạp không tồn tại"));
            current.setCinema(effectiveCinema);
        }

        // Nếu có thay đổi phòng -> cập nhật effectiveRoom
        if (req.getMaPhong() != null) {
            effectiveRoom = roomRepo.findById(req.getMaPhong())
                    .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
            current.setRoom(effectiveRoom);
        }

        // Nếu có thay đổi thời gian bắt đầu -> cập nhật effectiveStart
        if (req.getTgBatDau() != null) {
            effectiveStart = req.getTgBatDau();
            current.setTgBatDau(effectiveStart);
        }

        // Tính lại tgKetThuc theo phim (mới hoặc cũ)
        long duration = effectiveFilm.getThoiLuong();
        LocalDateTime effectiveEnd = effectiveStart.plusMinutes(duration);
        current.setTgKetThuc(effectiveEnd);

        // Kiểm tra trùng lịch trên phòng (dù chỉ thay đổi phòng hoặc thời gian)
        LocalDateTime freeTime = effectiveEnd.plusMinutes(CLEANING_TIME_MINUTES);
        List<Showtime> overlaps = showtimeRepo.checkOverlap(effectiveRoom.getMaPhong(), effectiveStart, freeTime);
        boolean conflict = overlaps.stream()
                .anyMatch(s -> !id.equals(s.getId()));
        if (conflict) {
            throw new RuntimeException("Phòng chiếu bị trùng lịch với suất chiếu khác! (Vui lòng cách ít nhất 15p dọn dẹp)");
        }

        return mapper.toResponse(showtimeRepo.save(current));
    }


    public void deleteShowtime(Long id) {
        // Check xem có vé nào chưa, nếu có thì không cho xóa cứng mà chỉ xóa mềm (isActive = false)
        showtimeRepo.deleteById(id);
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepo.findAll();
    }

    @Transactional
    public List<ShowtimeResponse> autoGenerateShowtimesForCinema(AutoCreateShowtimeRequest req) {
        // 1. Lấy danh sách Phim & Rạp
        List<Film> films = filmRepository.findAllById(req.getDsMaPhim());
        if (films.isEmpty()) throw new RuntimeException("Danh sách phim trống");

        Cinema cinema = cinemaRepository.findById(req.getMaRap())
                .orElseThrow(() -> new RuntimeException("Rạp không tồn tại"));

        // 2. Lấy tất cả phòng của rạp đó (Giả sử bạn có hàm này trong Repo)
        List<Room> rooms = roomRepo.findByCinema_MaRap(req.getMaRap());
        if (rooms.isEmpty()) throw new RuntimeException("Rạp này chưa có phòng nào");

        List<Showtime> allGeneratedShowtimes = new ArrayList<>();

        // 3. DUYỆT QUA TỪNG PHÒNG ĐỂ XẾP LỊCH
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            // --- THUẬT TOÁN XOAY VÒNG PHIM (Smart Rotate) ---
            // Phòng 1 ưu tiên phim 1, Phòng 2 ưu tiên phim 2...
            // Giúp khách đến giờ nào cũng có phim khác nhau để chọn.
            List<Film> rotatedFilms = new ArrayList<>(films);
            // Dịch chuyển danh sách phim dựa theo số thứ tự phòng
            java.util.Collections.rotate(rotatedFilms, -i);

            // Gọi logic xếp lịch cho 1 phòng (Reuse logic cũ nhưng áp dụng cho rotatedFilms)
            List<Showtime> roomShowtimes = generateForSingleRoom(
                    room,
                    rotatedFilms,
                    req.getNgayChieu(),
                    req.getGioMoCua(),
                    req.getGioDongCua(),
                    req.getThoiGianNghi()
            );

            allGeneratedShowtimes.addAll(roomShowtimes);
        }

        return mapper.toResponseList(allGeneratedShowtimes);
    }

    // Tách logic xếp lịch 1 phòng ra hàm riêng (private) để tái sử dụng
    private List<Showtime> generateForSingleRoom(Room room, List<Film> films, LocalDate date, LocalTime open, LocalTime close, int breakTime) {
        List<Showtime> result = new ArrayList<>();
        LocalDateTime currentStartTime = LocalDateTime.of(date, open);
        LocalDateTime limitTime = LocalDateTime.of(date, close);
        int filmIndex = 0;

        while (true) {
            Film currentFilm = films.get(filmIndex % films.size());
            LocalDateTime currentEndTime = currentStartTime.plusMinutes(currentFilm.getThoiLuong());

            if (currentEndTime.isAfter(limitTime)) break;

            LocalDateTime timeWithCleaning = currentEndTime.plusMinutes(breakTime);

            // Check trùng
            // Room primary key is `maPhong` so use getMaPhong()
            List<Showtime> overlaps = showtimeRepo.checkOverlap(room.getMaPhong(), currentStartTime, timeWithCleaning);

            if (overlaps.isEmpty()) {
                Showtime showtime = Showtime.builder()
                        .film(currentFilm)
                        .cinema(room.getCinema())
                        .room(room)
                        .tgBatDau(currentStartTime)
                        .tgKetThuc(currentEndTime)
                        .build();

                result.add(showtimeRepo.save(showtime));
                currentStartTime = timeWithCleaning;
                filmIndex++;
            } else {
                // Logic nhảy cóc (như bài trước)
                LocalDateTime maxEndTime = overlaps.stream()
                        .map(Showtime::getTgKetThuc)
                        .max(LocalDateTime::compareTo)
                        .orElse(currentStartTime.plusMinutes(30));
                currentStartTime = maxEndTime.plusMinutes(breakTime);
            }
        }
        return result;
    }

    public List<ShowtimeResponse> getShowtimesForCinema(Long maRap, LocalDate date) {
        if (maRap == null) {
            throw new RuntimeException("maRap là bắt buộc");
        }

        List<Showtime> showtimes;
        if (date != null) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);
            showtimes = showtimeRepo.findByCinemaAndDateRange(maRap, startOfDay, endOfDay);
        } else {
            showtimes = showtimeRepo.findByCinema_MaRapOrderByTgBatDauAsc(maRap);
        }

        return mapper.toResponseList(showtimes);
    }
}
