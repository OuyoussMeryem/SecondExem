package ma.ensa.commandservice.repo;

import ma.ensa.commandservice.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepo extends JpaRepository<Command,Long> {
}
