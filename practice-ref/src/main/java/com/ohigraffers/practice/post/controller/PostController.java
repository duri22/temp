package com.ohigraffers.practice.post.controller;

import com.ohigraffers.practice.post.dto.request.PostCreateRequest;
import com.ohigraffers.practice.post.dto.response.ResponseMessage;
import com.ohigraffers.practice.post.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final List<Post> posts;

    public PostController() {
        posts = new ArrayList<>();
        posts.add(new Post(1L, "제목1", "내용1", "홍길동"));
        posts.add(new Post(2L, "제목2", "내용2", "유관순"));
        posts.add(new Post(3L, "제목3", "내용3", "신사임당"));
        posts.add(new Post(4L, "제목4", "내용4", "이순신"));
        posts.add(new Post(5L, "제목5", "내용5", "장보고"));
    }

    /* 전체 포스트 조회 */
    @GetMapping
    @ApiOperation(value = "전체 포스트 조회", notes = "등록된 모든 포스트를 조회합니다.")
    public ResponseEntity<List<Post>> findAllPosts() {
        return ResponseEntity.ok(posts);
    }

    /* 특정 코드로 포스트 조회 */
    @GetMapping("/{code}")
    @ApiOperation(value = "특정 코드로 포스트 조회", notes = "코드로 포스트를 조회합니다.")
    public ResponseEntity<Post> findPostByCode(
            @PathVariable Long code
    ) {
        Optional<Post> optionalPost = posts.stream()
                .filter(post -> post.getCode().equals(code))
                .findFirst();
        return optionalPost.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* 신규 포스트 등록 */
    @PostMapping
    @ApiOperation(value = "신규 포스트 등록", notes = "신규 포스트를 등록합니다.")
    public ResponseEntity<Void> registPost(
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        Long newCode = posts.isEmpty() ? 1 : posts.get(posts.size() - 1).getCode() + 1;
        Post newPost = new Post(newCode, postCreateRequest.getTitle(), postCreateRequest.getContent(), postCreateRequest.getAuthor());
        posts.add(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 포스트 제목과 내용 수정 */
    @PutMapping("/{code}")
    @ApiOperation(value = "포스트 제목과 내용 수정", notes = "포스트의 제목과 내용을 수정합니다.")
    public ResponseEntity<Void> modifyPost(
            @PathVariable Long code,
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        Optional<Post> optionalPost = posts.stream()
                .filter(post -> post.getCode().equals(code))
                .findFirst();
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postCreateRequest.getTitle());
            post.setContent(postCreateRequest.getContent());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* 포스트 삭제 */
    @DeleteMapping("/{code}")
    @ApiOperation(value = "포스트 삭제", notes = "코드로 포스트를 삭제합니다.")
    public ResponseEntity<Void> removePost(
            @PathVariable Long code
    ) {
        Optional<Post> optionalPost = posts.stream()
                .filter(post -> post.getCode().equals(code))
                .findFirst();
        if (optionalPost.isPresent()) {
            posts.remove(optionalPost.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
