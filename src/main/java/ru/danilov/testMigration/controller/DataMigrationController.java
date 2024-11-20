package ru.danilov.testMigration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danilov.testMigration.migration.MongoToPostgresMigration;

@RestController
@RequestMapping("/api/data-migration")
public class DataMigrationController {

  private final MongoToPostgresMigration mongoToPostgresMigration;

  @Autowired
  public DataMigrationController(MongoToPostgresMigration mongoToPostgresMigration) {
    this.mongoToPostgresMigration = mongoToPostgresMigration;
  }

  @PostMapping("/run")
  public ResponseEntity<String> runDataMigration() {
    try {
      mongoToPostgresMigration.run();
      return ResponseEntity.ok("Data migration completed successfully.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Data migration failed: " + e.getMessage());
    }
  }
}
