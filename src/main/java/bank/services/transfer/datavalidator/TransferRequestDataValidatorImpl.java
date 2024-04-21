package bank.services.transfer.datavalidator;

import bank.common.ValidationResult;
import bank.repository.TransferRepository;
import bank.datamodel.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import static bank.common.BankAppConstants.DAILY_TRANSFER_NUMBER_LIMIT;
import static bank.common.BankAppConstants.TransferWarnings.*;

@Component
public class TransferRequestDataValidatorImpl implements TransferRequestDataValidator {

    @Autowired
    private TransferRepository transferRepository;

    private final List<BiConsumer<TransferDto, ValidationResult>> validationsList = List.of(
            this::requestSenderUsingNotOwnedAccount,
            this::requestAmountHigherThanCurrentBalance,
            this::amountNegative,
            this::dailyTransferLimitAlreadyReached
    );

    @Override
    public ValidationResult validate(TransferDto transferDto) {
        ValidationResult validationResult = new ValidationResult();

        boolean missingOrIncorrectFromAccount = isRequestMissingOrIncorrectFromAccount(transferDto, validationResult);
        boolean missingOrIncorrectToAccount = isRequestMissingOrIncorrectToAccount(transferDto, validationResult);
        boolean missingAmount = isRequestMissingAmount(transferDto, validationResult);
        // missing data validations - if failed should return warning (can't operate on missing data)
        if (missingOrIncorrectFromAccount || missingOrIncorrectToAccount || missingAmount) {
            return validationResult;
        }

        validationsList.forEach(validation -> validation.accept(transferDto, validationResult));

        return validationResult;
    }

    private boolean isRequestMissingOrIncorrectFromAccount(TransferDto transferDto, ValidationResult validationResult) {
        if (Objects.isNull(transferDto.getFromAccount())) {
            validationResult.getFieldErrorPairs().add(MISSING_INCORRECT_FROM_ACCOUNT);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingOrIncorrectToAccount(TransferDto transferDto, ValidationResult validationResult) {
        if (Objects.isNull(transferDto.getToAccount())) {
            validationResult.getFieldErrorPairs().add(MISSING_INCORRECT_TO_ACCOUNT);
            return true;
        }
        return false;
    }

    private boolean isRequestMissingAmount(TransferDto transferDto, ValidationResult validationResult) {
        if (Objects.isNull(transferDto.getAmount())) {
            validationResult.getFieldErrorPairs().add(MISSING_AMOUNT);
            return true;
        }
        return false;
    }

    private void requestSenderUsingNotOwnedAccount(TransferDto transferDto, ValidationResult validationResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentAuthenticateUsername = authentication.getName();
        String transferFromAccountOwnerUsername = transferDto.getFromAccount().getBankUserDetails().getUsername();
        if (!currentAuthenticateUsername.equals(transferFromAccountOwnerUsername)) {
            validationResult.getFieldErrorPairs().add(TRANSFER_FROM_NOT_OWNED_ACCOUNT);
        }
    }

    private void requestAmountHigherThanCurrentBalance(TransferDto transferDto, ValidationResult validationResult) {
        BigDecimal currentBalance = transferDto.getFromAccount().getBalance();
        BigDecimal transferAmount = transferDto.getAmount();
        if (currentBalance.compareTo(transferAmount) < 0) {
            validationResult.getFieldErrorPairs().add(AMOUNT_HIGHER_THAN_BALANCE);
        }
    }

    private void amountNegative(TransferDto transferDto, ValidationResult validationResult) {
        if (transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            validationResult.getFieldErrorPairs().add(AMOUNT_LESS_EQUAL_ZERO);
        }
    }

    private void dailyTransferLimitAlreadyReached(TransferDto transferDto, ValidationResult validationResult) {
        if (transferRepository.countTransfersMadeToday(transferDto.getFromAccount().getAccountNumber()) >= DAILY_TRANSFER_NUMBER_LIMIT) {
            validationResult.getFieldErrorPairs().add(DAILY_LIMIT_OF_TRANSFER_NUMBER_REACHED);
        }
    }

}
