package bank.datamodel.entity;

import bank.datamodel.NotificationChannelType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

@Entity
@Data
@Table(name="BANK_USER_DETAILS")
public class BankUserDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @Column(name = "USER_NAME", nullable = false, length = 30, unique = true)
    private String username;

    @Column(name = "ENCODED_PASSWORD", nullable = false, length = 100)
    private String encodedPassword;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 9)
    private String phoneNumber;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOTIFICATION_CHANNEL", nullable = false, length = 10)
    private NotificationChannelType preferredNotificationChannel;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bankUserDetails", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Account account;

}
