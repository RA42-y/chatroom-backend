package com.ra.chatapplication.dao;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatRepository is a repository interface for performing CRUD operations on Chat entities.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findAll(Pageable pageable);

    Chat findChatById(long id);

    List<Chat> findByCreator(User creator);

    Page<Chat> findByCreator(User creator, Pageable pageable);

    List<Chat> findByMembersContaining(User member);

    Page<Chat> findByMembersContaining(User member, Pageable pageable);

    Page<Chat> findByMembersContainingOrCreator(User member, User creator, Pageable pageable);

}