package bank.services.userregistration.datavalidator;

import bank.common.ValidationResult;
import bank.datamodel.NotificationChannelType;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;

import static bank.common.BankAppConstants.UserRegisterWarnings.*;

@Component
public class UserRegistrationDataValidatorImpl implements UserRegistrationDataValidator {

    private final List<BiConsumer<UserRegisterRequest, ValidationResult>> validationsList = List.of(
            this::usernameIncorrectLength,
            this::usernameIncorrectCharacters,
            this::passwordIncorrectLength,
            this::phoneNumberIncorrectLength,
            this::phoneNumberIncorrectCharacters,
            this::emailIncorrectFormat,
            this::notificationChannelUnrecognized
    );

    @Override
    public ValidationResult validateRequest(UserRegisterRequest registerRequest) {
        ValidationResult validationResult = new ValidationResult();

        boolean missingUsername = isRequestMissingUsername(registerRequest, validationResult);
        boolean missingPassword = isRequestMissingPassword(registerRequest, validationResult);
        boolean missingPhoneNumber = isRequestMissingPhoneNumber(registerRequest, validationResult);
        boolean missingEmail = isRequestMissingEmail(registerRequest, validationResult);
        boolean missingNotificationChannel = isRequestMissingNotificationChannel(registerRequest, validationResult);
        // missing data validations - if failed should return warning (can't operate on missing data)
        if (missingUsername || missingPassword || missingPhoneNumber || missingEmail || missingNotificationChannel) {
            return validationResult;
        }

        validationsList.forEach(validation -> validation.accept(registerRequest, validationResult));

        return validationResult;
    }

    private boolean isRequestMissingUsername(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (Strings.isBlank(registerRequest.getUserName())) {
            validationResult.getFieldErrorPairs().add(MISSING_USERNAME);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingPassword(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (Strings.isBlank(registerRequest.getPassword())) {
            validationResult.getFieldErrorPairs().add(MISSING_PASSWORD);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingPhoneNumber(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (Strings.isBlank(registerRequest.getPhoneNumber())) {
            validationResult.getFieldErrorPairs().add(MISSING_PHONE_NUMBER);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingEmail(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (Strings.isBlank(registerRequest.getEmail())) {
            validationResult.getFieldErrorPairs().add(MISSING_EMAIL);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingNotificationChannel(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (Strings.isBlank(registerRequest.getNotificationChannel())) {
            validationResult.getFieldErrorPairs().add(MISSING_NOTIFICATION_CHANNEL);
            return true;
        }
        return false;
    }

    private void usernameIncorrectLength(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (registerRequest.getUserName().length() > 30 || registerRequest.getUserName().length() < 8) {
            validationResult.getFieldErrorPairs().add(USERNAME_INCORRECT_LENGTH);
        }
    }

    private void usernameIncorrectCharacters(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        String REGEX = "^[a-zA-Z0-9]*$";
        if (!registerRequest.getUserName().matches(REGEX)) {
            validationResult.getFieldErrorPairs().add(USERNAME_INCORRECT_CHARACTERS);
        }
    }

    private void passwordIncorrectLength(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (registerRequest.getPassword().length() > 30 || registerRequest.getPassword().length() < 8) {
            validationResult.getFieldErrorPairs().add(PASSWORD_INCORRECT_LENGTH);
        }
    }

    private void phoneNumberIncorrectLength(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        if (registerRequest.getPhoneNumber().length() != 9) {
            validationResult.getFieldErrorPairs().add(PHONE_NUMBER_INCORRECT_LENGTH);
        }
    }

    private void phoneNumberIncorrectCharacters(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        String REGEX = "^[0-9]*$";
        if (!registerRequest.getPhoneNumber().matches(REGEX)) {
            validationResult.getFieldErrorPairs().add(PHONE_NUMBER_INCORRECT_CHARACTERS);
        }
    }

    private void emailIncorrectFormat(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        String RFC_5322_EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!registerRequest.getEmail().matches(RFC_5322_EMAIL_REGEX)) {
            validationResult.getFieldErrorPairs().add(EMAIL_INCORRECT_FORMAT);
        }
    }

    private void notificationChannelUnrecognized(UserRegisterRequest registerRequest, ValidationResult validationResult) {
        try {
            NotificationChannelType.valueOf(registerRequest.getNotificationChannel());
        } catch (Exception e) {
            validationResult.getFieldErrorPairs().add(NOTIFICATION_CHANNEL_UNRECOGNIZED);
        }
    }

}
