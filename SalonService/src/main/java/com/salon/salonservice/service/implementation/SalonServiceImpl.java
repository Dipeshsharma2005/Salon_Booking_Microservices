package com.salon.salonservice.service.implementation;

import com.salon.salonservice.model.Salon;
import com.salon.salonservice.payload.dto.SalonDTO;
import com.salon.salonservice.payload.dto.UserDTO;
import com.salon.salonservice.payload.dto.UserRole;
import com.salon.salonservice.repository.SalonRepository;
import com.salon.salonservice.service.SalonService;
import com.salon.salonservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;
    private final UserFeignClient userFeignClient;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        // Create new salon
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setCity(req.getCity());
        salon.setAddress(req.getAddress());
        salon.setImages(req.getImages());
        salon.setPhoneNumber(req.getPhoneNumber());
        salon.setEmail(req.getEmail());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());

        // Save salon
        Salon savedSalon = salonRepository.save(salon);

        // ✅ Update the user’s role to SALON_OWNER via Feign client
        try {
            userFeignClient.updateUserRole(user.getId(), UserRole.SALON_OWNER.name());
            System.out.println("✅ User role updated to SALON_OWNER");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to update user role: " + e.getMessage());
        }

        return savedSalon;
    }

    @Override
    public Salon updataSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with id " + salonId));

        if (!existingSalon.getOwnerId().equals(user.getId())) {
            throw new Exception("You don't have permission to update this salon");
        }

        existingSalon.setName(salon.getName());
        existingSalon.setCity(salon.getCity());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setImages(salon.getImages());
        existingSalon.setPhoneNumber(salon.getPhoneNumber());
        existingSalon.setEmail(salon.getEmail());
        existingSalon.setOpenTime(salon.getOpenTime());
        existingSalon.setCloseTime(salon.getCloseTime());

        return salonRepository.save(existingSalon);
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        return salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not exist with id " + salonId));
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalon(String keyword) {
        return salonRepository.searchSalons(keyword);
    }
}
