package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody //반환할 값을 문자열로 만들어서 보내겠다는 뜻.

    public String index() {

        System.out.println("sbb");

        return "sbb";

    }
// 0920 수업시작
    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {

        return """
                <form method="POST" action="/page2">
                <input type='number' name="age" placeholder="나이" />
                <input type="submit" value="page2로 POST방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(value="age", defaultValue="0") int age) {
        System.out.println("age : " + age);
     return """
             <h1>입력된 나이 : %d</h1>
             <h1>POST방식으로 옴</h1>
             """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(value="age", defaultValue="0") int age) {

        return """
             <h1>입력된 나이 : %d</h1>
             <h1>POST방식으로 옴</h1>
             """.formatted(age);

    }

//    @GetMapping("/plus")
//    @ResponseBody
//    public int plus(@RequestParam int a, @RequestParam int b) {
//        int c = a + b;
//        return c;
//    }

    @GetMapping("/plus")
    @ResponseBody
    public String plus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
        if ( a == null ) {
            return "a를 입력해주세요.";
        }
        if ( b == null ) {
            return "b를 입력해주세요.";
        }
        return String.valueOf(a+b);
    }

//    @GetMapping("/minus")
//    @ResponseBody
//    public int minus(@RequestParam int a, @RequestParam int b) {
//        int c = a - b;
//        return c;
//    }

    @GetMapping("/minus")
    @ResponseBody
    public String minus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
        if ( a == null ) {
            return "a를 입력해주세요.";
        }
        if ( b == null ) {
            return "b를 입력해주세요.";
        }
        return String.valueOf(a-b);
    }
//    int num;
//    @GetMapping("/increase")
//    @ResponseBody
//    public int increase() {
//                num++;
//                return num;
//    }

    int increaseNum;
    @GetMapping("/increase")
    @ResponseBody
    public int increase() {

        return increaseNum++;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(int dan, int limit) {
//        String result = "";
//        for ( int i = 1; i <= limit; i++ ) {
//            result += dan + " * " + i + " = " + (dan * i) + "<br/>\n";
//            }
//        return result;
//
//    }
        final Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>\n"));
    }
    // 0920 수업끝



    // 0921 수업 시작
    @GetMapping("/saveSessionAge")
    @ResponseBody
//    public String saveSession(@RequestParam("age") int age, HttpServletRequest req) {
//        HttpSession session = req.getSession();
//        session.setAttribute("age", age);
//
//    } 아래로 대체 가능

    public String saveSession(@RequestParam("age") int age, HttpSession session) {

       session.setAttribute("age", age);

       return "나이 %d이(가) 세션에 저장되었습니다.".formatted(age);
    }

//    @GetMapping("/getSessionAge")
//    @ResponseBody
//    public String saveSession(HttpSession session) {
//
//        int age = (int)session.getAttribute("age");
//
//        return "세션에 저장된 나이는 %d입니다.".formatted(age);
//    }


    //쿠키 조회까지
    @GetMapping("/getSessionAge")
    @ResponseBody
    public String saveSession(HttpSession session, HttpServletResponse res) {

        int age = (int)session.getAttribute("age");
        Cookie cookie = new Cookie("age", String.valueOf(age));
        res.addCookie(cookie);

        return "세션에 저장된 나이는 %d입니다.".formatted(age);
    }
    //0921 수업 끝

}

