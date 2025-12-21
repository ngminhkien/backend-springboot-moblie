package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    @Query("SELECT d.ghe.maSeatType FROM InvoiceDetail d " +
            "WHERE d.hoaDon.showtime.id = :maSuatChieu " +
            "AND d.hoaDon.trangThai = true")
    List<String> findBookedSeatCodes(@Param("maSuatChieu") Long maSuatChieu);
}
