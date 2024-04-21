package bank.services.transfer.mapper;

import bank.repository.AccountRepository;
import bank.datamodel.dto.TransferDto;
import bank.rest.controllers.transfers.dto.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestToDtoMapperImpl implements TransferRequestToDtoMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransferDto map(TransferRequest transferRequest) {
        TransferDto transferDto = new TransferDto();
        transferDto.setFromAccount(accountRepository.getAccountByAccountNumber(transferRequest.getFromAccountNumber()));
        transferDto.setToAccount(accountRepository.getAccountByAccountNumber(transferRequest.getToAccountNumber()));
        transferDto.setAmount(transferRequest.getAmount());
        return transferDto;
    }
}
