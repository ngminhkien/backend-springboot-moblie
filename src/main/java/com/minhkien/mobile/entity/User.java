package com.minhkien.mobile.entity;

import com.minhkien.mobile.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {

    @Id
//    @Column(name = "MaUser", length = 5)
    @GeneratedValue(strategy = GenerationType.UUID)
    String maUser; // "KHxxx"

    @Column(name = "HoTen")
    String hoTen;

    @Column(name = "GioiTinh")
    String gioiTinh;

    @Column(name = "NgaySinh")
    LocalDate ngaySinh;

    @Column(name = "Email")
    String email;

    @Column(name = "MatKhau")
    String matKhau;

    @Column(name = "AnhURL")
    String anhURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "ma_user")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_genre",
            joinColumns = @JoinColumn(name = "ma_user"),
            inverseJoinColumns = @JoinColumn(name = "ma_genre")
    )
    Set<Genre> favoriteGenres = new HashSet<>();

}
