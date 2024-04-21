package bank.services.userregistration.datavalidator;

import bank.common.ValidationResult;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;

public interface UserRegistrationDataValidator {

    ValidationResult validateRequest(UserRegisterRequest registerRequest);

}
