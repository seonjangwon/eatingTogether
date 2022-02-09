package com.et.eatingtogether;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.repository.StoreRepository;
import com.et.eatingtogether.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class JiwonTest {
    @Autowired
    private StoreService ss;
    @Autowired
    private StoreRepository sr;
    @Autowired
    private BigCategoryRepository bcr;
    //✔ 테스트에 앞서 의존성 주입을 진행

    @Test
    @DisplayName("회원데이터 생성")
    public void newMembers() {
    }

/*    @Test
    @DisplayName("대분류생성")
    public void AddBcList() {
        BigCategoryEntity bc1 = new BigCategoryEntity("ㅎㅎ",1L);
        BigCategoryEntity bc2 = new BigCategoryEntity("ㅋㅋ",2L);

        List<BigCategoryEntity> bcList = new ArrayList<>();
        bcr.saveAll(bcList);
        System.out.println("흠");
    }*/

    @Test
    @Transactional
    @Rollback
    //작업을 취소하고 이전의 상태로 돌림
    //로그인테스트
    public void loginTest() {
        //given 나는 가입기능이 있으니까 가입부터 진행

        //when 로그인
        //1. 이메일이 틀렸을 경우
        //2. 비밀번호가 틀렸을 경우

        //then 기능실행
        //트라이캐치
    }
}
