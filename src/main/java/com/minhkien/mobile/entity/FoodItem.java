package com.minhkien.mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "food_item")
public class FoodItem {

    @Id
    String maFoodItem;
    String tenFoodItem;
    Double gia;
    String urlPoster;
    String phanLoai; //đồ ăn đồ uống
}
