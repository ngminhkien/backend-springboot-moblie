package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.UserCreationRequest;
import com.minhkien.mobile.dto.response.UserResponse;
import com.minhkien.mobile.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

}
