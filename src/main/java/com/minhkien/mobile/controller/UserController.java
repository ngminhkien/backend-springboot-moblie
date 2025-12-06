package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.UserCreationRequest;
import com.minhkien.mobile.dto.response.UserResponse;
import com.minhkien.mobile.entity.User;
import com.minhkien.mobile.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    //chỉ hỏi nếu roles là user trả vêf 401 thì bỏ qua do là admin
    @GetMapping("/{userId}/favorite-genres")
    public ResponseEntity<?> getFavoriteGenres(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getFavoriteGenres(userId));
    }
//    Nếu trả về { } hoặc [] → user chưa chọn sở thích → mở form chọn thể loại.

    @PostMapping()
    UserResponse createUser(@RequestBody UserCreationRequest request) {

        return userService.createRequest(request);
    }

    @GetMapping("/myInfo")
    UserResponse getMyInfo() {

        return userService.getMyInfo();
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return userService.getAllUsers();
    }
}
