package com.example.demoTwitter.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto2 {
    private Long commentID;
    private String commentBody;

    private CommentCreatorDto commentCreator;

    public CommentDto2(Long id, String body, Long userId, String name) {
            this.commentID = id;
            this.commentBody = body;
            this.commentCreator = new CommentCreatorDto();
            this.commentCreator.setUserID(userId);
            this.commentCreator.setName(name);
     }
}
