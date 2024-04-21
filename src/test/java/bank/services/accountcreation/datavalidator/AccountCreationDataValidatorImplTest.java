package bank.services.accountcreation.datavalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import bank.datamodel.entity.Account;
import bank.datamodel.entity.BankUserDetails;
import bank.repository.BankUserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountCreationDataValidatorImplTest {

    @Mock
    private BankUserDetailsRepository bankUserDetailsRepository;

    @InjectMocks
    private AccountCreationDataValidatorImpl validator;

    @Test
    public void testCheckIfUserExistsAndDoesntHaveAccount_UserExistsAndDoesntHaveAccount() {
        String username = "testUser";
        BankUserDetails userDetails = new BankUserDetails();
        userDetails.setUsername(username);
        when(bankUserDetailsRepository.findByUsername(username)).thenReturn(Optional.of(userDetails));

        Optional<String> validationOutput = validator.checkIfUserExistsAndDoesntHaveAccount(username);

        assertEquals(Optional.empty(), validationOutput);
    }

    @Test
    public void testCheckIfUserExistsAndDoesntHaveAccount_UserExistsAndHasAccount() {
        String username = "testUser";
        BankUserDetails userDetails = new BankUserDetails();
        userDetails.setUsername(username);
        userDetails.setAccount(new Account()); // Simulating that user already has an account
        when(bankUserDetailsRepository.findByUsername(username)).thenReturn(Optional.of(userDetails));

        Optional<String> validationOutput = validator.checkIfUserExistsAndDoesntHaveAccount(username);

        assertEquals("Given user already has one account.", validationOutput.get());
    }

    @Test
    public void testCheckIfUserExistsAndDoesntHaveAccount_UserDoesntExist() {
        String username = "nonExistentUser";
        when(bankUserDetailsRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<String> validationOutput = validator.checkIfUserExistsAndDoesntHaveAccount(username);

        assertEquals("User with given username doesn't exist.", validationOutput.get());
    }
}
