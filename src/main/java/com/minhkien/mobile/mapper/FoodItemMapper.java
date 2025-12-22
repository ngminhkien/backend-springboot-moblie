package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.FoodItemResponse;
import com.minhkien.mobile.entity.FoodItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {

    // Convert từ Entity sang Response DTO
    FoodItemResponse toResponse(FoodItem foodItem);

    // Convert List Entity sang List Response DTO
    List<FoodItemResponse> toResponseList(List<FoodItem> foodItems);
}
