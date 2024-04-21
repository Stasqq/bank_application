package bank.services.userregistration.datavalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bank.common.ValidationResult;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;
import org.junit.jupiter.api.Test;

import static bank.common.BankAppConstants.UserRegisterWarnings.*;


public class UserRegistrationDataValidatorImplTest {

    private final UserRegistrationDataValidatorImpl validator = new UserRegistrationDataValidatorImpl();

    @Test
    public void testValidateRequest_WhenMissingPhoneNumber() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("testPassword");
        registerRequest.setNotificationChannel("SMS");
        registerRequest.setEmail("test@example.com");
        ValidationResult validationResult = validator.validateRequest(registerRequest);
        assertEquals(1, validationResult.getFieldErrorPairs().size());
        assertEquals(MISSING_PHONE_NUMBER, validationResult.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidateRequest_WhenMissingEmail() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("testPassword");
        registerRequest.setPhoneNumber("123456789");
        registerRequest.setNotificationChannel("SMS");
        ValidationResult validationResult = validator.validateRequest(registerRequest);
        assertEquals(1, validationResult.getFieldErrorPairs().size());
        assertEquals(MISSING_EMAIL, validationResult.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidateRequest_WhenMissingNotificationChannel() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("testPassword");
        registerRequest.setPhoneNumber("123456789");
        registerRequest.setEmail("test@example.com");
        ValidationResult validationResult = validator.validateRequest(registerRequest);
        assertEquals(1, validationResult.getFieldErrorPairs().size());
        assertEquals(MISSING_NOTIFICATION_CHANNEL, validationResult.getFieldErrorPairs().get(0));
    }

    @Test
    public void testValidateRequest_WhenUsernameIncorrectLength() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUserName("user"); // shorter than minimum length
        registerRequest.setPassword("testPassword");
        registerRequest.setPhoneNumber("123456789");
        registerRequest.setEmail("test@example.com");
        registerRequest.setNotificationChannel("SMS");
        ValidationResult validationResult = validator.validateRequest(registerRequest);
        assertTrue(validationResult.getFieldErrorPairs().contains(USERNAME_INCORRECT_LENGTH));

        registerRequest.setUserName("usernamewithexceedingthirtycharacters"); // longer than maximum length
        validationResult = validator.validateRequest(registerRequest);
        assertTrue(validationResult.getFieldErrorPairs().contains(USERNAME_INCORRECT_LENGTH));
    }

}
