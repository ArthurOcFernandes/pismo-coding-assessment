package br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerAccount, Long> {
    java.util.Optional<CustomerAccount> findByDocumentNumber(String documentNumber);
}
