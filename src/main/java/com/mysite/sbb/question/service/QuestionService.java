package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
