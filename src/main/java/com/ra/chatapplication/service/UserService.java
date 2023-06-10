package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The UserService interface provides operations for managing user entities.
 */
public interface UserService {

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user
     * @return The User object
     */
    User getUserById(long id);

    /**
     * Retrieves all users.
     *
     * @return A list of User objects
     */
    List<User> getAllUsers();

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageNumber The page number
     * @param pageSize   The number of users per page
     * @param sortBy     The field to sort the users by
     * @return A Page object containing the User objects
     */
    Page<User> getAllUsersByPage(int pageNumber, int pageSize, String sortBy);

    /**
     * Retrieves a paginated list of deactivated users.
     *
     * @param pageNumber The page number
     * @param pageSize   The number of users per page
     * @param sortBy     The field to sort the users by
     * @return A Page object containing the deactivated User objects
     */
    Page<User> getAllDeactivatedUsersByPage(int pageNumber, int pageSize, String sortBy);

    /**
     * Creates a new user.
     *
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param email     The email of the user
     * @param password  The password of the user
     * @param admin     The role of the user
     * @return The created User object
     */
    User createUser(String firstName, String lastName, String email, String password, Boolean admin);

    /**
     * Creates a new user by an admin.
     *
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param email     The email of the user
     * @param admin     The role of the user
     * @return The created User object
     * @throws MessagingException If an error occurs while sending the email with default password
     */
    User createUserByAdmin(String firstName, String lastName, String email, Boolean admin) throws MessagingException;

    /**
     * Saves a user.
     *
     * @param user The User object to save
     */
    void saveUser(User user);

    /**
     * Edits a user.
     *
     * @param user      The User object to edit
     * @param firstName The new first name of the user
     * @param lastName  The new last name of the user
     * @param admin     The new role of the user
     */
    void editUser(User user, String firstName, String lastName, Boolean admin);

    /**
     * Deletes a user.
     *
     * @param id The ID of the user to delete
     */
    void deleteUserById(long id);

    /**
     * Deactivates a user.
     *
     * @param user The User object to deactivate
     */
    void deactivateUser(User user);

    /**
     * Activates a user.
     *
     * @param user The User object to activate
     */
    void activateUser(User user);

    /**
     * Resets the failure login attempts counter for a user.
     *
     * @param user The User object to reset the failure times
     */
    void resetUserFailureTimes(User user);

    /**
     * Searches for users based on a keyword.
     *
     * @param keyword    The keyword to search for
     * @param pageNumber The page number
     * @param pageSize   The number of users per page
     * @param sortBy     The field to sort the users by
     * @return A Page object containing the matched User objects
     */
    Page<User> searchUsers(String keyword, int pageNumber, int pageSize, String sortBy);

    /**
     * Searches for deactivated users based on a keyword.
     *
     * @param keyword    The keyword to search for
     * @param pageNumber The page number
     * @param pageSize   The number of users per page
     * @param sortBy     The field to sort the users by
     * @return A Page object containing the matched deactivated User objects
     */
    Page<User> searchDeactivatedUsers(String keyword, int pageNumber, int pageSize, String sortBy);

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user
     * @return The User object
     */
    User getUserByEmail(String email);

    /**
     * Retrieves a safety user by without sensitive information cloning an existing user object.
     * This is useful for ensuring the security of user data in certain operations.
     *
     * @param originUser The original User object
     * @return The safety User object without sensitive information
     */
    User getSafetyUser(User originUser);

    /**
     * Retrieves the logged-in user based on the HttpServletRequest.
     *
     * @param request The HttpServletRequest object
     * @return The logged-in User object
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * Retrieves the logged-in user based on the access token.
     *
     * @param request The HttpServletRequest object
     * @return The logged-in User object
     */
    User getLoginUserByToken(HttpServletRequest request);

    User getUserResetPasswordByToken(String token);

    /**
     * Compares a raw password with an encoded password.
     *
     * @param rawPassword    The raw password to compare
     * @param encodedPassword The encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
    boolean comparePasswords(String rawPassword, String encodedPassword);
}
