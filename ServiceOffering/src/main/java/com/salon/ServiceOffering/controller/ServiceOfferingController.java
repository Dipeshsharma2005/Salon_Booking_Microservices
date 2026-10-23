package com.salon.ServiceOffering.controller;

import com.salon.ServiceOffering.model.ServiceOffering;
import com.salon.ServiceOffering.service.ServiceOfferingService;
import com.salon.ServiceOffering.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServiceOfferingsBySalonId(@PathVariable Long salonId, @RequestParam(required = false) Long categoryId){

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceOfferingsBySalonId(salonId, categoryId);

        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceOfferingById(@PathVariable Long id) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingService.getServiceOfferingById(id);

        return ResponseEntity.ok(serviceOffering);
     }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceOfferingsByIds(@PathVariable Set<Long> ids){

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesOfferingsByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }
}
