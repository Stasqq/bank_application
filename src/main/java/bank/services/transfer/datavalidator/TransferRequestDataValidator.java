package bank.services.transfer.datavalidator;

import bank.common.ValidationResult;
import bank.datamodel.dto.TransferDto;

public interface TransferRequestDataValidator {

    ValidationResult validate(TransferDto transferDto);

}
