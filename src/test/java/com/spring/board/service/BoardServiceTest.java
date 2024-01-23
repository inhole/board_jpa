package com.spring.board.service;

import com.spring.board.entity.Board;
import com.spring.board.entity.User;
import com.spring.board.repository.BoardRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;
    @Autowired EntityManager em;

    @Test
    public void register() {
        //given
        User user = User.builder().loginId("userA").build();
        em.persist(user);

        //when
        Long registerId = boardService.register("AAA", "BBB", user.getId());

        //then
        Board board = boardRepository.findById(registerId).orElseThrow();

        assertThat(board.getTitle()).isEqualTo("AAA");
        assertThat(board.getContent()).isEqualTo("BBB");
        assertThat(board.getUser()).isEqualTo(user);
    }

    @Test
    public void updateBoard() {
        //given
        User user = User.builder().loginId("userA").build();
        em.persist(user);
        Long registerId = boardService.register("AAA", "BBB", user.getId());

        //when
        boardService.updateBoard(registerId, "CCC", "DDD");

        //then
        Board board = boardRepository.findById(registerId).orElseThrow();

        assertThat(board.getTitle()).isEqualTo("CCC");
        assertThat(board.getContent()).isEqualTo("DDD");
        assertThat(board.getUser()).isEqualTo(user);
    }
}