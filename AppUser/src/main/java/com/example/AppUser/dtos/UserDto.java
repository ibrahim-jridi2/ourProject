package com.example.AppUser.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String Country;
    private byte[] Image;
    private String phoneNumber;
}
