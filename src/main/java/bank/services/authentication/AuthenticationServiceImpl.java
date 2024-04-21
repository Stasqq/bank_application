package bank.services.authentication;

import bank.datamodel.entity.BankUserDetails;
import bank.repository.BankUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private BankUserDetailsRepository bankUserDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean areInitialsCorrect(String username, String password) {
        Optional<BankUserDetails> userDetailsOptional = bankUserDetailsRepository.findByUsername(username);
        return userDetailsOptional.isPresent()
                && passwordEncoder.matches(password, userDetailsOptional.get().getEncodedPassword());
    }

}
