package com.et.eatingtogether;

import com.et.eatingtogether.repository.StoreRepository;
import com.et.eatingtogether.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JiwonTest {
    @Autowired
    private StoreService ss;
    @Autowired
    private StoreRepository sr;
}
