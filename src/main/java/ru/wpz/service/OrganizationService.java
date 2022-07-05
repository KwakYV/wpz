package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.entity.Organization;
import ru.wpz.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<Organization> getAllOrganization() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganization(Long id){
        return organizationRepository.findById(id);
    }

    public void saveOrganization(Organization organization) {
        organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}

