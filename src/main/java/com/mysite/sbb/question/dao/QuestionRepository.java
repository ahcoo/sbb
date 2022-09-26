package com.mysite.sbb.question.dao;

import com.mysite.sbb.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String s);

    List<Question> findAllBySubject(String s);

    List<Question> findAllBySubjectIn(List<String> searchWordList);

    List<Question> findBySubjectLike(String subject);
<<<<<<< HEAD


    List<Question> findBySubjectAndContent(String subject, String content);
=======
>>>>>>> 4926c816653a0a97b799e80cff198b580f9ede8e
}

