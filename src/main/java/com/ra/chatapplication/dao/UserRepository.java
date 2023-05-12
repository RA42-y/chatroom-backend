package com.ra.chatapplication.dao;

import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    Page<User> findByActiveFalse(Pageable pageable);

    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    List<User> findByLastNameContainingIgnoreCase(String lastName);

    User findByEmailIgnoreCase(String email);

    User findById(long id);

    Page<User> findAll(Pageable pageable);


//    User findCurrentUser();

    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

//    // Requete creee manuellement
//    @Query("SELECT u FROM User u WHERE LENGTH(u.lastName) >= :lastNameLength")
//    List<User> findByLastNameLength(@Param("lastNameLength") int lastNameLength);
}