package com.smartShoppe.login.Repository;

import com.smartShoppe.login.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> getByEmailOrMobileNumber(String email, String mobileNumber);
}
