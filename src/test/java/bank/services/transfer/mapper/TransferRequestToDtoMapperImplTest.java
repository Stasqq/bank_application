package bank.services.transfer.mapper;

import bank.datamodel.dto.TransferDto;
import bank.datamodel.entity.Account;
import bank.repository.AccountRepository;
import bank.rest.controllers.transfers.dto.TransferRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferRequestToDtoMapperImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferRequestToDtoMapperImpl mapper;

    @Test
    public void testMap() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountNumber("fromAccountNumber");
        transferRequest.setToAccountNumber("toAccountNumber");
        transferRequest.setAmount(BigDecimal.TEN);

        Account fromAccount = new Account();
        fromAccount.setAccountNumber("fromAccountNumber");

        Account toAccount = new Account();
        toAccount.setAccountNumber("toAccountNumber");

        when(accountRepository.getAccountByAccountNumber("fromAccountNumber")).thenReturn(fromAccount);
        when(accountRepository.getAccountByAccountNumber("toAccountNumber")).thenReturn(toAccount);

        TransferDto transferDto = mapper.map(transferRequest);

        Assertions.assertNotNull(transferDto);
        Assertions.assertEquals(fromAccount, transferDto.getFromAccount());
        Assertions.assertEquals(toAccount, transferDto.getToAccount());
        Assertions.assertEquals(BigDecimal.TEN, transferDto.getAmount());
    }

    @Test
    public void testMap_WithNullFromAccount() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountNumber("fromAccountNumber");
        transferRequest.setToAccountNumber("toAccountNumber");
        transferRequest.setAmount(BigDecimal.TEN);

        Account toAccount = new Account();
        toAccount.setAccountNumber("toAccountNumber");

        when(accountRepository.getAccountByAccountNumber("fromAccountNumber")).thenReturn(null);
        when(accountRepository.getAccountByAccountNumber("toAccountNumber")).thenReturn(toAccount);

        TransferDto transferDto = mapper.map(transferRequest);

        Assertions.assertNotNull(transferDto);
        Assertions.assertNull(transferDto.getFromAccount());
        Assertions.assertEquals(toAccount, transferDto.getToAccount());
        Assertions.assertEquals(BigDecimal.TEN, transferDto.getAmount());
    }

    @Test
    public void testMap_WithNullToAccount() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountNumber("fromAccountNumber");
        transferRequest.setAmount(BigDecimal.TEN);

        Account fromAccount = new Account();
        fromAccount.setAccountNumber("fromAccountNumber");

        when(accountRepository.getAccountByAccountNumber("fromAccountNumber")).thenReturn(fromAccount);

        TransferDto transferDto = mapper.map(transferRequest);

        Assertions.assertNotNull(transferDto);
        Assertions.assertEquals(fromAccount, transferDto.getFromAccount());
        Assertions.assertNull(transferDto.getToAccount());
        Assertions.assertEquals(BigDecimal.TEN, transferDto.getAmount());
    }

    @Test
    public void testMap_WithNullAccounts() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount(BigDecimal.TEN);

        TransferDto transferDto = mapper.map(transferRequest);

        Assertions.assertNotNull(transferDto);
        Assertions.assertNull(transferDto.getFromAccount());
        Assertions.assertNull(transferDto.getToAccount());
        Assertions.assertEquals(BigDecimal.TEN, transferDto.getAmount());
    }
}
