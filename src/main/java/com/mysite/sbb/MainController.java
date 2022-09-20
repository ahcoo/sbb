package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody //반환할 값을 문자열로 만들어서 보내겠다는 뜻.

    public String index() {

        System.out.println("sbb");

        return "sbb";

    }

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

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(value="age", defaultValue="0") int age) {

        return """
             <h1>입력된 나이 : %d</h1>
             <h1>POST방식으로 옴</h1>
             """.formatted(age);
    }

}