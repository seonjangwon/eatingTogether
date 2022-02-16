package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.system.BigCategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bigCategory_table")
public class BigCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bigCategory_number")
    private Long bigCategoryNumber;
    private String bigCategoryName;

    // 대분류 : 업장 1:n
    @OneToMany(mappedBy = "bigCategoryEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StoreEntity> storeEntities;




}
