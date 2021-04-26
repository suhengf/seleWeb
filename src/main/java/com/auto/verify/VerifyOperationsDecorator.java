package com.auto.verify;

public class VerifyOperationsDecorator extends  AbstractVerifyOperations{


    public VerifyOperationsDecorator(VerifyOperations verifyOperations) {
        super(verifyOperations);
    }

    @Override
    public VerifyEnum verify(VerifycationTask verifycationTask) {
        return verifyOperations.verify(verifycationTask);
    }

    @Override
    public void process(String refNo, VerifyEnum verifyEnum) {
        verifyOperations.process(refNo,verifyEnum);
    }
}
