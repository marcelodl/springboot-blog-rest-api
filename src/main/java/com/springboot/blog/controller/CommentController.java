package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.model.Comment;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO newCommentDTO = commentService.createComment(postId, commentDTO);
        return new ResponseEntity<CommentDTO>(newCommentDTO,HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByPostId(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId,@Valid  @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<CommentDTO>(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.deleteComment(postId, commentId), HttpStatus.OK);
    }

}
