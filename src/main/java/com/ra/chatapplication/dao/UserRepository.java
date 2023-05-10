package com.ra.chatapplication.dao;

import com.ra.chatapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    List<User> findByLastNameContainingIgnoreCase(String lastName);

    User findByEmailIgnoreCase(String email);


    // Requete generee automatiquement par Spring
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

//    // Requete creee manuellement
//    @Query("SELECT u FROM User u WHERE LENGTH(u.lastName) >= :lastNameLength")
//    List<User> findByLastNameLength(@Param("lastNameLength") int lastNameLength);
}