package ru.danilov.testMigration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.testMigration.model.ClosingAosrPsql;

@Repository
public interface ClosingAosrPsqlRepository extends JpaRepository<ClosingAosrPsql, Long> {
}
