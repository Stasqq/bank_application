package bank.services.transfer;

import bank.datamodel.dto.TransferDto;

public interface TransferService {

    void processTransfer(TransferDto transferDto);

}
