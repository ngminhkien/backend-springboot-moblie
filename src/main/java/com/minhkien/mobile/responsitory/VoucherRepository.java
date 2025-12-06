package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
}
