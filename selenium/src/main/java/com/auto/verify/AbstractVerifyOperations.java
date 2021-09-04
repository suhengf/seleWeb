package com.auto.verify;

public abstract class AbstractVerifyOperations implements  VerifyOperations{
    public VerifyOperations verifyOperations;

    public AbstractVerifyOperations(VerifyOperations verifyOperations){
        this.verifyOperations=verifyOperations;
    }


    public void verification(VerifycationTask verifycationTask){
        verifyOperations.process(verifycationTask.getVerifyId(),verifyOperations.verify(verifycationTask));
    }


}
