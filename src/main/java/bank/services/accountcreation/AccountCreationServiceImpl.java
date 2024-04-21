package bank.services.accountcreation;

import bank.datamodel.entity.Account;
import bank.datamodel.entity.BankUserDetails;
import bank.datamodel.PromoCode;
import bank.repository.AccountRepository;
import bank.repository.BankUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountCreationServiceImpl implements AccountCreationService {

    @Autowired
    private BankUserDetailsRepository bankUserDetailsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccountForUser(String username, PromoCode promoCode) {
        BankUserDetails userDetails = bankUserDetailsRepository.getByUsername(username);
        Account account = new Account();
        account.setBankUserDetails(userDetails);
        account.setBalance(promoCode.getInitialBalanceAmount());
        return accountRepository.save(account);
    }
}
