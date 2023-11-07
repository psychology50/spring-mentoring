package com.likelion.study.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter // -> 이유가 없다면, 굳이 열지 말 것
// @Setter -> 쓰지 마라!
@NoArgsConstructor(access = AccessLevel.PUBLIC)
// @AllArgsConstructor -> 웬만하면 직접 작성하는 것을 권고
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 30)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder // 빌터 패턴 생성자가 만들어진다.
    public User(String name, String email, String password, String address, RoleType roleType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.roleType = roleType;
    }

    @Override public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", roleType=" + roleType +
                '}';
    }
}
