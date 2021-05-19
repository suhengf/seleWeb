package com.auto.service.core.housework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class EnlishCourse extends EcnusoleUniversityCommanAnswerHandler{


    @Override
    public void loadAnswer(Map<Integer, List<String>> titleAnswer) {

    }

    @Override
    public CourseNameEnum courseName() {
        return CourseNameEnum.EnlishCourse;
    }
}
