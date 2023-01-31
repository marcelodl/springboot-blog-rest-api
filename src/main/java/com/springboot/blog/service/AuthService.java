package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDTO;
import com.springboot.blog.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
