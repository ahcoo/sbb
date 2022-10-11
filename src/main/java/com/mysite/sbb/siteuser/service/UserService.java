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
