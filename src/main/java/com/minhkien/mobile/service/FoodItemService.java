package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.FoodItemResponse;
import com.minhkien.mobile.entity.FoodItem;
import com.minhkien.mobile.mapper.FoodItemMapper;
import com.minhkien.mobile.responsitory.FoodItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FoodItemService {

    FoodItemRepository foodItemRepository;
    FoodItemMapper foodItemMapper;

    public List<FoodItemResponse> getAllFoodItems() {
        List<FoodItem> entities = foodItemRepository.findAll();
        return foodItemMapper.toResponseList(entities);
    }

    public FoodItemResponse getFoodItemById(String id) {
        return foodItemRepository.findById(id)
                .map(foodItemMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với ID: " + id));
    }
}
