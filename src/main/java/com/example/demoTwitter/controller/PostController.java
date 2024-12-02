package com.example.demoTwitter.controller;

import java.util.List;

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

import com.example.demoTwitter.dto.PostDto;
import com.example.demoTwitter.service.PostService;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getUserFeed() {
        List<PostDto> userFeed = postService.getUserFeed();
        return ResponseEntity.ok(userFeed);
    }

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        if (postService.createPost(postDto)) {
            return ResponseEntity.ok("Post created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam("postID") Long postId) {
        PostDto postDto = postService.getPost(postId);
        if (postDto != null) {
            return ResponseEntity.ok(postDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

    @PatchMapping("/post")
    public ResponseEntity<String> editPost(@RequestBody PostDto postDto) {
        if (postService.editPost(postDto)) {
            return ResponseEntity.ok("Post edited successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

    @DeleteMapping("/post")
    public ResponseEntity<String> deletePost(@RequestParam("postID") Long postId) {
        if (postService.deletePost(postId)) {
            return ResponseEntity.ok("Post deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

}
