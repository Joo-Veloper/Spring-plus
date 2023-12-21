package io.joo.plus.member.controller;

import io.joo.plus.jwt.JwtUtil;
import io.joo.plus.member.dto.CommonResponseDto;
import io.joo.plus.member.dto.MemberRequestDto;
import io.joo.plus.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    @GetMapping("/{userId}/checkId")
    public ResponseEntity<CommonResponseDto> checkId(@PathVariable String userId) {
        boolean exists = checkIdExists(userId);
        if (exists) {
            // 중복된 아이디인 경우
            return ResponseEntity.ok(new CommonResponseDto("아이디가 이미 존재합니다.", HttpStatus.BAD_REQUEST.value()));
        } else {
            // 중복되지 않은 아이디인 경우
            return ResponseEntity.ok(new CommonResponseDto("사용 가능한 아이디입니다.", HttpStatus.CONTINUE.value()));
        }
    }

    private boolean checkIdExists(String userId) {
        return memberService.existsById(userId);
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody MemberRequestDto memberRequestDto){
        try {
            memberService.signup(memberRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse res){
        try {
            memberService.login(memberRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        res.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(memberRequestDto.getUserId()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }


}
