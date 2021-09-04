package com.auto.verify;

import java.util.HashMap;
import java.util.Map;

public class VerifyFactory {

    private static final Map<String,VerifyOperations> cachedVerifyOperations = new HashMap<>();

    private VerifyFactory(){}

    private static final VerifyFactory instance;

    static{
        instance= new VerifyFactory();
        cachedVerifyOperations.put("1",new AntiFraudVerifyOperations());
        cachedVerifyOperations.put("2",new LoanVerifyOperations());
    }


    public static VerifyFactory getInstance(){
        return instance;
    }


    public VerifyOperations getVerifyOperations(String verifyType){
        return cachedVerifyOperations.get(verifyType);
    }

}
