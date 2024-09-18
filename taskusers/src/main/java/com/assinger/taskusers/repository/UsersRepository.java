package com.assinger.taskusers.repository;

import com.assinger.taskusers.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {

    Optional<UsersEntity> findByEmail(String email);

    Optional<List<UsersEntity>> findAllByEnabledTrue();
}
