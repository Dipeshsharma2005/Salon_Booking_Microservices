package com.salon.salonservice.service;

import com.salon.salonservice.model.Salon;
import com.salon.salonservice.payload.dto.SalonDTO;
import com.salon.salonservice.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {
    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updataSalon(SalonDTO salon, UserDTO user,Long salonId) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalon(String city);
}
