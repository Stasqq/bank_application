package bank.rest.controllers.accountcreation.dto;

import bank.datamodel.PromoCode;
import lombok.Data;

@Data
public class AccountCreationRequest {

    private String username;
    private PromoCode promoCode;

}
