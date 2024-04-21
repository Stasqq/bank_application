package bank.rest.controllers.accountcreation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreationResponse {

    private String errorMessage;
    private String createdAccountNumber;

}
