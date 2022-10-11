package com.mysite.sbb.question.domain;

import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.siteuser.domain.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;




/*                                 entity                                 */
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Answer> answerList;

    //로그인 되어야 함.
    @ManyToOne
    private SiteUser author;


}
