package com.dataorb.api.impl;

import java.util.List;
import java.util.Map;

public interface DataService {

    void processData(List<String> fileLines);
    Map<String, Object> getRecords();
}
