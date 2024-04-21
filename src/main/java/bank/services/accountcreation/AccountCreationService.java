package bank.services.accountcreation;

import bank.datamodel.entity.Account;
import bank.datamodel.PromoCode;

public interface AccountCreationService {

    Account createAccountForUser(String username, PromoCode promoCode);

}
