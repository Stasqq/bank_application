package bank.services.userregistration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bank.datamodel.NotificationChannelType;
import bank.datamodel.entity.BankUserDetails;
import bank.repository.BankUserDetailsRepository;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceImplTest {

    @InjectMocks
    private UserRegistrationServiceImpl userRegistrationService;

    @Mock
    private BankUserDetailsRepository bankUserDetailsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("testPassword");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPhoneNumber("123456789");
        registerRequest.setNotificationChannel("EMAIL");

        BankUserDetails bankUserDetails = new BankUserDetails();
        bankUserDetails.setUsername(registerRequest.getUserName());
        bankUserDetails.setEncodedPassword("encodedPassword");
        bankUserDetails.setEmail(registerRequest.getEmail());
        bankUserDetails.setPhoneNumber(registerRequest.getPhoneNumber());
        bankUserDetails.setPreferredNotificationChannel(NotificationChannelType.EMAIL);

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(bankUserDetailsRepository.save(bankUserDetails)).thenReturn(bankUserDetails);

        userRegistrationService.registerUser(registerRequest);

        verify(bankUserDetailsRepository).save(bankUserDetails);
    }
}
