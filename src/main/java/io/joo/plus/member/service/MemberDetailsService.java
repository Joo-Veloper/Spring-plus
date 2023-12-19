package io.joo.plus.member.service;

import io.joo.plus.member.entity.Member;
import io.joo.plus.member.repository.MemberRepository;
import io.joo.plus.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    public UserDetails getMemberDetails(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + userId));
        return new MemberDetailsImpl(member);
    }
}
