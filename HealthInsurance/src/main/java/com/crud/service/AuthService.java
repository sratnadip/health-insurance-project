package com.crud.service;

public interface AuthService {

    String login(String email, String password);

    String verifyOtp(String email, String otp);



}