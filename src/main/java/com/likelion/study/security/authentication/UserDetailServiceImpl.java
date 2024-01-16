package com.likelion.study.security.authentication;

import com.likelion.study.dao.UserRepository;
import com.likelion.study.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService { // Implementation
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.debug("loadUserByUsername userId : {}", userId);
        System.out.println("loadUserByUsername userId : " + userId);
        /* Stream을 사용한 방식 */
//        return userRepository.findById(Long.parseLong(userId))
//                .map(CustomUserDetails::of)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//        UserDetails userDetails = new CustomUserDetails(user); // 이렇게 써도 되고
        log.info("loadUserByUsername user : {}", user);
        UserDetails userDetails = CustomUserDetails.from(user); // 이렇게 써도 됨
        log.info("loadUserByUsername userDetails : {}", userDetails);
        return userDetails;
    }
}
