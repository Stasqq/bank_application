package bank.services.accountcreation.datavalidator;

import bank.datamodel.entity.BankUserDetails;
import bank.repository.BankUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class AccountCreationDataValidatorImpl implements AccountCreationDataValidator {

    @Autowired
    private BankUserDetailsRepository bankUserDetailsRepository;

    @Override
    public Optional<String> checkIfUserExistsAndDoesntHaveAccount(String username) {
        Optional<String> validationOutput;
        Optional<BankUserDetails> optionalBankUserDetails = bankUserDetailsRepository.findByUsername(username);

        if (optionalBankUserDetails.isPresent()) {
            if (Objects.isNull(optionalBankUserDetails.get().getAccount())) {
                validationOutput = Optional.empty();
            } else {
                validationOutput = Optional.of("Given user already has one account.");
            }
        } else {
            validationOutput = Optional.of("User with given username doesn't exist.");
        }

        return validationOutput;
    }
}
