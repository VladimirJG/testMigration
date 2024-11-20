package ru.danilov.testMigration.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.danilov.testMigration.enum_for_model.ApprovalStatus;
import ru.danilov.testMigration.intermediate_classes.Act;
import ru.danilov.testMigration.intermediate_classes.ActKsInfo;
import ru.danilov.testMigration.intermediate_classes.Journal;
import ru.danilov.testMigration.intermediate_classes.Registry;
import ru.danilov.testMigration.intermediate_classes.SecondaryAct;

@Entity
@Table(name = "closing_aosr")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClosingAosrPsql implements Cloneable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "mongo_entity_id")
  private UUID mongoEntityId;

  @Column(name = "journal_id")
  private Long journalId;

  @Column(name = "work_doc_section")
  private String workDocSection;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(name = "creator_id", nullable = false)
  private Long creatorId;

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Act> acts = new ArrayList<>();

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SecondaryAct> secondaryActs = new ArrayList<>();

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Journal> journals = new ArrayList<>();

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Registry> registries = new ArrayList<>();

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ActKsInfo> attachedKsActs = new ArrayList<>();

  @OneToMany(mappedBy = "closingAosr", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ClosingNumberTemplate> closingNumberTemplates = new ArrayList<>();

  @Transient
  private ApprovalStatus approvalStatus;

  public ClosingAosrPsql clone() throws CloneNotSupportedException {
    return (ClosingAosrPsql) super.clone();
  }

  public long countAgeInMilliseconds() {
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - this.creationDate.toEpochSecond(ZoneOffset.UTC);
  }
}