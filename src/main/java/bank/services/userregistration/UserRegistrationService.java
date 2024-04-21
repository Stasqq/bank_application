package bank.services.userregistration;

import bank.rest.controllers.userregistration.dto.UserRegisterRequest;

public interface UserRegistrationService {

    void registerUser(UserRegisterRequest registerRequest);

}
