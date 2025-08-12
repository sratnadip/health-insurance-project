package com.crud.controller;

import com.crud.confg.JwtUtil;
import com.crud.dto.LoginDto;
import com.crud.dto.OtpRequest;
import com.crud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
//        String jwt = authService.verifyOtp(request.getEmail(), request.getOtp());
//        return ResponseEntity.ok(Map.of("jwt", jwt));
//    }

    // Step 2: Verify OTP -> Return JWT
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest dto) {
        String result = authService.verifyOtp(dto.getEmail(), dto.getOtp());

        if ("success".equals(result)) {
            String token = jwtUtil.generateToken(dto.getEmail());
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful ✅",
                    "token", token
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", result));
        }
    }

}


