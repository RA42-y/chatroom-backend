package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * The ChatService interface provides operations for managing chat entities.
 */
public interface ChatService {

    /**
     * Retrieves all chats by page.
     *
     * @param pageNumber The page number
     * @param pageSize   The page size
     * @return A Page containing the chats
     */
    Page<Chat> getAllChatsByPage(int pageNumber, int pageSize);

    /**
     * Retrieves a chat by its ID.
     *
     * @param id The ID of the chat
     * @return The Chat object
     */
    Chat getChatById(long id);

    /**
     * Adds a member to a chat.
     *
     * @param chat   The chat to add the member to
     * @param member The member to add to the chat
     */
    void addMemberToChat(Chat chat, User member);

    /**
     * Retrieves chats created by a user.
     *
     * @param user The user who created the chats
     * @return A list of Chat objects created by the user
     */
    List<Chat> getChatsCreatedByUser(User user);

    /**
     * Retrieves chats joined by a user.
     *
     * @param user The user who is a member of the chats
     * @return A list of Chat objects joined by the user
     */
    List<Chat> getChatsJoinedByUser(User user);

    /**
     * Retrieves chats created by a user with pagination.
     *
     * @param user       The user who created the chats
     * @param pageNumber The page number
     * @param pageSize   The page size
     * @return A Page containing the chats created by the user
     */
    Page<Chat> getChatsCreatedByUserByPage(User user, int pageNumber, int pageSize);

    /**
     * Retrieves chats joined by a user with pagination.
     *
     * @param user       The user who is a member of the chats
     * @param pageNumber The page number
     * @param pageSize   The page size
     * @return A Page containing the chats joined by the user
     */
    Page<Chat> getChatsJoinedByUserByPage(User user, int pageNumber, int pageSize);

    /**
     * Retrieves chats of a user with pagination.
     *
     * @param user       The user
     * @param pageNumber The page number
     * @param pageSize   The page size
     * @return A Page containing the chats of the user
     */
    Page<Chat> getChatsOfUserByPage(User user, int pageNumber, int pageSize);

    /**
     * Removes the creator of a chat.
     *
     * @param chat The chat to remove the creator from
     */
    void removeCreator(Chat chat);

    /**
     * Removes a user from a chat.
     *
     * @param chat The chat to remove the user from
     * @param user The user to remove from the chat
     */
    void removeUserFromChat(Chat chat, User user);

    /**
     * Removes all users from a chat.
     *
     * @param chat The chat to remove the users from
     */
    void removeAllUsersFromChat(Chat chat);

    /**
     * Saves a chat.
     *
     * @param chat The chat to save
     */
    void saveChat(Chat chat);

    /**
     * Deletes a chat.
     *
     * @param chat The chat to delete
     */
    void deleteChat(Chat chat);

    /**
     * Edits a chat.
     *
     * @param chat        The chat to edit
     * @param name        The new name of the chat
     * @param description The new description of the chat
     * @param expireDate  The date of expiration of the chat
     */
    void editChat(Chat chat, String name, String description, Date expireDate);

    /**
     * Creates a new chat.
     *
     * @param name        The name of the chat
     * @param description The description of the chat
     * @param creator     The user who creates the chat
     * @return The created Chat object
     */
    Chat createChat(String name, String description, Date expireDate, User creator);

    /**
     * Checks if a user is the creator of a chat.
     *
     * @param chat The chat to check
     * @param user The user to check
     * @return true if the user is the creator of the chat, false otherwise
     */
    boolean isUserCreator(Chat chat, User user);

    /**
     * Checks if a user is a member of a chat.
     *
     * @param chat The chat to check
     * @param user The user to check
     * @return true if the user is a member of the chat, false otherwise
     */
    boolean isUserMember(Chat chat, User user);
}
