package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByUser_MaUserOrderByNgayTaoDesc(String maUser);
}
