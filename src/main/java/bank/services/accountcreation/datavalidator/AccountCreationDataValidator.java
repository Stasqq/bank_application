package bank.services.accountcreation.datavalidator;

import java.util.Optional;

public interface AccountCreationDataValidator {

    Optional<String> checkIfUserExistsAndDoesntHaveAccount(String username);

}
