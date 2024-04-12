package com.example.Blog.feign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String prenom;
    private String email;
}
