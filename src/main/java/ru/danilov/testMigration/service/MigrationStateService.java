package ru.danilov.testMigration.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.testMigration.model.MigrationState;
import ru.danilov.testMigration.repository.MigrationStateRepository;

@Service
@Transactional
public class MigrationStateService {

  private final MigrationStateRepository migrationStateRepository;

  public MigrationStateService(MigrationStateRepository migrationStateRepository) {
    this.migrationStateRepository = migrationStateRepository;
  }

  public MigrationState save(MigrationState migrationState) {
    return migrationStateRepository.save(migrationState);
  }

  public MigrationState getLastMigrationState() {
    Optional<MigrationState> optionalMigrationState =
        migrationStateRepository.findFirstByOrderByLastMigrationDateDesc();
    return optionalMigrationState.orElse(null);
  }
}
