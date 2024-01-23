package com.spring.board.controller.board;

import com.spring.board.controller.SessionConst;
import com.spring.board.entity.Board;
import com.spring.board.entity.User;
import com.spring.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 전체 조회
     * */
    @GetMapping
    public String postList(Model model) {
        model.addAttribute("list", boardService.findAll());
        return "board/postList";
    }

    /**
     * 게시글 상세 조회
     * */
    @GetMapping("/{postId}")
    public String postView(@PathVariable Long postId, Model model) {
        log.info("postView");

        Board post = boardService.findOne(postId).orElseThrow();
        model.addAttribute("post", post);

        return "board/post";
    }

    /**
     * 게시글 등록 폼 화면
     * */
    @GetMapping("/register")
    public String registerForm(@ModelAttribute PostForm postForm) {
        return "board/registerForm";
    }

    /**
     * 게시글 등록
     * */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult
        , @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "board/registerForm";
        }

        boardService.register(postForm.getTitle(), postForm.getContent(), loginUser.getId());

        return "redirect:/board";
    }

    /**
     * 게시글 수정 폼
     * */
    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {

        Board post = boardService.findOne(postId).orElseThrow();

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", postId);

        return "board/editForm";
    }

    /**
     * 게시글 수정 
     * */
    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId, @Valid @ModelAttribute PostForm postForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "board/editForm";
        }
        
        boardService.updateBoard(postId, postForm.getTitle(), postForm.getContent());
        
        return "redirect:/board/{postId}";
    }
    
    /**
     * 게시글 삭제
     * */
    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        boardService.deleteById(postId);
        return "redirect:/board";
    }
}
