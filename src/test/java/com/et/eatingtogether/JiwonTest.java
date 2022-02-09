package com.et.eatingtogether;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.StoreRepository;
import com.et.eatingtogether.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootTest
public class JiwonTest {
    @Autowired
    private StoreService ss;
    @Autowired
    private StoreRepository sr;
    //✔ 테스트에 앞서 의존성 주입을 진행

    @Test
    @DisplayName("회원데이터 생성")
    public void newMembers() {
    }

/*    @Test
    @DisplayName("회원가입테스트")
    public void SaveTest() throws IOException {

        //전부 쓰지 않으면 안되는건가
        StoreSaveDTO storeSaveDTO = new StoreSaveDTO();
        storeSaveDTO.setStoreEmail("테스트아이디1");
        storeSaveDTO.setStorePassword("테스트비번1");
        storeSaveDTO.setStoreName("테스트업체명1");
        storeSaveDTO.setStoreFile("file");
        storeSaveDTO.setStoreFilename("테스트업체파일이름");
        storeSaveDTO.setStorePhone("테스트연락처");
        storeSaveDTO.setStoreOpen("테스트오픈");
        storeSaveDTO.setStoreClose("테스트마감");
        storeSaveDTO.setStoreAddress("테스트주소");
        ss.save(storeSaveDTO);
    }*/

/* @Test
    @DisplayName("회원가입테스트")
    public void memberSaveTest()  {
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트회원 이메일1");
        memberSaveDTO.setMemberPassword("테스트회원 비번1");
        memberSaveDTO.setMemberName("테스트회원 이름1");

        ms.save(memberSaveDTO);
*/

/*    @Test
    @DisplayName("대분류")
    public void categoryTest()  {
        BigCategoryDTO bigCategoryDTO = new BigCategoryDTO();
        bigCategoryDTO.setBigCategoryName("한식");
        System.out.println("해치웠나");
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
