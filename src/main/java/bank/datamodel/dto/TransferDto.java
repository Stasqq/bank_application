package bank.datamodel.dto;

import bank.datamodel.entity.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;

}
