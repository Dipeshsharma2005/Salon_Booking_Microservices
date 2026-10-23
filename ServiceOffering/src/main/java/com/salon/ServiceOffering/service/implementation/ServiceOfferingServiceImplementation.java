package com.salon.ServiceOffering.service.implementation;

import com.salon.ServiceOffering.dto.CategoryDTO;
import com.salon.ServiceOffering.dto.SalonDTO;
import com.salon.ServiceOffering.dto.ServiceDTO;
import com.salon.ServiceOffering.model.ServiceOffering;
import com.salon.ServiceOffering.repository.ServiceOfferingRepository;
import com.salon.ServiceOffering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImplementation  implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createServiceOffering(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {

        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateServiceOffering(Long serviceId, ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingRepository.findById(serviceId).orElse(null);

        if(serviceOffering1 == null){
            throw new Exception("Service not found with id "+serviceId);
        }
        serviceOffering1.setName(serviceOffering.getName());
        serviceOffering1.setDescription(serviceOffering.getDescription());
        serviceOffering1.setImage(serviceOffering.getImage());
        serviceOffering1.setPrice(serviceOffering.getPrice());
        serviceOffering1.setDuration(serviceOffering.getDuration());
        return serviceOfferingRepository.save(serviceOffering1);
    }

    @Override
    public Set<ServiceOffering> getAllServiceOfferingsBySalonId(Long salonId, Long categoryId) {

        Set<ServiceOffering> serviceOfferings = serviceOfferingRepository.findBySalonId(salonId);
        if (categoryId != null) {
            serviceOfferings = serviceOfferings.stream().filter((service)->service.getCategoryId() !=null && service.getCategoryId()==categoryId).collect(Collectors.toSet());
        }
        return serviceOfferings;
    }

    @Override
    public Set<ServiceOffering> getServicesOfferingsByIds(Set<Long> ids) {

        List<ServiceOffering> serviceOfferings = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(serviceOfferings);
    }

    @Override
    public ServiceOffering getServiceOfferingById(Long id) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);

        if(serviceOffering == null){
            throw new Exception("service not exist with id "+id);
        }
        return serviceOffering;
    }
}
