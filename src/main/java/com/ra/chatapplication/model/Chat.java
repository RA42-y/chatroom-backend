package com.ra.chatapplication.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@ToString
@Table(name = "chat")
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @CreatedDate
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToMany
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    private List<User> members = new ArrayList<User>();

    public Chat(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
    public Chat(){
    }
}
