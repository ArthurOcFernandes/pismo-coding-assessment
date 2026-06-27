package br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
