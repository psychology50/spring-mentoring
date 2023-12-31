package com.likelion.study.controller;

import com.likelion.study.dto.UserReq;
import com.likelion.study.dto.UserRes;
import com.likelion.study.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
// 1. 어느 경로로 들어오는지를 매핑해주는 어노테이션
@RequestMapping("/api/v1/users") // http://localhost:8080/api/v1/users
@Slf4j
public class UserController {
    private final UserService userService;

    // 2. 어떤 요청으로 받을 건데?
    @PostMapping("") // POST http://localhost:8080/api/v1/users
    public ResponseEntity<UserRes> signUp(@RequestBody UserReq request) { // 3. 요청을 어떻게 받을 건데?
        // 4. 응답하기 위한 데이터 어떻게 가져올 건데?
        UserRes response = userService.save(request);
//        System.out.println(response);
        log.info("User signup success: {}", response);

        return ResponseEntity.ok(response);
    }

    // User 전체 조회
    @GetMapping("") // GET http://localhost:8080/api/v1/users
    public ResponseEntity<?> findAll() {
        List<UserRes> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    // User id로 조회
    @GetMapping("/{id}") // GET http://localhost:8080/api/v1/users/{id}?type=1
    public ResponseEntity<?> receive(@PathVariable("id") Long id) { // /api/v1/users/1 -> id = 1
        UserRes response = userService.findById(id);

        return ResponseEntity.ok(response);
    }


//    // User 수정
//    @PutMapping("/{id}") // PUT http://localhost:8080/api/v1/users/{id}
//
//
//    // User 삭제
//    @DeleteMapping("/{id}") // DELETE http://localhost:8080/api/v1/users/{id}

}
