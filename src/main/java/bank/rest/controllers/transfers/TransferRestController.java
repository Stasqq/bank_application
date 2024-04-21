package bank.rest.controllers.transfers;

import bank.common.ValidationResult;
import bank.datamodel.dto.TransferDto;
import bank.rest.controllers.transfers.dto.TransferRequest;
import bank.rest.controllers.transfers.dto.TransferResponse;
import bank.services.transfer.TransferService;
import bank.services.transfer.datavalidator.TransferRequestDataValidator;
import bank.services.transfer.mapper.TransferRequestToDtoMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@JsonSerialize
public class TransferRestController {

    @Autowired
    private TransferRequestToDtoMapper transferRequestToDtoMapper;

    @Autowired
    private TransferRequestDataValidator transferRequestDataValidator;

    @Autowired
    private TransferService transferService;

    @PostMapping("/order")
    public ResponseEntity<TransferResponse> orderTransfer(@RequestBody TransferRequest transferRequest) {
        TransferDto transferDto = transferRequestToDtoMapper.map(transferRequest);
        ValidationResult validationResult = transferRequestDataValidator.validate(transferDto);

        if (validationResult.getFieldErrorPairs().isEmpty()) {
            transferService.processTransfer(transferDto);
            return ResponseEntity.ok(new TransferResponse(Collections.emptyList()));
        } else {
            return new ResponseEntity<>(new TransferResponse(validationResult.getFieldErrorPairs()), HttpStatus.FORBIDDEN);
        }
    }

}
