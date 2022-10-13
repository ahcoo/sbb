package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.siteuser.domain.SiteUser;
import com.mysite.sbb.siteuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

//0929
@RequestMapping("/answer")
//메소드상관없이 처리
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    //포스트요청으로 온것만 처리
    //질문, 답변에 글쓴이를 추가한다는 느낌으로 작업을 진행하자. >> Principal principal
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        //principal.getName()을 호출하면 현재 로그인한 사용자의 사용자명(사용자ID)을 알수 있다.
        //principal 객체를 통해 사용자명을 얻은 후에 사용자명을 통해 SiteUser 객체를 얻어서 답변을 등록하는 AnswerService의 create 메서드에 전달하여 답변을 저장하도록 했다.
        SiteUser siteUser = userService.getUser(principal.getName());
        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, @AuthenticationPrincipal SiteUser siteUser) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_Form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, @AuthenticationPrincipal SiteUser siteUser) {
        if(bindingResult.hasErrors()) {
            return "answer_Form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(@AuthenticationPrincipal SiteUser siteUser, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

}
