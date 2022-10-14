package com.mysite.sbb.question.domain;

import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.siteuser.domain.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;// 컬럼이름

    @Column(length=200) //varchar 200으로 됨
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Answer> answerList;

    //여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로 @ManyToOne 관계가 성립한다.
    @ManyToOne
    private SiteUser author;

    //추천기능
    @ManyToMany
    Set<SiteUser> voter; //Set : 중복된 값이 들어갈 수 없음.

}
