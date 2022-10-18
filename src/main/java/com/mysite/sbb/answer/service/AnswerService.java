package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.siteuser.domain.SiteUser;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

//0929
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;



    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setContent(content);
        //답변 저장시 작성자를 저장할 수 있도록 다음과 같이 AnswerService를 수정.
        //create 메서드에 SiteUser 객체를 추가로 전달받아 답변 저장시 author 속성에 세팅했다. 이제 답변을 작성하면 작성자도 함께 저장될 것이다.
        answer.setAuthor(author);
        this.answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        return answer.orElseThrow(() -> new DataNotFoundException("answer not found"));
//        if (answer.isPresent()) {
//            return answer.get();
//        } else {
//            throw new DataNotFoundException("answer not found");
//        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
