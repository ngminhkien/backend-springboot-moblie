package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Room;
import com.minhkien.mobile.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


}
