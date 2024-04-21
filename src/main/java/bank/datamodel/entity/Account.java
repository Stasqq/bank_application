package bank.datamodel.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Data
@Table(name="ACCOUNT")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NaturalId
    @Column(name = "ACCOUNT_NUMBER", length = 20, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "BALANCE", precision = 14, scale = 2, nullable = false)
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "BANK_USER_DETAILS_ID", nullable = false, unique = true)
    private BankUserDetails bankUserDetails;

    public Account() {
        this.accountNumber = String
                .format("%010d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
                .substring(0,20);
    }

}