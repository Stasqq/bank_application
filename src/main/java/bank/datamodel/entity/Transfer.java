package bank.datamodel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="TRANSFER")
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @Column(name = "TRANSFER_TIMESTAMP", nullable = false)
    private LocalDateTime transferTimestamp;

    @Column(name = "AMOUNT", precision = 14, scale = 2, nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "FROM_ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER",  nullable = false)
    private Account fromAccount;

    @Column(name = "FROM_ACCOUNT_BALANCE_BEFORE", precision = 14, scale = 2, nullable = false)
    private BigDecimal fromAccountBalanceBefore;

    @Column(name = "FROM_ACCOUNT_BALANCE_AFTER", precision = 14, scale = 2, nullable = false)
    private BigDecimal fromAccountBalanceAfter;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "TO_ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER", nullable = false)
    private Account toAccount;

    @Column(name = "TO_ACCOUNT_BALANCE_BEFORE", precision = 14, scale = 2, nullable = false)
    private BigDecimal toAccountBalanceBefore;

    @Column(name = "TO_ACCOUNT_BALANCE_AFTER", precision = 14, scale = 2, nullable = false)
    private BigDecimal toAccountBalanceAfter;
}
