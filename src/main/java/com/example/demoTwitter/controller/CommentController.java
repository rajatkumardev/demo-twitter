package com.example.demoTwitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoTwitter.dto.CommentDto;
import com.example.demoTwitter.service.CommentService;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
        String response = commentService.createComment(commentDto);
        if (response.equals("Comment created successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam("commentID") Long commentId) {
        CommentDto commentDto = commentService.getComment(commentId);
        if (commentDto != null) {
            return ResponseEntity.ok(commentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<String> editComment(@RequestBody CommentDto commentDto) {
        if (commentService.editComment(commentDto)) {
            return ResponseEntity.ok("Comment edited successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@RequestParam("commentID") Long commentId) {
        if (commentService.deleteComment(commentId)) {
            return ResponseEntity.ok("Comment deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

}
