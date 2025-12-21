package com.minhkien.mobile.service;


import com.minhkien.mobile.dto.request.User.UserCreationRequest;
import com.minhkien.mobile.dto.request.User.UserUpdateRequest;
import com.minhkien.mobile.dto.response.UserResponse;
import com.minhkien.mobile.entity.Genre;
import com.minhkien.mobile.entity.User;
import com.minhkien.mobile.enums.Role;
import com.minhkien.mobile.mapper.UserMapper;
import com.minhkien.mobile.responsitory.GenreRepository;
import com.minhkien.mobile.responsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor //tạo constructor với những field final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)//mặc định là private final
@Slf4j
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    GenreRepository genreRepository;
    UserMapper userMapper;

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userMapper.updateUserFromRequest(request, user);

        if (request.getMaGenres() != null) {
            List<Genre> genres = genreRepository.findAllById(request.getMaGenres());

            if (genres.size() != request.getMaGenres().size()) {
                log.error("Genres length mismatch");
            }
            user.setFavoriteGenres(new HashSet<>(genres));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {

        //Trong security khi đăng nhập thì sẽ lưu thông tin người dùng trong SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }

    public Set<Genre> getFavoriteGenres(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteGenres();
    }

    public UserResponse createRequest(UserCreationRequest request) {

//        log.info("In method get Create");
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("email da ton tai"); //lấy lỗi từ exception ta tạo ra
        }

        User user = userMapper.toUser(request);
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {

        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }


//    @PostAuthorize("returnObject.username == authentication.name")//ngược lại với pre thì là thực hiện sau khi hàm chạy
//    public UserResponse getUserById(String userId) {
//
//        log.info("In method getUserById");
//        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
//    }
//
//    public UserResponse updateUser(UserUpdateRequest request, String userId) {
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        userMapper.updateUser(user, request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteUser(String userId) {
//
//        userRepository.deleteById(userId);
//    }
}
