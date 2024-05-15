package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    void findByUsername(String username);
}
