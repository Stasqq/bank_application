package bank.services.userregistration;

import bank.datamodel.NotificationChannelType;
import bank.datamodel.entity.BankUserDetails;
import bank.repository.BankUserDetailsRepository;
import bank.rest.controllers.userregistration.dto.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private BankUserDetailsRepository bankUserDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterRequest registerRequest) {
        BankUserDetails bankUserDetails = new BankUserDetails();
        bankUserDetails.setUsername(registerRequest.getUserName());
        bankUserDetails.setEncodedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        bankUserDetails.setEmail(registerRequest.getEmail());
        bankUserDetails.setPhoneNumber(registerRequest.getPhoneNumber());
        bankUserDetails.setPreferredNotificationChannel(NotificationChannelType.valueOf(registerRequest.getNotificationChannel()));
        bankUserDetailsRepository.save(bankUserDetails);
    }

}
