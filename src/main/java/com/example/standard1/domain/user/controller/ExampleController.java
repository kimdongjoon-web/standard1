package com.example.standard1.domain.user.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) { // 뷰로 데이터를 넘겨주는 모델 객체
                                                  // import springframework.ui.Model
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        // "person"이라는 키에 사람 정보, "today"라는 키에 날짜 정보 저장
        model.addAttribute("person", examplePerson); // Person 객체 저장
        model.addAttribute("today", LocalDate.now());

        /**
         * [ 클래스 수준 애너테이션이 Controller이므로 뷰의 이름을 반환 ]
         *
         * 스프링 부트는 컨트롤러의 @Controller 애너테이션을 보고
         * 반환하는 값의 이름을 가진 뷰 파일을 찾으라는 것을 인지한다.
         *
         * resource/templates 디렉터리에서 example.html을 찾은 다음
         * 웹 브라우저에서 해당 파일을 보여준다.
         */
        return "example"; // example.html라는 뷰 조회
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
