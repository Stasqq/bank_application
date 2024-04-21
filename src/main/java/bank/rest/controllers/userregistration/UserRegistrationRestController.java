package bank.rest.controllers.userregistration;

import bank.common.ValidationResult;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;
import bank.rest.controllers.userregistration.dto.UserRegisterResponse;
import bank.services.userregistration.UserRegistrationService;
import bank.services.userregistration.datavalidator.UserRegistrationDataValidator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@JsonSerialize
public class UserRegistrationRestController {

    @Autowired
    private UserRegistrationDataValidator userRegistrationDataValidator;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest registerRequest) {
        ValidationResult validationResult = userRegistrationDataValidator.validateRequest(registerRequest);

        if (validationResult.getFieldErrorPairs().isEmpty()) {
            userRegistrationService.registerUser(registerRequest);
            return ResponseEntity.ok(new UserRegisterResponse(Collections.emptyList()));
        } else {
            return new ResponseEntity<>(new UserRegisterResponse(validationResult.getFieldErrorPairs()), HttpStatus.FORBIDDEN);
        }
    }

}
