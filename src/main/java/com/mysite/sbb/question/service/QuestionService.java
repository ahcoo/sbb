package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.siteuser.domain.SiteUser;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    //사용하려면 @RequiredArgsConstructor 주입
    private final QuestionRepository questionRepository;

    //전체 조회
    public List<Question> getList() {



        return questionRepository.findAll();
    }
    //단건 조회
    public Question getQuestion(Integer id) {

//        Optional<Question> question = this.questionRepository.findById(id);
//        if (question.isPresent()) {
//            return question.get();
//        } else {
//            throw new DataNotFoundException("question not found");
//        }

        /* 위 구문을 아래처럼 짧게 바꿀 수 있음. */

        return questionRepository.findById(id).orElseThrow(()->new DataNotFoundException("question not found"));

    }


    //question_form.html에서 작성한 질문을 sql에 등록
    public void create(String subject, String content, SiteUser siteUser) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        //먼저 작성자 정보를 저장하기 위해 QuestionService를 다음과 같이 수정
        q.setAuthor(siteUser);
        questionRepository.save(q);
    }

    //1페이지가 몇 칸인지 → size : 10
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
}
