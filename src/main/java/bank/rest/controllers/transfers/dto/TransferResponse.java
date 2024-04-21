package bank.rest.controllers.transfers.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
public class TransferResponse {

    private boolean success;
    private List<Pair<String, String>> errorMessages;

    public TransferResponse(List<Pair<String, String>> errorMessages) {
        this.errorMessages = errorMessages;
        this.success = errorMessages.isEmpty();
    }

}
