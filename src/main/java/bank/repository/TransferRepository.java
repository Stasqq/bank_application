package bank.repository;

import bank.datamodel.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TransferRepository extends JpaRepository<Transfer, String> {

    @Query(value = "SELECT COUNT(*) " +
            "FROM TRANSFER " +
            "WHERE FROM_ACCOUNT_NUMBER = ?1 " +
            "AND trunc(TRANSFER_TIMESTAMP) = trunc(sysdate)", nativeQuery = true)
    int countTransfersMadeToday(String fromAccountNumber);

}
