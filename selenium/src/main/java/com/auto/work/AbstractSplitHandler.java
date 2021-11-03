package com.auto.work;

import java.util.List;

public abstract class AbstractSplitHandler {

    public void handle(){
        querySplitList(null,null,null);
    }

    abstract List<String> querySplitList(Integer fixNum, String startDate, String endDate);


}
