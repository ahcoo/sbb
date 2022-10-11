package com.mysite.sbb.siteuser.service;

import com.mysite.sbb.siteuser.dao.UserRepository;
import com.mysite.sbb.siteuser.domain.SiteUser;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {

        SiteUser user = SiteUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);
        return user;
    }

// AnswerController에서 principal 객체를 사용하면 이제 로그인한 사용자의 사용자명을 알수 있으므로 사용자명을 통해 SiteUser객체를 조회할 수 있다.
// 먼저 User 서비스를 통해 SiteUser를 조회할 수 있는 getUser 메서드를 UserService에 추가.
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByusername(username);

        return siteUser.orElseThrow(()-> new DataNotFoundException("siteuser not found"));
        //아래를 줄여서 위처럼 쓸 수 있음. 유저가 데이터베이스에 없을 때 에러 호출, 있으면 유저 호출.
//        if(siteUser.isPresent()) {
//            return siteUser.get();
//        } else {
//            throw new DataNotFoundException("siteuser not found");
//        }
    }
}
