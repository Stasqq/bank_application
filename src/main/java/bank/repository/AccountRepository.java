package bank.repository;

import bank.datamodel.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, String> {

    Account getAccountByAccountNumber(String accountNumber);

}
