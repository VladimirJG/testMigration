package ru.danilov.testMigration.intermediate_classes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.danilov.testMigration.model.ClosingAosrPsql;

@Entity
@Table(name = "act_ks_info")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ActKsInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "closing_aosr_id")
  private ClosingAosrPsql closingAosr;
}