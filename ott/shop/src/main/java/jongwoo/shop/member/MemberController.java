package jongwoo.shop.member;



//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/member")
//public class MemberController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/memberLoginForm")
//    public String login() {
//        return "member/memberLoginForm";
//    }
//
//    @GetMapping("/memberJoinForm")
//    public String addForm() {
//        return "member/memberJoinForm";
//    }
//
//
//    @PostMapping("/memberJoinForm")
//    public String createMember(@ModelAttribute Member member){
//        memberService.join(member);
//        return "member/memberSaved";
//    }
//
//
//}

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final HttpSession httpSession;
    @GetMapping("/memberJoinForm")
    public String createMember() {
        return "member/memberJoinForm";
    }

    @GetMapping("/test/login")
    @ResponseBody
    //@AuthenticationPrincipal 을 통해 세션정보에 접근 가능하다.
    //@AuthenticationPrincipal UserDetails userDetails 가 기존인데, PrincipalDetails 에서 userDetails 를 상속받고 있으므로
    //PrincipalDetails 로 바꾸고,userDetails.getMember() 를 사용할 수 있다.
    public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("testLogin ============ " );
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Member member = principal.getMember();
        System.out.println("authentication.getPrincipal() = " + member);
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("member.getPassword() = " + member.getPassword());
        System.out.println("member.getEmail() = " + member.getEmail());
        System.out.println("member.getRole() = " + member.getRole());
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        System.out.println("userDetails = " + userDetails.getMember());
        return "세션 정보 확인하기";
    }
    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails oauth){
        System.out.println("testLogin ============ " );
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User = " + oAuth2User.getAttributes());
        System.out.println("oauth.getAttributes() = " + oauth.getAttributes());
        return "OAuth 세션 정보 확인하기";
    }

    // OAuth 로그인을 해도 PrincipalDetails
    // 일반 로그인을 해도 PrincipalDetails
    // 굳이 분기할 필요가 없음.
    //Authentication 은 다운캐스팅 해야하는데 PrincipalDetails 는 필요없음
    // PrincipalDetailService 와 OAuth2MemberService 둘 다 PrincipalDetails 를 리턴함. -> 리턴하면 Authentication 객체에 저장됨
    // OAuth2MemberService 는 PrincipalDetails 타입으로 리턴하기 위함과, 강제 회원가입을 진행하기 위해서 코드를 작성한 것!
    // 어노테이션이 활성화 되는 때는 함수가 종료됐을때! 각 서비스별 load 함수.
    @GetMapping("/test/oauth/security/login")
    @ResponseBody
    public String testOAuthSecurityLogin(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("testLogin ============ " );
        System.out.println("principalDetails.getMember() = " + principalDetails.getMember());
        return "OAuth 세션 정보 확인하기";
    }

    @ApiOperation(value = "저장")
    @PostMapping("/memberJoinForm")
    public String createMember(@RequestBody MemberDto member) {
        member.setRole(Role.USER.getValue());
        memberService.joinUser(member);
        return "redirect:/";
    }
    @ApiOperation(value = "회원 수정 폼")
    @GetMapping("/memberEditForm/{username}")
    public String editMember(@PathVariable Long id, Model model) {
//        Member member = memberService.findById(id);
//        model.addAttribute("member", member);
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "member/memberEditForm";
    }

    @ApiOperation(value = "회원 수정")
    @PostMapping("/memberEditForm")
    public String updateMember(@RequestBody MemberDto member) {
        memberService.updateUser(member);
        return "redirect:/";
    }
    @GetMapping("/memberLoginForm")
    public String login() {
        return "member/memberLoginForm";
    }

    @GetMapping("/memberLoginResult")
    public String loginResult(Model model) {
        return "member/memberLoginResult";
    }

    @GetMapping("/memberList")
    public String findAllMember(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "member/memberList";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();

        session.invalidate();
        return "redirect:/";
    }


}