package com.minhkien.mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long maHoaDon;

    // Liên kết tới User
    @ManyToOne
    @JoinColumn(name = "maUser", nullable = false)
    User user;

    // Liên kết tới suất chiếu
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    Showtime showtime;

    // Liên kết tới voucher (mỗi hóa đơn chọn 1 voucher)
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    Voucher voucher;

    // Liên kết tới đồ ăn (nhiều đồ ăn trong 1 hóa đơn)
    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FoodInvoice> doAnList;

//    // Liên kết tới chi tiết hóa đơn (ghế)
//    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<InvoiceDetail> chiTietList;

    Double tongTien;

    LocalDateTime ngayTao;

    String url;

    Boolean trangThai;
}
