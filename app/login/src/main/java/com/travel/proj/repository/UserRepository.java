package com.travel.proj.repository;

import com.travel.proj.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email,String password);
}
