package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customer_table")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_number")
    private Long customerNumber;

    private String customerEmail;
    private String customerPassword;
    private String customerNickname;
    private String customerAddress;
    private String customerPhone;
    private int customerGrade;
    private int customerPoint;
    private String customerDname;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<BasketEntity> basketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<MyCouponEntity> myCouponEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private  List<PointEntity> pointEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CustomerBlacklistEntity customerBlacklistEntity;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "customerEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<WishlistEntity> wishlistEntity;

    public static CustomerEntity loginTest(CustomerDetailDTO customerDetailDTO){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerEmail(customerDetailDTO.getCustomerEmail());
        customerEntity.setCustomerPassword(customerDetailDTO.getCustomerPassword());
        return customerEntity;
    }
}
