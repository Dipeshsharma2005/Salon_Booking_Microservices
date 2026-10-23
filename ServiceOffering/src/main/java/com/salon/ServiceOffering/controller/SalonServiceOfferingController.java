package com.salon.ServiceOffering.controller;

import com.salon.ServiceOffering.dto.CategoryDTO;
import com.salon.ServiceOffering.dto.SalonDTO;
import com.salon.ServiceOffering.dto.ServiceDTO;
import com.salon.ServiceOffering.model.ServiceOffering;
import com.salon.ServiceOffering.service.ServiceOfferingService;
import com.salon.ServiceOffering.service.client.CategoryFeignClient;
import com.salon.ServiceOffering.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    private final SalonFeignClient salonFeignClient;

    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createServiceOffering(@RequestBody ServiceDTO serviceDTO,@RequestHeader("Authorization")String token) throws Exception{

        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(token).getBody();

        CategoryDTO categoryDTO = categoryFeignClient.getCategoryByIdAndSalonId(serviceDTO.getCategoryId(),salonDTO.getId()).getBody();

        ServiceOffering serviceOffering = serviceOfferingService.createServiceOffering(salonDTO,serviceDTO,categoryDTO);

        return new ResponseEntity<>(serviceOffering, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateServiceOffering(@PathVariable Long id, @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingService.updateServiceOffering(id, serviceOffering);

        return new ResponseEntity<>(serviceOffering1, HttpStatus.OK);
    }

}
