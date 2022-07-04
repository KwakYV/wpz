package ru.wpz.lessons.lesson1.service;

import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.repository.OrganizationRepository;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService( OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
}
