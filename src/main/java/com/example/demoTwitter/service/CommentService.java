package com.example.demoTwitter.service;

import java.util.Optional;

import com.example.demoTwitter.dto.CommentDto;
import com.example.demoTwitter.entities.Post;
import com.example.demoTwitter.entities.User;
import com.example.demoTwitter.repository.PostRepository;
import com.example.demoTwitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoTwitter.entities.Comment;
import com.example.demoTwitter.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // public boolean createComment(CommentDto commentDto) {
    //     Optional<Post> postOptional = postRepository.findById(commentDto.getPostID());
    //     if (postOptional.isPresent()) {
    //         Optional<User> userOptional = userRepository.findById(commentDto.getUserID());
    //         if (userOptional.isPresent()) {
    //             Post post = postOptional.get();
    //             User user = userOptional.get();
    //             Comment comment = new Comment(post, user, commentDto.getCommentBody());
    //             commentRepository.save(comment);
    //             return true;
    //         }
    //     }
    //     return false; // Post or user does not exist
    // }

    public String createComment(CommentDto commentDto) {
        Optional<Post> postOptional = postRepository.findById(commentDto.getPostID());
        Optional<User> userOptional = userRepository.findById(commentDto.getUserID());
        
        if (!postOptional.isPresent()) {
            return "Post does not exist";
        }
        
        if (!userOptional.isPresent()) {
            return "User does not exist";
        }
        
        Post post = postOptional.get();
        User user = userOptional.get();
        
        Comment comment = new Comment(post, user, commentDto.getCommentBody());
        commentRepository.save(comment);
        
        return "Comment created successfully";
    }

    public CommentDto getComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        return commentOptional.map(this::convertCommentToDto).orElse(null);
    }

    public boolean editComment(CommentDto commentDto) {
        Optional<Comment> commentOptional = commentRepository.findById(commentDto.getCommentID());
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setBody(commentDto.getCommentBody());
            commentRepository.save(comment);
            return true;
        }
        return false; // Comment does not exist
    }

    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false; // Comment does not exist
    }

    private CommentDto convertCommentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBody(), comment.getCommentCreator().getUserId(), comment.getCommentCreator().getName());
    }
}