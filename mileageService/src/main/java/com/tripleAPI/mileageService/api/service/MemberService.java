package com.tripleAPI.mileageService.api.service;

import com.tripleAPI.mileageService.domain.Member;
import com.tripleAPI.mileageService.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //repository를 생성자 주입 해줌
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public UUID join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(UUID memberId) {
        return memberRepository.findOne(memberId);
    }
}
