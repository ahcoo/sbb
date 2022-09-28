package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

//
//
//    @RequestMapping("/sbb")
//    @ResponseBody //반환할 값을 문자열로 만들어서 보내겠다는 뜻.
//
//    public String index() {
//
//        System.out.println("sbb");
//
//        return "sbb";
//
//    }
//
//    // 0920 수업시작
//    @GetMapping("/page1")
//    @ResponseBody
//    public String showPage1() {
//
//        return """
//                <form method="POST" action="/page2">
//                <input type='number' name="age" placeholder="나이" />
//                <input type="submit" value="page2로 POST방식으로 이동" />
//                </form>
//                """;
//    }
//
//    @PostMapping("/page2")
//    @ResponseBody
//    public String showPage2Post(@RequestParam(value = "age", defaultValue = "0") int age) {
//        System.out.println("age : " + age);
//        return """
//                <h1>입력된 나이 : %d</h1>
//                <h1>POST방식으로 옴</h1>
//                """.formatted(age);
//    }
//
//    @GetMapping("/page2")
//    @ResponseBody
//    public String showPage2Get(@RequestParam(value = "age", defaultValue = "0") int age) {
//
//        return """
//                <h1>입력된 나이 : %d</h1>
//                <h1>POST방식으로 옴</h1>
//                """.formatted(age);
//
//    }
//
////    @GetMapping("/plus")
////    @ResponseBody
////    public int plus(@RequestParam int a, @RequestParam int b) {
////        int c = a + b;
////        return c;
////    }
//
//    @GetMapping("/plus")
//    @ResponseBody
//    public String plus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
//        if (a == null) {
//            return "a를 입력해주세요.";
//        }
//        if (b == null) {
//            return "b를 입력해주세요.";
//        }
//        return String.valueOf(a + b);
//    }
//
////    @GetMapping("/minus")
////    @ResponseBody
////    public int minus(@RequestParam int a, @RequestParam int b) {
////        int c = a - b;
////        return c;
////    }
//
//    @GetMapping("/minus")
//    @ResponseBody
//    public String minus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
//        if (a == null) {
//            return "a를 입력해주세요.";
//        }
//        if (b == null) {
//            return "b를 입력해주세요.";
//        }
//        return String.valueOf(a - b);
//    }
////    int num;
////    @GetMapping("/increase")
////    @ResponseBody
////    public int increase() {
////                num++;
////                return num;
////    }
//
////    int increaseNum;
//int increaseNum = -1;
//
//    @GetMapping("/increase")
//    @ResponseBody
//    public int increase() {
//
//        return increaseNum++;
//    }
//
//    @GetMapping("/gugudan")
//    @ResponseBody
//    public String gugudan(int dan, int limit) {
////        String result = "";
////        for ( int i = 1; i <= limit; i++ ) {
////            result += dan + " * " + i + " = " + (dan * i) + "<br/>\n";
////            }
////        return result;
////
////    }
//        final Integer finalDan = dan;
//        return IntStream.rangeClosed(1, limit)
//                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
//                .collect(Collectors.joining("<br>\n"));
//    }
//    // 0920 수업끝
//
//
//    // 0921 수업 시작
//    @GetMapping("/saveSessionAge")
//    @ResponseBody
////    public String saveSession(@RequestParam("age") int age, HttpServletRequest req) {
////        HttpSession session = req.getSession();
////        session.setAttribute("age", age);
////
////    } 아래로 대체 가능
//
//    public String saveSession(@RequestParam("age") int age, HttpSession session) {
//
//        session.setAttribute("age", age);
//
//        return "나이 %d이(가) 세션에 저장되었습니다.".formatted(age);
//    }
//
////    @GetMapping("/getSessionAge")
////    @ResponseBody
////    public String saveSession(HttpSession session) {
////
////        int age = (int)session.getAttribute("age");
////
////        return "세션에 저장된 나이는 %d입니다.".formatted(age);
////    }
//
//
//    //쿠키 조회까지
//    @GetMapping("/getSessionAge")
//    @ResponseBody
//    public String saveSession(HttpSession session, HttpServletResponse res) {
//
//        int age = (int) session.getAttribute("age");
//        Cookie cookie = new Cookie("age", String.valueOf(age));
//        res.addCookie(cookie);
//
//        return "세션에 저장된 나이는 %d입니다.".formatted(age);
//    }
//    //0921 수업 끝
//
//    //0922 수업
////    @GetMapping("/addPerson")
////    @ResponseBody
////    public Person addPerson(int id, int age, String name) {
////        Person p = new Person(id, age, name);
////
////        return p;
////    }
////
////
////
//////    @Getter
////    class Person {
////        private int id;
////        private int age;
////        private String name;
////
////        /* @Getter가 아래 기능을 구현함 */
////        public int getId() {
////            return id;
////        }
////
////        public int getAge() {
////            return age;
////        }
////
////        public String getName() {
////            return name;
////        }
////        /* @Getter */
////        public Person(int id, int age, String name) {
////            this.id = id;
////            this.age = age;
////            this.name = name;
////        }
////    }
//
///*  ============================================================================================================  */
//    /*
//
//    *****     public Person addPerson(매개변수 없애고 구현하기) {}       *****
//
//    */
////    @GetMapping("/addPerson")
////    @ResponseBody
////    public void addPerson(Person person) {
////
////        System.out.println(person.getName()); //이렇게 넣어도 스프링부트가 알아서 조립하여 넣어줌.(Tom출력)
////
////    }
//
//
//
////    public Person addPerson(Person person) {
////
////        return person;
////    }
////
////
////
////    //    @Getter
////    class Person {
////        private int id;
////        private int age;
////        private String name;
////
////        /* @Getter가 아래 기능을 구현함 */
////        public int getId() {
////            return id;
////        }
////
////        public int getAge() {
////            return age;
////        }
////
////        public String getName() {
////            return name;
////        }
////        /* @Getter */
////        public Person(int id, int age, String name) {
////            this.id = id;
////            this.age = age;
////            this.name = name;
////        }
//
// /*  ===================================================================================  */
//
///*    ******    http://localhost:8082/addPerson?id=1&age=25&name=tom를
//                http://localhost:8082/addPerson/1&age=25&name=tom 로 받게 하기.    ******         */
//
//
//    @GetMapping("addPerson/{id}")
//    @ResponseBody
//    public Person addPerson(Person person, @PathVariable("id")Integer id) {
//
//        return person;
//    }
//}
//
//            @Getter
//        class Person {
//            private int id;
//            private int age;
//            private String name;
//
//
//
//            public Person(int id, int age, String name) {
//                this.id = id;
//                this.age = age;
//                this.name = name;
//            }
//
// /*  ===================================================================================  */
//
//
//@Autowired
//private QuestionRepository questionRepository;
//
//    @GetMapping("/createQuestion")
//    @ResponseBody
//        public List<Question> createQuestion() {
//            Question q1 = new Question();
//            q1.setSubject("sbb가 무엇인가요?");
//            q1.setContent("sbb에 대해서 알고 싶습니다.");
//            q1.setCreateDate(LocalDateTime.now());
//            this.questionRepository.save(q1);  // 첫번째 질문 저장
//
//            Question q2 = new Question();
//            q2.setSubject("스프링부트 모델 질문입니다.");
//            q2.setContent("id는 자동으로 생성되나요?");
//            q2.setCreateDate(LocalDateTime.now());
//            this.questionRepository.save(q2);  // 두번째 질문 저장
//
//            return questionRepository.findAll();
//        }
//     /*  ===================================================================================  */
//                //0922 수업 끝
@RequestMapping("/")
public String root() {
    return "redirect:/question/list";
}

}

