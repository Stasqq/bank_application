package bank.repository;

import bank.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
}
