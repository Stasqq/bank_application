package bank.services.transfer;

import bank.datamodel.entity.Account;
import bank.datamodel.entity.Transfer;
import bank.repository.TransferRepository;
import bank.datamodel.dto.TransferDto;
import bank.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void processTransfer(TransferDto transferDto) {
        Transfer transfer = createTransferBasedOnDto(transferDto);
        updateAccountsBalances(transfer);
        transferRepository.save(transfer);
        sendNotificationAboutTransfer(transfer);
    }

    private Transfer createTransferBasedOnDto(TransferDto transferDto) {
        Account fromAccount = transferDto.getFromAccount();
        Account toAccount = transferDto.getToAccount();

        Transfer transfer = new Transfer();
        transfer.setTransferTimestamp(LocalDateTime.now());
        transfer.setAmount(transferDto.getAmount());

        transfer.setFromAccount(fromAccount);
        transfer.setFromAccountBalanceBefore(fromAccount.getBalance());
        transfer.setFromAccountBalanceAfter(fromAccount.getBalance().subtract(transferDto.getAmount()));

        transfer.setToAccount(toAccount);
        transfer.setToAccountBalanceBefore(toAccount.getBalance());
        transfer.setToAccountBalanceAfter(toAccount.getBalance().add(transferDto.getAmount()));

        return transfer;
    }

    private void updateAccountsBalances(Transfer transfer) {
        BigDecimal updatedFromAccountBalance = transfer.getFromAccount().getBalance().subtract(transfer.getAmount());
        BigDecimal updatedToAccountBalance = transfer.getToAccount().getBalance().add(transfer.getAmount());
        transfer.getFromAccount().setBalance(updatedFromAccountBalance);
        transfer.getToAccount().setBalance(updatedToAccountBalance);
    }

    private void sendNotificationAboutTransfer(Transfer transfer) {
        notificationService.asyncNotificationAboutTransfer(transfer);
    }

}
