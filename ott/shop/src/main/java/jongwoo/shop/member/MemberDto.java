package jongwoo.shop.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberDto {


    private Long id;

    private String username;

    private String password;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    @Builder
    public MemberDto(String username, String password, String email, String role,String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

//    public Role setRole(Role role) {
//        this.role = role;
//        return role;
//    }
}