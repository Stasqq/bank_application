package bank.services.transfer.mapper;

import bank.datamodel.dto.TransferDto;
import bank.rest.controllers.transfers.dto.TransferRequest;

public interface TransferRequestToDtoMapper {

    TransferDto map(TransferRequest transferRequest);

}
