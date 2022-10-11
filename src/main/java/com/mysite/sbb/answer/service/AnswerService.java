package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.siteuser.domain.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//0929
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;



    public void create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setContent(content);
        //답변 저장시 작성자를 저장할 수 있도록 다음과 같이 AnswerService를 수정.
        //create 메서드에 SiteUser 객체를 추가로 전달받아 답변 저장시 author 속성에 세팅했다. 이제 답변을 작성하면 작성자도 함께 저장될 것이다.
        answer.setAuthor(author);
        answerRepository.save(answer);
    }
}
