package bank.common;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.LinkedList;
import java.util.List;

@Data
public class ValidationResult {

    // list in case same field would allow multiple warnings
    private List<Pair<String, String>> fieldErrorPairs = new LinkedList<>();

}
