package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.siteuser.domain.SiteUser;
import com.mysite.sbb.siteuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {

    //questionRepository = null;이 들어있음.
//    private final QuestionRepository questionRepository;

    private final QuestionService questionService;

    private final UserService userService;

//0928
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {

        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

//0929
    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, @AuthenticationPrincipal SiteUser siteUser, Principal principal) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    //주소로 갈때 인증되어있는지 확인
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        //ToDo질문 저장
//        System.out.println("subject: "+ subject); //question_form의 id와 같아야 함.
//        System.out.println("content: "+ content); //question_form의 id와 같아야 함.
        if ( bindingResult.hasErrors() ) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/list"; //질문 저장 후 질문 목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, @AuthenticationPrincipal SiteUser siteUser) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @AuthenticationPrincipal SiteUser siteUser, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(@AuthenticationPrincipal SiteUser siteUser, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(siteUser.getUsername()) && !(siteUser.getRole().equals("Role_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(@AuthenticationPrincipal SiteUser siteUser, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser _siteUser = this.userService.getUser(siteUser.getUsername());
        this.questionService.vote(question, _siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}


