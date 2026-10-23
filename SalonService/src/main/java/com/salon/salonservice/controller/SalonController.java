package com.salon.salonservice.controller;

import com.salon.salonservice.mapper.SalonMapper;
import com.salon.salonservice.model.Salon;
import com.salon.salonservice.payload.dto.SalonDTO;
import com.salon.salonservice.payload.dto.UserDTO;
import com.salon.salonservice.service.SalonService;
import com.salon.salonservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {
//    @Autowired
//    private SalonService salonService;

    private final SalonService salonService;
    private final UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO,
                                                @RequestHeader("Authorization") String token) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(token).getBody();
        if(userDTO == null) throw new Exception("Invalid user token");

        Salon salon = salonService.createSalon(salonDTO, userDTO);  // links salon to user
        SalonDTO response = SalonMapper.mapSalonToSalonDTO(salon);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(@PathVariable Long salonId , @RequestBody SalonDTO salonDTO ,@RequestHeader("Authorization")String token) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(token).getBody();

        Salon salon = salonService.updataSalon(salonDTO,userDTO,salonId);
        SalonDTO salonDTO1= SalonMapper.mapSalonToSalonDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() {

        List<Salon> salons =salonService.getAllSalons();

        List<SalonDTO> salonDTOS = salons.stream().map((salon -> {
            SalonDTO salonDTO = SalonMapper.mapSalonToSalonDTO(salon);
            return salonDTO;
        })
        ).toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {

        Salon salon =salonService.getSalonById(salonId);

        SalonDTO salonDTO = SalonMapper.mapSalonToSalonDTO(salon);

        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam String keyword) {

        List<Salon> salons =salonService.searchSalon(keyword);

        List<SalonDTO> salonDTOS = salons.stream().map((salon -> {
                    SalonDTO salonDTO = SalonMapper.mapSalonToSalonDTO(salon);
                    return salonDTO;
                })
        ).toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@RequestHeader("Authorization")String jwt) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        if (userDTO == null) {
            throw new Exception("User not found with JWT");
        }
        Salon salon =salonService.getSalonByOwnerId(userDTO.getId());

        SalonDTO salonDTO = SalonMapper.mapSalonToSalonDTO(salon);

        return ResponseEntity.ok(salonDTO);
    }
}
