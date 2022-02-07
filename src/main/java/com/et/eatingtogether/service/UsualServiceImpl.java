package com.et.eatingtogether.service;

import com.et.eatingtogether.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsualServiceImpl implements UsualService{
    private final CustomerRepository cr;


}
