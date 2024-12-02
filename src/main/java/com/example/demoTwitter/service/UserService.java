package com.example.demoTwitter.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoTwitter.dto.UserAuthDto;
import com.example.demoTwitter.dto.UserDetailsDto;
import com.example.demoTwitter.entities.User;
import com.example.demoTwitter.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(UserAuthDto userAuthDto) {
        User user = new User();
        user.setName(userAuthDto.getName());
        user.setEmail(userAuthDto.getEmail());
        user.setPassword(userAuthDto.getPassword());

        userRepository.save(user);
    }

    public boolean login(UserAuthDto userAuthDto) throws UserPrincipalNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(userAuthDto.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPassword().equals(userAuthDto.getPassword());
        } else {
            throw new UserPrincipalNotFoundException(null);
        }

    }

    public boolean existsByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    public User getUser(Long userId) throws UserPrincipalNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserPrincipalNotFoundException("User does not exist"));
    }

//    public UserDetailsDto getUserDetails(Long userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            List<PostDto> postDtos = new ArrayList<>();
//            for (Post post : user.getPosts()) {
//                List<CommentDto> commentDtos = new ArrayList<>();
//                for (Comment comment : post.getComments()) {
//                    commentDtos.add(new CommentDto(comment.getId(), comment.getBody(), comment.getCommentCreator().getUserId(), comment.getCommentCreator().getName()));
//                }
//                postDtos.add(new PostDto(post.getId(), post.getBody(), post.getDate(), commentDtos));
//            }
//            return new UserDetailsDto(user.getId(), user.getEmail(), user.getName(), postDtos);
//        }
//        return null; // User does not exist
//    }

    public UserDetailsDto getUserDetails(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDetailsDto(user.getId(), user.getEmail(), user.getName());
        }
        return null; // User does not exist
    }

    public List<UserDetailsDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDetailsDto> userDetailsDtos = new ArrayList<>();
        for (User user : users) {
            userDetailsDtos.add(new UserDetailsDto(user.getId(), user.getEmail(), user.getName()));
        }
        return userDetailsDtos;
    }
}
