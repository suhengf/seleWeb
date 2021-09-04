package com.auto.verify;

/**
 * 查证接口类
 */
public interface VerifyOperations {

    VerifyEnum verify(VerifycationTask verifycationTask);

    void process(String refNo, VerifyEnum verifyEnum);

}
