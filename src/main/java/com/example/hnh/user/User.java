package com.example.hnh.user;

import com.example.hnh.global.BaseEntity;
import com.example.hnh.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , columnDefinition = "varchar(320)")
    private String email;

    @Column(nullable = false , columnDefinition = "varchar(20)" )
    private String name;

    @Column(nullable = false , columnDefinition = "varchar(100)")
    private String password;

    @Column(nullable = false , columnDefinition = "varchar(20)")
    @Enumerated(value = EnumType.STRING)
    private UserRole auth = UserRole.USER;

    public User() {
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(UserRequestDto requestDto){
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }

    public void updateAuth(UserRole auth) {
        this.auth = auth;
    }

    public void setPassword(String encodePassword){
        this.password = encodePassword;
    }
}
