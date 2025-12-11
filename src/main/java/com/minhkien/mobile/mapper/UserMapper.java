package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.UserCreationRequest;
import com.minhkien.mobile.dto.response.UserResponse;
import com.minhkien.mobile.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    @Mapping(source = "maUser", target = "maUser")
    UserResponse toUserResponse(User user);

}
