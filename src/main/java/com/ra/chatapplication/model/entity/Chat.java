package com.ra.chatapplication.model.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Chat entity
 */
@Entity
@Data
@ToString
@Table(name = "chat")
@EntityListeners(AuditingEntityListener.class)
public class Chat implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * Name
     */
    @Column
    private String name;

    /**
     * Description
     */
    @Column
    private String description;

    /**
     * The creator of the chat
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user")
    private User creator;

    /**
     * The date time of creation of the chat
     */
    @CreatedDate
    private Date createDate;

    /**
     * The date of expiration of the chat
     */
    @Column(name = "expire_date")
    private Date expireDate;

    /**
     * The list of users added in the chat as member
     */
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members = new ArrayList<>();

    public Chat(String name, String description, Date expireDate, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.expireDate = expireDate;
    }

    public Chat(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public Chat(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
