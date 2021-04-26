package com.auto.verify;

import java.util.ArrayList;
import java.util.List;

/**
 * 装饰器模式
 */
public class CzTask {


    public static void main(String[] args) {
        List<VerifycationTask> verifycationTaskList = new ArrayList<>();
        for (VerifycationTask verifycationTask: verifycationTaskList) {
            VerifyOperationsDecorator verifyOperationsDecorator = new VerifyOperationsDecorator(VerifyFactory.getInstance().getVerifyOperations(verifycationTask.getVerifyType()));
            verifyOperationsDecorator.verification(verifycationTask);

        }


    }




}
