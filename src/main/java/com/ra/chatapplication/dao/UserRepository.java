package com.ra.chatapplication.dao;

import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository is a repository interface for performing CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByActiveFalse(Pageable pageable);

    User findByEmailIgnoreCase(String email);

    User findById(long id);

    Page<User> findAll(Pageable pageable);

    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email, Pageable pageable);

    Page<User> findByFirstNameContainingIgnoreCaseAndActiveFalseOrLastNameContainingIgnoreCaseAndActiveFalseOrEmailContainingIgnoreCaseAndActiveFalse(String firstName, String lastName, String email, Pageable pageable);
}