package com.spring.board.service;

import com.spring.board.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void test() {
        //given
        User user = User.builder().loginId("userA").build();

        //when
        Long joinId = userService.join(user);

        //then
        assertThat(user).isEqualTo(userService.findOne(joinId).orElseThrow());
    }

    @Test
    void test2() throws Exception {
        //given
        User user1 = User.builder().loginId("userA").build();
        User user2 = User.builder().loginId("userA").build();

        //when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

}