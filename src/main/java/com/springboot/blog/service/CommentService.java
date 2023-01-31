package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    String deleteComment(Long postId, Long commentId);

}
