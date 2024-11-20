package ru.danilov.testMigration.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danilov.testMigration.intermediate_classes.MigrationState;
import ru.danilov.testMigration.service.MigrationStateService;

@RestController
@RequestMapping("/api/migration-state")
public class MigrationStateController {

  private final MigrationStateService migrationStateService;

  @Autowired
  public MigrationStateController(MigrationStateService migrationStateService) {
    this.migrationStateService = migrationStateService;
  }

  @GetMapping
  public ResponseEntity<MigrationState> getLastMigrationState() {
    MigrationState migrationState = migrationStateService.getLastMigrationState();
    return ResponseEntity.ok(migrationState);
  }

  @PostMapping
  public ResponseEntity<MigrationState> updateMigrationState(
      @RequestBody MigrationState migrationState) {
    migrationState.setLastMigrationDate(LocalDateTime.now());
    MigrationState savedMigrationState = migrationStateService.save(migrationState);
    return ResponseEntity.ok(savedMigrationState);
  }
}
