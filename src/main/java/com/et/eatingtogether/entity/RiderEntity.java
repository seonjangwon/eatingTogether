package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.system.RiderDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rider_table")
public class RiderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_number")
    private Long riderNumber;

    private String riderName;
    private String riderState;

    public static RiderEntity toRiderSave(RiderDTO riderDTO){
        RiderEntity riderEntity = new RiderEntity();
        riderEntity.setRiderName(riderDTO.getRiderName());
        riderEntity.setRiderState(riderDTO.getRiderState());
        return riderEntity;
    }
}
