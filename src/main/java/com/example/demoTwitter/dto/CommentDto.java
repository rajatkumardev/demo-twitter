 package com.example.demoTwitter.dto;

 import lombok.Getter;
 import lombok.Setter;

 @Setter
 @Getter
 public class CommentDto {
    private Long commentID;
    private String commentBody;

    private Long postID;
    private Long userID;

    private CommentCreatorDto commentCreator;

     public CommentDto(Long id, String body, Long userId, String name) {
            this.commentID = id;
            this.commentBody = body;
            this.commentCreator = new CommentCreatorDto();
            this.commentCreator.setUserID(userId);
            this.commentCreator.setName(name);
     }
 }
