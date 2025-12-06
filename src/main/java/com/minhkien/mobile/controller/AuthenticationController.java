package com.minhkien.mobile.controller;

import com.minhkien.mobile.dto.request.AuthenticationRequest;
import com.minhkien.mobile.dto.request.IntrospectRequest;
import com.minhkien.mobile.dto.response.AuthenticationResponse;
import com.minhkien.mobile.dto.response.IntrospectResponse;
import com.minhkien.mobile.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/introspect")
    public IntrospectResponse introspect(@RequestBody IntrospectRequest request) throws Exception {
        return authenticationService.introspect(request);
    }
}
