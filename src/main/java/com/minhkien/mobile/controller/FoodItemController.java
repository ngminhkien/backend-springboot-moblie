package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.response.FoodItemResponse;
import com.minhkien.mobile.service.FoodItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/food_iteams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FoodItemController {

    FoodItemService foodItemService;

    @GetMapping
    public ResponseEntity<List<FoodItemResponse>> getAll() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(foodItemService.getFoodItemById(id));
    }
}
