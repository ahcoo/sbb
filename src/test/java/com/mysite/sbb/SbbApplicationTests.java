package com.mysite.sbb;

import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//서버를 시작하지 않고 테스트해볼 수 있음.

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository; //변수 만듦

	@Autowired
	private AnswerRepository answerRepository;

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
		if (oq.isPresent()) {
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
	/*0923 수업 끝*/


	/*0926 수업*/

	/* Subject와 Content 둘 다 만족하는 값 찾기 */
	@Test
	void getQuestionsBySubjectAndContent() {
		List<Question> questions = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
		assertEquals(2, questions.size());
	}

	/* Subject 내용이 뭔가를 포함하는 값 찾기 */
	@Test
	void getSubjectAndSomething() {
		/*
		* abc% = abc로 시작하는 것
		* %abc% = abc를 포함하는 것
		* */

		List<Question> questions = this.questionRepository.findBySubjectLike("sbb%");
		assertEquals(2, questions.size());
	}

	/* 질문 수정하기 */
	@Test
	void updateQuestion() {
		Optional<Question> oq = questionRepository.findById(2);
		if ( oq.isPresent() ) {
			Question question = oq.get();
			question.setSubject("수정된 질문");
			question.setContent("수정된 내용");
			questionRepository.save(question);
		}
	}

	/* 질문 삭제하기 */
	@Test
	void deleteQuestion() {
		assertEquals(4, questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		if ( oq.isPresent() ) {
			Question question = oq.get();
			questionRepository.delete(question);
		}
		assertEquals(3, questionRepository.count());
	}

	/*답변 만들기*/
	@Test
	void createAnswer() {
		Optional<Question> oq = questionRepository.findById(2);
		if ( oq.isPresent() ) {
			Question question = oq.get();

			Answer answer = new Answer();
			answer.setContent("답변입니다");
			answer.setQuestion(question);
			answer.setCreateDate(LocalDateTime.now());
			answerRepository.save(answer);
		}

	}

	/*답변 조회하기*/
	@Test
	void searchAnswer() {
		Optional<Answer> oa = answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	void getAnswersByQuestion() {
		Optional<Question> oq = questionRepository.findById(2);
		if (oq.isPresent()) {
			Question question = oq.get();
			List<Answer> answerList = question.getAnswerList();
			assertEquals(1, answerList.size());
		}
	}
	//fetchType:Lazyloading → Question.java의 @OneToMany()에 fetch = FetchType.EAGER 추가

	/*0926 수업 끝*/

	@Test
	void getQuestionsByLike() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}


	@Test
	void QuestionEdit() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q1 = oq.get();
			q1.setSubject("수정된 제목");
			this.questionRepository.save(q1);
		}
	}

	@Test
	void deleteQuestions() {
		//Repository.count()는 해당 리포지터리의 총 데이터건수를 리턴함.
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			this.questionRepository.delete(q);
			assertEquals(1, this.questionRepository.count());
		}
	}


	@Autowired
	private QuestionService questionService;

	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}


}
