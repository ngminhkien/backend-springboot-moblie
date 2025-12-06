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
@Table(name = "rooms")
public class Room {

    @Id
    Long maPhong;

    String tenPhong;

    @ManyToOne
    @JoinColumn(name = "ma_rap")
    Cinema cinema;

    int soGhe;
}
