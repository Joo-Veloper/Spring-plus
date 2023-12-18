package io.joo.plus.member.service;

import io.joo.plus.member.entity.Member;
import io.joo.plus.member.repository.MemberRepository;
import io.joo.plus.security.MemberDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService {
    private MemberRepository memberRepository;

    public UserDetails getMemberDetails(String userId){
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + userId));
        return new MemberDetailsImpl(member);
    }
}
