package bank.services.authentication;

public interface AuthenticationService {

    boolean areInitialsCorrect(String userName, String encodedPassword);

}
