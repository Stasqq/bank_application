package bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

@Entity
@Table(name="USER_DETAILS")
@Getter
@Setter
public class UserDetails implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;

    @Version
    private int version;

    @Column(name = "NAME", length = 30)
    private String name;

}
