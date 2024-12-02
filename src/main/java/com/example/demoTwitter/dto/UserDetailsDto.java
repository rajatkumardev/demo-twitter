 package com.example.demoTwitter.dto;

 import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

 @Setter
 @Getter
 @AllArgsConstructor
 public class UserDetailsDto {
     private Long userId;
     private String email;
     private String name;
 }
