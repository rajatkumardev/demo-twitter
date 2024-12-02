package com.example.demoTwitter.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @Embedded
    private CommentCreator commentCreator;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(Post post, User user, String commentBody) {
        this.post = post;
        this.commentCreator = new CommentCreator();
        this.commentCreator.setUserId(user.getId());
        this.commentCreator.setName(user.getName());
        this.body = commentBody;
    }
}
