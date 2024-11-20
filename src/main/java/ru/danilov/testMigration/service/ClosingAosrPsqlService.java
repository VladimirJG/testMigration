package ru.danilov.testMigration.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.testMigration.model.ClosingAosrPsql;
import ru.danilov.testMigration.repository.ClosingAosrPsqlRepository;

@Service
@Transactional
public class ClosingAosrPsqlService {

  private final ClosingAosrPsqlRepository closingAosrPsqlRepository;

  @Autowired
  public ClosingAosrPsqlService(ClosingAosrPsqlRepository closingAosrPsqlRepository) {
    this.closingAosrPsqlRepository = closingAosrPsqlRepository;
  }

  public void saveAll(List<ClosingAosrPsql> closingAosrPsqls) {
    closingAosrPsqlRepository.saveAll(closingAosrPsqls);
  }
}
