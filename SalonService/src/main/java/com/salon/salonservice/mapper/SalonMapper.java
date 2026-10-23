package com.salon.salonservice.mapper;

import com.salon.salonservice.model.Salon;
import com.salon.salonservice.payload.dto.SalonDTO;

public class SalonMapper {
    public static SalonDTO mapSalonToSalonDTO(Salon salon) {

        SalonDTO salonDTO = new SalonDTO();

        salonDTO.setId(salon.getId());
        salonDTO.setName(salon.getName());
        salonDTO.setCity(salon.getCity());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setImages(salon.getImages());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());

        return salonDTO;
    }
}
