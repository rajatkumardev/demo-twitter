package com.example.demoTwitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demoTwitter.dto.CommentDto;
import com.example.demoTwitter.dto.PostDto;
import com.example.demoTwitter.entities.Comment;
import com.example.demoTwitter.entities.User;
import com.example.demoTwitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoTwitter.entities.Post;
import com.example.demoTwitter.repository.PostRepository;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PostDto> getUserFeed() {
        List<Post> posts = postRepository.findAllByOrderByDateDesc();
        return convertPostsToDto(posts);
    }

    public boolean createPost(PostDto postDto) {
        Optional<User> userOptional = userRepository.findById(postDto.getUserID());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Post post = new Post(user, postDto.getPostBody());
            postRepository.save(post);
            return true;
        }
        return false; // User does not exist
    }

    public PostDto getPost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.map(this::convertPostToDto).orElse(null);
    }

    public boolean editPost(PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(postDto.getPostID());
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setBody(postDto.getPostBody());
            postRepository.save(post);
            return true;
        }
        return false; // Post does not exist
    }

    public boolean deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false; // Post does not exist
    }

    private List<PostDto> convertPostsToDto(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(convertPostToDto(post));
        }
        return postDtos;
    }

    private PostDto convertPostToDto(Post post) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            commentDtos.add(new CommentDto(
                    comment.getId(),
                    comment.getBody(),
                    comment.getCommentCreator().getUserId(),
                    comment.getCommentCreator().getName()
            ));
        }
        return new PostDto(post.getId(), post.getBody(), post.getDate(), commentDtos);
    }

}
