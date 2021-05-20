package com.auto.service.core.housework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PreEducationCourse  extends EcnusoleUniversityCommanAnswerHandler{



    @Override
    public void loadAnswer(Map<Integer, List<String>> titleAnswer) {
        //专题一  D
        //A
        //D
        //B
        //C
        //ABCD
        List<String> answer1 = new ArrayList<>();
        answer1.add("A");
        answer1.add("C");
        answer1.add("D");
        answer1.add("C");
        answer1.add("C");
        answer1.add("B");
        titleAnswer.put(1,answer1);


        List<String> answer2 = new ArrayList<>();
        answer2.add("C");
        answer2.add("A");
        answer2.add("A");
        answer2.add("A");
        answer2.add("ABCD");
        answer2.add("×");
        answer2.add("√");
        answer2.add("×");
        answer2.add("×");
        answer2.add("×");
        titleAnswer.put(2,answer2);

        //专题三
        List<String> answer3 = new ArrayList<>();
        answer3.add("C");
        answer3.add("B");
        answer3.add("A");
        answer3.add("A");
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
        answer4.add("ABC");
        answer4.add("ABCD");
        answer4.add("ABD");
        answer4.add("×");
        answer4.add("×");
        answer4.add("×");
        answer4.add("×");
        titleAnswer.put(4,answer4);

        //专题五 D
        //C
        //B
        //D
        //B
        List<String> answer5 = new ArrayList<>();
        answer5.add("C");
        answer5.add("D");
        answer5.add("A");
        answer5.add("A");
        answer5.add("×");
        answer5.add("×");
        titleAnswer.put(5,answer5);

        //专题六B
        //C
        //C
        //C
        //D
        List<String> answer6 = new ArrayList<>();
        answer6.add("B");
        answer6.add("B");
        answer6.add("A");
        answer6.add("A");
        answer6.add("A");
        answer6.add("×");
        answer6.add("√");
        titleAnswer.put(6,answer6);

        List<String> answer7 = new ArrayList<>();
        answer7.add("A");
        answer7.add("A");
        answer7.add("A");
        answer7.add("A");
        answer7.add("A");
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
        answer8.add("A");
        answer8.add("A");
        answer8.add("D");
        answer8.add("C");
        answer8.add("D");
        answer8.add("×");
        answer8.add("×");
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
        answer9.add("B");
        answer9.add("A");
        answer9.add("C");
        answer9.add("x");
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
        answer10.add("C");
        answer10.add("D");
        answer10.add("A");
        answer10.add("√");
        titleAnswer.put(10,answer10);

    }

    @Override
    public CourseNameEnum courseName() {
        return CourseNameEnum.PreEducationCourse;
    }
}
