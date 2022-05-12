package com.travel.proj.repository;

import com.travel.proj.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface BoardRepository extends JpaRepository<Board,Integer> {

}
