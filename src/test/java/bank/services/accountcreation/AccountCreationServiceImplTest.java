package bank.services.accountcreation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bank.datamodel.PromoCode;
import bank.datamodel.entity.Account;
import bank.datamodel.entity.BankUserDetails;
import bank.repository.AccountRepository;
import bank.repository.BankUserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountCreationServiceImplTest {

    @Mock
    private BankUserDetailsRepository bankUserDetailsRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountCreationServiceImpl accountCreationService;

    @Test
    public void testCreateAccountForUser() {
        String username = "testUser";
        PromoCode promoCode = PromoCode.KOD_1;

        BankUserDetails userDetails = new BankUserDetails();
        userDetails.setUsername(username);
        when(bankUserDetailsRepository.getByUsername(username)).thenReturn(userDetails);

        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setBankUserDetails(userDetails);
        createdAccount.setBalance(promoCode.getInitialBalanceAmount());
        when(accountRepository.save(any())).thenReturn(createdAccount);

        Account resultAccount = accountCreationService.createAccountForUser(username, promoCode);

        assertEquals(userDetails, resultAccount.getBankUserDetails());
        assertEquals(promoCode.getInitialBalanceAmount(), resultAccount.getBalance());

        verify(bankUserDetailsRepository).getByUsername(username);
        verify(accountRepository).save(any());
    }
}
