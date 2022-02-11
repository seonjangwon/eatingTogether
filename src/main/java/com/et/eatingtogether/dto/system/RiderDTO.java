package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.RiderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class RiderDTO {
    private Long riderNumber;
    private String riderName;
    private String riderState;

    public static RiderDTO riderDTOList(RiderEntity riderEntity){
        RiderDTO riderDTO = new RiderDTO();
        riderDTO.setRiderNumber(riderEntity.getRiderNumber());
        riderDTO.setRiderName(riderEntity.getRiderName());
        riderDTO.setRiderState(riderEntity.getRiderState());
        return riderDTO;
    }
}
