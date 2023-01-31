package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.config.security.exception.BlogAPIException;
import com.springboot.blog.config.security.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",Long.toString(postId)));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",Long.toString(postId)));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));

        if(!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post");
        }

        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());

        Comment updateComment = commentRepository.save(comment);

        return mapToDTO(updateComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(c -> mapToDTO(c)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",Long.toString(postId)));

       // Comment comment = post.getComments().stream().filter(c -> c.getId() == commentId).findFirst().orElseThrow(() -> new ResourceNotFoundException("comment","id",Long.toString(commentId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));

        if(!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post");
        }

        return mapToDTO(comment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",Long.toString(postId)));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));

        if(!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post");
        }

        commentRepository.delete(comment);
        return String.format("Comment of id %s delete", commentId);
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = mapper.map(commentDTO, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
//        comment.setName(commentDTO.getName());

        return comment;
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setName(comment.getName());

        return commentDTO;
    }
}
