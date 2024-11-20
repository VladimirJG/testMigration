package ru.danilov.testMigration.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.testMigration.intermediate_classes.MigrationState;

@Repository
public interface MigrationStateRepository extends JpaRepository<MigrationState, Long> {
  Optional<MigrationState> findFirstByOrderByLastMigrationDateDesc();
}
