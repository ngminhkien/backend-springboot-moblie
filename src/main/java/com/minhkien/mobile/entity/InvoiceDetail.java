package com.minhkien.mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "invoice_details")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long maChiTietHoaDon;

    @ManyToOne
    @JoinColumn(name = "maHoaDon", nullable = false)
    Invoice hoaDon;

    @ManyToOne
    @JoinColumn(name = "maSeatType", nullable = false)
    SeatType ghe;

    Double giaLichSu;
}
