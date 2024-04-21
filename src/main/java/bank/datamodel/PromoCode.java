package bank.datamodel;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum PromoCode {

    KOD_1(BigDecimal.valueOf(100)),
    KOD_2(BigDecimal.valueOf(200)),
    KOD_3(BigDecimal.valueOf(300)),
    KOD_4(BigDecimal.valueOf(400)),
    KOD_5(BigDecimal.valueOf(500));


    private final BigDecimal initialBalanceAmount;

    PromoCode(BigDecimal initialBalanceAmount) {
        this.initialBalanceAmount = initialBalanceAmount;
    }

}
