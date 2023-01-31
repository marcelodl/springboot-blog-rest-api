package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.config.security.exception.ResourceNotFoundException;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(PostDTO postDTO, Long id);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Long id) throws ResourceNotFoundException;

    List<PostDTO> getPostByCategory(Long categoryId) throws ResourceNotFoundException;
    String deletePost(Long id);


}
