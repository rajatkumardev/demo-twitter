package com.example.demoTwitter.entities;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CommentCreator {

    private Long userId;

    private String name;

}
