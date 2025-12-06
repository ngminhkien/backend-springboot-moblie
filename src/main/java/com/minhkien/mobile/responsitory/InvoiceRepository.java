package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
