package com.minhkien.mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    Film film;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    LocalDateTime tgBatDau;
    LocalDateTime tgKetThuc;

    public void calculateEndTime() {
        if (film != null && tgBatDau != null) {
            this.tgKetThuc = tgKetThuc.plusMinutes(film.getThoiLuong());
        }
    }
}
