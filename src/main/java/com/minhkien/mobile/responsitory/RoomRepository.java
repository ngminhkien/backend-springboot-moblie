package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Thêm phương thức để lấy tất cả phòng theo mã rạp
    List<Room> findByCinema_MaRap(Long maRap);

}
