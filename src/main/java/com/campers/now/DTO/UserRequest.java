package com.campers.now.DTO;

import com.campers.now.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @JsonIgnore(value = false)
    private String password;
    private Integer id;
    private String nom;
    private String prenom;
    private String avatar;
    private String email;
    private boolean isEmailValide;
    private boolean isActive;
    private boolean tokenExpired;
    private Instant createdAt;
    private Instant modifiedAt;
    private List<Role> roles;
}
