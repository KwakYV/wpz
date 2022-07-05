package ru.wpz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wpz.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
