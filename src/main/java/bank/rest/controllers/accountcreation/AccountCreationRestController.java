package bank.rest.controllers.accountcreation;

import bank.datamodel.entity.Account;
import bank.rest.controllers.accountcreation.dto.AccountCreationRequest;
import bank.rest.controllers.accountcreation.dto.AccountCreationResponse;
import bank.services.accountcreation.AccountCreationService;
import bank.services.accountcreation.datavalidator.AccountCreationDataValidator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@JsonSerialize
public class AccountCreationRestController {

    @Autowired
    private AccountCreationDataValidator accountCreationDataValidator;

    @Autowired
    private AccountCreationService accountCreationService;

    /**
     * Review comment:<br>
     * I'm not really sure about who/how should create accounts?<br>
     * Should that be:<br>
     * - some kind of ADMIN user - let's say bank assistant?<br>
     * - each user should be allowed to create account for themselves?<br>
     * As I was not sure about it, I've just stick with the easiest solution that everyone can create account anyone.<br>
     * Either way later on in transfer endpoint there will be mechanism allowing users doing transfers only from
     * their own accounts.
     */
    @PostMapping("/create")
    public ResponseEntity<AccountCreationResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest) {
        Optional<String> validationError = accountCreationDataValidator.checkIfUserExistsAndDoesntHaveAccount(accountCreationRequest.getUsername());
        if (validationError.isPresent()) {
            return new ResponseEntity<>(new AccountCreationResponse(validationError.get(), null), HttpStatus.FORBIDDEN);
        } else {
            Account account = accountCreationService.createAccountForUser(accountCreationRequest.getUsername(), accountCreationRequest.getPromoCode());
            return ResponseEntity.ok(new AccountCreationResponse(null, account.getAccountNumber()));
        }
    }

}
