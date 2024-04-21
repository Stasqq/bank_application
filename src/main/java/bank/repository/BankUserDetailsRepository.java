package bank.repository;

import bank.datamodel.entity.BankUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface BankUserDetailsRepository extends JpaRepository<BankUserDetails, String> {

    Optional<BankUserDetails> findByUsername(String username);

    BankUserDetails getByUsername(String username);

}
