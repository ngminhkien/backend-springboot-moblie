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
@Table(name = "food_invoice")
public class FoodInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "hoa_don_id", nullable = false)
    Invoice hoaDon;

    @ManyToOne
    @JoinColumn(name = "do_an_id", nullable = false)
    FoodItem doAn;

    Integer soLuong;
}
