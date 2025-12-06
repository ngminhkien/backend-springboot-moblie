package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Cinema;
import com.minhkien.mobile.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {


}
