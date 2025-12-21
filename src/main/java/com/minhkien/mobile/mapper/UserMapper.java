package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.User.UserCreationRequest;
import com.minhkien.mobile.dto.request.User.UserUpdateRequest;
import com.minhkien.mobile.dto.response.UserResponse;
import com.minhkien.mobile.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    @Mapping(source = "maUser", target = "maUser")
    UserResponse toUserResponse(User user);

    // Chỉ map các field cơ bản, bỏ qua maGenres vì cần xử lý logic riêng trong Service
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "favoriteGenres", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);

}
