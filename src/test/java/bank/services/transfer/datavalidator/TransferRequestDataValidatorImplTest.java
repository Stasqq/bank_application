package bank.services.transfer.datavalidator;

import bank.common.BankAppConstants;
import bank.common.ValidationResult;
import bank.datamodel.dto.TransferDto;
import bank.datamodel.entity.Account;
import bank.datamodel.entity.BankUserDetails;
import bank.repository.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferRequestDataValidatorImplTest {

    @InjectMocks
    private TransferRequestDataValidatorImpl validator;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private Authentication authentication;

    @Test
    public void testValidate_WithMissingFromAccount() {
        TransferDto transferDto = new TransferDto();
        transferDto.setToAccount(new Account());
        transferDto.setAmount(BigDecimal.TEN);

        ValidationResult result = validator.validate(transferDto);

        Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
        Assertions.assertEquals(1, result.getFieldErrorPairs().size());
        Assertions.assertEquals(BankAppConstants.TransferWarnings.MISSING_INCORRECT_FROM_ACCOUNT, result.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidate_WithMissingToAccount() {
        TransferDto transferDto = new TransferDto();
        transferDto.setFromAccount(new Account());
        transferDto.setAmount(BigDecimal.TEN);

        ValidationResult result = validator.validate(transferDto);

        Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
        Assertions.assertEquals(1, result.getFieldErrorPairs().size());
        Assertions.assertEquals(BankAppConstants.TransferWarnings.MISSING_INCORRECT_TO_ACCOUNT, result.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidate_WithMissingAmount() {
        TransferDto transferDto = new TransferDto();
        transferDto.setFromAccount(new Account());
        transferDto.setToAccount(new Account());

        ValidationResult result = validator.validate(transferDto);

        Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
        Assertions.assertEquals(1, result.getFieldErrorPairs().size());
        Assertions.assertEquals(BankAppConstants.TransferWarnings.MISSING_AMOUNT, result.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidate_WithNotOwnedAccount() {
        Account fromAccount = new Account();
        BankUserDetails userDetails = new BankUserDetails();
        userDetails.setUsername("user1");
        fromAccount.setBankUserDetails(userDetails);
        fromAccount.setBalance(BigDecimal.TEN);
        TransferDto transferDto = new TransferDto();
        transferDto.setFromAccount(fromAccount);
        transferDto.setToAccount(new Account());
        transferDto.setAmount(BigDecimal.ONE);

        when(authentication.getName()).thenReturn("user2");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ValidationResult result = validator.validate(transferDto);

        Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
        Assertions.assertEquals(1, result.getFieldErrorPairs().size());
        Assertions.assertEquals(BankAppConstants.TransferWarnings.TRANSFER_FROM_NOT_OWNED_ACCOUNT, result.getFieldErrorPairs().get(0));
    }

    @Nested
    class SpringSecurityMockedTests {

        @BeforeEach
        public void setUp() {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            when(securityContext.getAuthentication())
                    .thenReturn(new UsernamePasswordAuthenticationToken(new User("TEST_USER", "TEST_PASSWORD", Collections.emptyList()), Collections.emptyList()));
            SecurityContextHolder.setContext(securityContext);
        }

        @Test
        public void testValidate_WithAmountHigherThanBalance() {
            BankUserDetails fromBankUserDetails = new BankUserDetails();
            fromBankUserDetails.setUsername("TEST_USER");
            Account fromAccount = new Account();
            fromAccount.setBalance(BigDecimal.TEN);
            fromAccount.setBankUserDetails(fromBankUserDetails);
            TransferDto transferDto = new TransferDto();
            transferDto.setFromAccount(fromAccount);
            transferDto.setToAccount(new Account());
            transferDto.setAmount(BigDecimal.valueOf(20));

            ValidationResult result = validator.validate(transferDto);

            Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
            Assertions.assertEquals(1, result.getFieldErrorPairs().size());
            Assertions.assertEquals(BankAppConstants.TransferWarnings.AMOUNT_HIGHER_THAN_BALANCE, result.getFieldErrorPairs().get(0));
        }

        @Test
        public void testValidate_WithNegativeAmount() {
            BankUserDetails fromBankUserDetails = new BankUserDetails();
            fromBankUserDetails.setUsername("TEST_USER");
            Account fromAccount = new Account();
            fromAccount.setBankUserDetails(fromBankUserDetails);
            fromAccount.setBalance(BigDecimal.ZERO);
            TransferDto transferDto = new TransferDto();
            transferDto.setFromAccount(fromAccount);
            transferDto.setToAccount(new Account());
            transferDto.setAmount(BigDecimal.valueOf(-10));

            ValidationResult result = validator.validate(transferDto);

            Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
            Assertions.assertEquals(1, result.getFieldErrorPairs().size());
            Assertions.assertEquals(BankAppConstants.TransferWarnings.AMOUNT_LESS_EQUAL_ZERO, result.getFieldErrorPairs().get(0));
        }

        @Test
        public void testValidate_WithDailyTransferLimitReached() {
            BankUserDetails fromBankUserDetails = new BankUserDetails();
            fromBankUserDetails.setUsername("TEST_USER");
            Account fromAccount = new Account();
            fromAccount.setAccountNumber("12345");
            fromAccount.setBankUserDetails(fromBankUserDetails);
            fromAccount.setBalance(BigDecimal.TEN);
            TransferDto transferDto = new TransferDto();
            transferDto.setFromAccount(fromAccount);
            transferDto.setToAccount(new Account());
            transferDto.setAmount(BigDecimal.ONE);

            when(transferRepository.countTransfersMadeToday("12345")).thenReturn(3);

            ValidationResult result = validator.validate(transferDto);

            Assertions.assertFalse(result.getFieldErrorPairs().isEmpty());
            Assertions.assertEquals(1, result.getFieldErrorPairs().size());
            Assertions.assertEquals(BankAppConstants.TransferWarnings.DAILY_LIMIT_OF_TRANSFER_NUMBER_REACHED, result.getFieldErrorPairs().get(0));
        }
    }

}
