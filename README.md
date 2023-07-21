# Chatroom (backend)

> Chatroom application based on React and Spring Boot with a user administration interface.

Backend repository: https://github.com/RA42-y/chatroom-backend

Frontend repository: https://github.com/RA42-y/chatroom-frontend

## Interface

### Login

#### Login page

<p>
  <img src="assets/screen-shots/login/login.png" width="45%" />
</p>

#### Different alerts

<p>
  <img src="assets/screen-shots/login/login-error.png" width="30%" />
  <img src="assets/screen-shots/login/login-blocked.png" width="30%" />
  <img src="assets/screen-shots/login/login-logout.png" width="30%" />
</p>

### Password management

#### Reset password

Mandatory for the first connection

<p>
  <img src="assets/screen-shots/login/login-reset-password.png" width="45%" />
</p>


#### Forget password

<p>
  <img src="assets/screen-shots/login/login-forgot-password-email1.png" width="45%" />
  <img src="assets/screen-shots/login/login-forgot-password-email2.png" width="45%" />
</p>


E-mail containing link to reset password

<p>
  <img src="assets/screen-shots/email/email-reset-forgot-password.jpeg" width="45%" />
</p>


Password reset page in case you forget your password

<p>
  <img src="assets/screen-shots/login/login-forgot-password-reset1.png" width="45%" />
  <img src="assets/screen-shots/login/login-forgot-password-reset2.png" width="45%" />
</p>

### Administration

#### User list

List of all users (with pagination, 7 per page)

<p>
  <img src="assets/screen-shots/admin/admin-user-list-page1.png" width="45%" />
  <img src="assets/screen-shots/admin/admin-user-list-page2.png" width="45%" />
</p>


List of disabled users (with pagination, 7 per page)

<p>
  <img src="assets/screen-shots/admin/admin-deactivated-user-list.png" width="45%" />
</p>


#### Creation of a new user

<p>
  <img src="assets/screen-shots/admin/admin-create1.png" width="45%" />
  <img src="assets/screen-shots/admin/admin-create2.png" width="45%" />
</p>


Email containing default password sent to the newly created user

<p>
  <img src="assets/screen-shots/email/email-default-password.jpeg" width="45%" />
</p>


#### Operations on existing users

An administrator can modify, delete, deactivate and activate users.

##### Deactivate and activate

<p>
  <img src="assets/screen-shots/admin/admin-deactivate.png" width="45%" />
    <img src="assets/screen-shots/admin/admin-activate.png" width="45%" />
</p>

##### Modify

<p>
  <img src="assets/screen-shots/admin/admin-edit1.png" width="45%" />
  <img src="assets/screen-shots/admin/admin-edit2.png" width="45%" />
</p>

##### Delete

<p>
  <img src="assets/screen-shots/admin/admin-delete.png" width="45%" />
</p>


### Chatroom

#### Chat list

List of all chats of the current user (with pagination, 6 per page)

<p>
  <img src="assets/screen-shots/chatroom/chatroom-list-my-chats-page1.png" width="45%" />
  <img src="assets/screen-shots/chatroom/chatroom-list-my-chats-page2.png" width="45%" />
</p>


List of chats created by the current user (with pagination, 6 per page)

<p>
  <img src="assets/screen-shots/chatroom/chatroom-list-created-chats.png" width="45%" />
</p>


List of chats to which the current user is invited (with pagination, 6 per page)

<p>
  <img src="assets/screen-shots/chatroom/chatroom-list-joined-chats.png" width="45%" />
</p>


Empty chat list

<p>
  <img src="assets/screen-shots/chatroom/chatroom-list-empty.png" width="45%" />
</p>


#### Chat window

functional and expired cat

<p>
  <img src="assets/screen-shots/chatroom/chatroom-chat.png" width="45%" />
  <img src="assets/screen-shots/chatroom/chatroom-expired.png" width="45%" />
</p>


#### Creation of a new chat

<p>
  <img src="assets/screen-shots/chatroom/chatroom-create1.png" width="30%" />
  <img src="assets/screen-shots/chatroom/chatroom-create2.png" width="30%" />
  <img src="assets/screen-shots/chatroom/chatroom-create3.png" width="30%" />
</p>


#### Operations on existing chats

If user is creator: edit, invite users, remove members, delete.

If user is guest: quit

<p>
  <img src="assets/screen-shots/chatroom/chatroom-operation-dropdown1.png" width="45%" />
  <img src="assets/screen-shots/chatroom/chatroom-operation-dropdown2.png" width="45%" />
</p>

##### Modify

<p>
  <img src="assets/screen-shots/chatroom/chatroom-modal-edit.png" width="45%" />
  <img src="assets/screen-shots/chatroom/chatroom-edit.png" width="45%" />
</p>

##### Invite users

<p>
  <img src="assets/screen-shots/chatroom/chatroom-modal-invite.png" width="30%" />
  <img src="assets/screen-shots/chatroom/chatroom-invite1.png" width="30%" />
  <img src="assets/screen-shots/chatroom/chatroom-invite2.png" width="30%" />
</p>

##### Remove members

<p>
  <img src="assets/screen-shots/chatroom/chatroom-modal-remove.png" width="45%" />
  <img src="assets/screen-shots/chatroom/chatroom-remove.png" width="45%" />
</p>

##### Delete

<p>
  <img src="assets/screen-shots/chatroom/chatroom-delete.png" width="45%" />
</p>

##### Quit

<p>
  <img src="assets/screen-shots/chatroom/chatroom-quit.png" width="45%" />
</p>


#### Example of a chat

John (client 1) joins the chat "Chat 1", then Jane (client 2) joins.

<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-1-client1.png" width="70%" />
</p>


John sends a message

<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-2-client1.png" width="70%" />
</p>


Jane receives the message sent by John, then replies.

<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-3-client2.png" width="70%" />
</p>


<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-4-client2.png" width="70%" />
</p>


John receives the message sent by Jane.

<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-5-client1.png" width="70%" />
</p>


Jane leaves the chat.

<p>
    <img src="assets/screen-shots/chatroom/chatroom-chat-6-client1.png" width="70%" />
</p>

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
