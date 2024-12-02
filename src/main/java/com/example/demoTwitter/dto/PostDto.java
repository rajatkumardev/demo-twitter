package com.example.demoTwitter.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto {
    private Long postID;
    private String postBody;
    private Date date;
    private Long userID;
    private List<CommentDto> comments;

    public PostDto(Long id, String body, Date date, List<CommentDto> commentDtos) {
        this.postID = id;
        this.postBody = body;
        this.date = date;
        this.comments = commentDtos;
    }
}
