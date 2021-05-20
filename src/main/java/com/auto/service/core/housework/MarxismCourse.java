package com.auto.service.core.housework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MarxismCourse extends EcnusoleUniversityCommanAnswerHandler{

    @Override
    public void loadAnswer(Map<Integer, List<String>> titleAnswer){
        //专题一
        List<String> answer1 = new ArrayList<>();
        answer1.add("D");
        answer1.add("A");
        answer1.add("B");
        answer1.add("ABC");
        answer1.add("ABC");
        answer1.add("ABCD");
        titleAnswer.put(1,answer1);

        //专题二
        List<String> answer2 = new ArrayList<>();
        answer2.add("B");
        answer2.add("A");
        answer2.add("B");
        answer2.add("B");
        answer2.add("A");
        answer2.add("B");
        answer2.add("ABC");
        answer2.add("ABC");
        titleAnswer.put(2,answer2);

        //专题三
        List<String> answer3 = new ArrayList<>();
        answer3.add("B");
        answer3.add("A");
        answer3.add("D");
        answer3.add("B");
        answer3.add("ABC");
        titleAnswer.put(3,answer3);

        //专题四
        List<String> answer4 = new ArrayList<>();
        answer4.add("B");
        answer4.add("D");
        answer4.add("D");
        answer4.add("ABCD");
        answer4.add("ABCD");
        titleAnswer.put(4,answer4);

        //专题五
        List<String> answer5 = new ArrayList<>();
        answer5.add("B");
        answer5.add("A");
        answer5.add("A");
        answer5.add("B");
        answer5.add("√");
        answer5.add("×");
        answer5.add("√");
        answer5.add("×");
        answer5.add("×");
        titleAnswer.put(5,answer5);

        //专题六
        List<String> answer61 = new ArrayList<>();
        answer61.add("C");
        answer61.add("A");
        answer61.add("B");
        answer61.add("D");
        answer61.add("C");
        answer61.add("B");
        answer61.add("D");
        answer61.add("B");
        answer61.add("D");
        titleAnswer.put(61,answer61);

        List<String> answer62 = new ArrayList<>();
        answer62.add("A");
        answer62.add("C");
        answer62.add("D");
        answer62.add("B");
        answer62.add("D");
        answer62.add("C");
        answer62.add("B");
        answer62.add("A");
        answer62.add("D");
        answer62.add("D");
        titleAnswer.put(62,answer62);

        List<String> answer63 = new ArrayList<>();
        answer63.add("A");
        answer63.add("B");
        answer63.add("B");
        answer63.add("B");
        answer63.add("A");
        answer63.add("A");
        answer63.add("B");
        answer63.add("B");
        answer63.add("D");
        answer63.add("B");
        titleAnswer.put(63,answer63);


        List<String> answer64 = new ArrayList<>();
        answer64.add("B");
        answer64.add("B");
        answer64.add("D");
        answer64.add("B");
        answer64.add("C");
        answer64.add("C");
        answer64.add("D");
        answer64.add("A");
        answer64.add("C");
        answer64.add("D");
        titleAnswer.put(64,answer64);

    }

    @Override
    public  CourseNameEnum courseName() {
        return CourseNameEnum.MarxismCourse;
    }

}
