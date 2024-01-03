package com.myblog.controller;

import com.myblog.payLoad.PostDto;
import com.myblog.payLoad.PostResponse;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }

//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
//        PostDto dto = postService.createPost(postDto);
//        return new ResponseEntity<>(dto, HttpStatus.CREATED);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts(){
//        List<PostDto> postDtos = postService.getAllPosts();
//        return new ResponseEntity<>(postDtos,HttpStatus.OK);
//    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0")int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5")int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false)String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false)String sortDir
    ){
        PostResponse response= postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return response;
    }

}
