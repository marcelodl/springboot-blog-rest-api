package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.config.security.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId().toString()));

        Post post = convertPostDTOToPost(postDTO);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        PostDTO postResponse = convertPostToPostDTO(savedPost);
        return postResponse;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id.toString()));
        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId().toString()));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setCategory(category);

        Post savedPost = postRepository.save(post);
        PostDTO postResponse = convertPostToPostDTO(savedPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(Sort.Direction.fromString(sortDir),sortBy));
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listPosts = posts.getContent();

        List<PostDTO> content = listPosts.stream().map(post -> convertPostToPostDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id.toString()));
        return convertPostToPostDTO(post);
    }

    @Override
    public List<PostDTO> getPostByCategory(Long categoryId) throws ResourceNotFoundException {
        List<PostDTO> postDTOList = postRepository
                .findByCategoryId(categoryId)
                .stream().map(p -> mapper.map(p,PostDTO.class)).collect(Collectors.toList());
        return postDTOList;
    }

    @Override
    public String deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id.toString()));

        postRepository.delete(post);

        return "Post of id " + id + " deleted";
    }

    private PostDTO convertPostToPostDTO(Post post) {
        PostDTO postDTO = mapper.map(post, PostDTO.class);

//        PostDTO postResponse = new PostDTO();
//        postResponse.setId(post.getId());
//        postResponse.setContent(post.getContent());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());

        return postDTO;
    }

    private Post convertPostDTOToPost(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);

//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());

        return  post;
    }

}
