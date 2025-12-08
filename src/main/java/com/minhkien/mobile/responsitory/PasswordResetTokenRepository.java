package com.minhkien.mobile.responsitory;

import com.minhkien.mobile.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {

    Optional<PasswordResetToken> findByEmail(String email);

    //void deleteByEmail(String email);
    @Modifying
    @Query("DELETE FROM PasswordResetToken p WHERE p.email = :email")
    void deleteByEmail(@Param("email") String email);
}

