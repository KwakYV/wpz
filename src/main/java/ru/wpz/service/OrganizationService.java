package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.entity.Organization;
import ru.wpz.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> get(Long id){
        return organizationRepository.findById(id);
    }

    @Transactional
    public void save(Organization organization) {
        organizationRepository.save(organization);
    }

    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }
}

