package bank.services.transfer;

import bank.datamodel.dto.TransferDto;
import bank.datamodel.entity.Account;
import bank.datamodel.entity.Transfer;
import bank.repository.TransferRepository;
import bank.services.notification.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private NotificationService notificationService;

    @Test
    public void testProcessTransfer() {
        TransferDto transferDto = new TransferDto();
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(100));
        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.valueOf(50));
        transferDto.setFromAccount(fromAccount);
        transferDto.setToAccount(toAccount);
        transferDto.setAmount(BigDecimal.valueOf(30));

        Transfer transfer = new Transfer();
        transfer.setTransferTimestamp(LocalDateTime.now());
        transfer.setAmount(BigDecimal.valueOf(30));
        transfer.setFromAccount(fromAccount);
        transfer.setFromAccountBalanceBefore(BigDecimal.valueOf(100));
        transfer.setFromAccountBalanceAfter(BigDecimal.valueOf(70));
        transfer.setToAccount(toAccount);
        transfer.setToAccountBalanceBefore(BigDecimal.valueOf(50));
        transfer.setToAccountBalanceAfter(BigDecimal.valueOf(80));

        when(transferRepository.save(Mockito.any(Transfer.class))).thenReturn(transfer);

        transferService.processTransfer(transferDto);

        verify(transferRepository).save(Mockito.any(Transfer.class));
        verify(notificationService).asyncNotificationAboutTransfer(any());
        Assertions.assertEquals(BigDecimal.valueOf(70), fromAccount.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(80), toAccount.getBalance());
    }
}
