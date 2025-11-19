package com.example.schedules.repository;

import com.example.schedules.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
   Optional<User> findByEmail(String email);//Optional은 User가 있을 수도 있고 없을 수도 있다는 걸 나타냄
    boolean existsByEmail(String email);
}
