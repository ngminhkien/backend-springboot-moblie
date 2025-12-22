package com.minhkien.mobile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodItemResponse {

    String maFoodItem;
    String tenFoodItem;
    Double gia;
    String urlPoster;
    String phanLoai;
}
