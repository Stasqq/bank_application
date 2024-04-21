package bank.rest.controllers.userregistration.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
public class UserRegisterResponse {

    private boolean success;
    private List<Pair<String, String>> errorMessages;

    public UserRegisterResponse(List<Pair<String, String>> errorMessages) {
        this.errorMessages = errorMessages;
        this.success = errorMessages.isEmpty();
    }

}
