package br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    java.util.Optional<Account> findByDocumentNumber(String documentNumber);
}
