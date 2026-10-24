package com.scaler.bloggapp.users;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = true)
    @Nullable
    private String bio;

    @Column(nullable = true)
    @Nullable
    private String image;

}
