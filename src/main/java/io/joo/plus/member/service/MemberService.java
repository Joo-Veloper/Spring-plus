package io.joo.plus.member.service;

import io.joo.plus.member.dto.MemberRequestDto;
import io.joo.plus.member.entity.Member;
import io.joo.plus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsById(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    public void signup(MemberRequestDto memberRequestDto) {
        String userId = memberRequestDto.getUserId();
        String password = passwordEncoder.encode(memberRequestDto.getPassword());


        if(memberRepository.findByUserId(userId).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
        Member member = new Member(userId, password);
        memberRepository.save(member);
    }

    public void login(MemberRequestDto memberRequestDto) {
        String userId = memberRequestDto.getUserId();
        String password = memberRequestDto.getPassword();

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(()-> new UsernameNotFoundException("닉네임을 확인해 주세요."));
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


}