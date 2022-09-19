package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody //반환할 값을 문자열로 만들어서 보내겠다는 뜻.

    public String index() {

        System.out.println("sbb");

        return "sbb";

    }
}