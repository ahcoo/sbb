package com.mysite.sbb;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


//서버를 시작하지 않고 테스트해볼 수 있음.

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository; //변수 만듦

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	/* 0923수업 */
	/* TDD */
	@Test
	void getQuestions() {

		//SELECT * FROM question;을 JPA에 내장되어있는 명령어(findAll())로 가져옴.
		List<Question> all = questionRepository.findAll();
		//불러온 값(데이터 수)과 찾는 값(데이터 수)이 같은지 체크(**.size()). 1 → 오류. why? 실제 데이터와 값이 다르기 때문.
		assertEquals(2, all.size());

		//원하는 데이터 값이 있는지 검색(*.getSubject())
		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void getQuestionById() {
		//Optional
		Optional<Question> oq = questionRepository.findById(1);

		//oq에 값이 있다면 = oq.isPresent()
		if ( oq.isPresent() ) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void getQuestionBySubject() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	void getQuestionsBySubject() {
		List<Question> questions = questionRepository.findAllBySubject("sbb가 무엇인가요?");
		assertEquals(1, questions.size());
	}

	@Test
	void getQuestionsByTwoSubjects() {
		List<String> searchWordList = new ArrayList<>();
		searchWordList.add("sbb가 무엇인가요?");
		searchWordList.add("스프링부트 모델 질문입니다.");
		List<Question> questions = this.questionRepository.findAllBySubjectIn(searchWordList);

		assertEquals(4, questions.size());
	}


}
