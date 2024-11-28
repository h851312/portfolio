package jongwoo.shop.member;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    /**
     * 중복 아이디 검증
     */
//    public void validateDuplicateId(Member member){
//        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 이름입니다. 다른 이름을 입력해주세요.");
//        }
//    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username);
    }
    public Member findByEmail(String email) {return memberRepository.findByEmail(email);}
//    public Member findById(String id){
//        return memberRepository.findById(id);
//    }
//    public void updateMember(Member member){
//        memberRepository.update(member.getUsername(), member.getId());
//    }

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Transactional
    public Long joinUserWithMember(Member member) {
        // 비밀번호 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(member).getId();
    }
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
    }

    public void updateUser(MemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + memberDto.getId()));
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setEmail(memberDto.getEmail());
        member.setRole(memberDto.getRole());
        memberRepository.save(member);
    }
}