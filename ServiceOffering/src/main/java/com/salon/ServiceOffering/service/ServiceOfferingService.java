package com.salon.ServiceOffering.service;

import com.salon.ServiceOffering.dto.CategoryDTO;
import com.salon.ServiceOffering.dto.SalonDTO;
import com.salon.ServiceOffering.dto.ServiceDTO;
import com.salon.ServiceOffering.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createServiceOffering(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateServiceOffering(Long serviceId, ServiceOffering serviceOffering) throws Exception;

    Set<ServiceOffering> getAllServiceOfferingsBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServicesOfferingsByIds(Set<Long> ids);

    ServiceOffering getServiceOfferingById(Long id) throws Exception;
}
