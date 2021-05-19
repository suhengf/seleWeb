package com.auto.service.core.housework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ChineseCourse extends EcnusoleUniversityCommanAnswerHandler{
    @Override
    public void loadAnswer(Map<Integer, List<String>> titleAnswer) {
        //专题一  D
        //A
        //D
        //B
        //C
        //ABCD
        List<String> answer1 = new ArrayList<>();
        answer1.add("D");
        answer1.add("A");
        answer1.add("D");
        answer1.add("B");
        answer1.add("C");
        answer1.add("ABCD");
        titleAnswer.put(1,answer1);

        //专题二C
        //D
        //A
        //D
        //ABC
        //BC
        List<String> answer2 = new ArrayList<>();
        answer2.add("C");
        answer2.add("D");
        answer2.add("A");
        answer2.add("D");
        answer2.add("ABC");
        answer2.add("BC");
        titleAnswer.put(2,answer2);

        //专题三
        List<String> answer3 = new ArrayList<>();
        answer3.add("C");
        answer3.add("C");
        answer3.add("C");
        answer3.add("D");
        answer3.add("A");
        titleAnswer.put(3,answer3);

        //专题四D
        //D
        //A
        //B
        //B
        //ABD
        List<String> answer4 = new ArrayList<>();
        answer4.add("D");
        answer4.add("D");
        answer4.add("A");
        answer4.add("B");
        answer4.add("B");
        answer4.add("ABD");
        titleAnswer.put(4,answer4);

        //专题五 D
        //C
        //B
        //D
        //B
        List<String> answer5 = new ArrayList<>();
        answer5.add("D");
        answer5.add("C");
        answer5.add("B");
        answer5.add("D");
        answer5.add("B");
        titleAnswer.put(5,answer5);

        //专题六B
        //C
        //C
        //C
        //D
        List<String> answer61 = new ArrayList<>();
        answer61.add("B");
        answer61.add("C");
        answer61.add("C");
        answer61.add("C");
        answer61.add("D");
        titleAnswer.put(6,answer61);

        List<String> answer7 = new ArrayList<>();
        answer7.add("A");
        answer7.add("A");
        answer7.add("C");
        answer7.add("AC");
        answer7.add("ABCD");
        titleAnswer.put(7,answer7);

        /**
         * C
         * D
         * A
         * AD
         * AB
         * ABCD
         */
        List<String> answer8 = new ArrayList<>();
        answer8.add("C");
        answer8.add("D");
        answer8.add("A");
        answer8.add("AD");
        answer8.add("AB");
        answer8.add("ABCD");
        titleAnswer.put(8,answer8);

        /**
         * B
         * D
         * C
         * D
         * BC
         */
        List<String> answer9 = new ArrayList<>();
        answer9.add("B");
        answer9.add("D");
        answer9.add("C");
        answer9.add("D");
        answer9.add("BC");
        titleAnswer.put(9,answer9);
        /**
         * B
         * D
         * C
         * D
         * BC
         */
        List<String> answer10 = new ArrayList<>();
        answer10.add("B");
        answer10.add("D");
        answer10.add("C");
        answer10.add("D");
        answer10.add("BC");
        titleAnswer.put(10,answer9);
        /**
         * B
         * D
         * C
         * D
         * BC
         */
        List<String> answer11 = new ArrayList<>();
        answer11.add("B");
        answer11.add("D");
        answer11.add("C");
        answer11.add("D");
        answer11.add("BC");

        titleAnswer.put(11,answer9);
    }

    @Override
    public CourseNameEnum courseName() {
        return CourseNameEnum.ChineseCourse;
    }
}
