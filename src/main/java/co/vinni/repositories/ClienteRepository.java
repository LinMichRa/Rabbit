package co.vinni.repositories;

import co.vinni.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmailAndPassword(String email, String password);
    Cliente findByUsernameAndPassword(String username, String password);
}


