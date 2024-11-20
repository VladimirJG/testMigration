package ru.danilov.testMigration.intermediate_classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.danilov.testMigration.dep_for_model.SchedulerModel.WorkType;
import ru.danilov.testMigration.model.ClosingAosrPsql;

@Entity
@Table(name = "closing_aosr_registries")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Registry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "closing_aosr_id")
  private ClosingAosrPsql closingAosr;

  @Enumerated(EnumType.STRING)
  @Column(name = "work_type")
  private WorkType workType;

  @Column(name = "registry_id")
  private Long registryId;
}
