/*
package ru.danilov.testMigration.model;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.danilov.testMigration.dep_for_model.SchedulerModel.WorkType;

@Entity
@Table(name = "closing_aosr")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClosingAosr implements Cloneable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "journal_id")
  private UUID journalId;

  @Column(name = "work_doc_section")
  private String workDocSection;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @ElementCollection
  @CollectionTable(name = "acts", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "work_type")
  @Column(name = "act_id")
  private Map<WorkType, List<UUID>> acts = new EnumMap<>(WorkType.class);

  @Column(name = "external_id")
  private String externalId;

  @Transient
  private Map<WorkType, List<ActAosr>> actsObjList = new EnumMap<>(WorkType.class);

  @ElementCollection
  @CollectionTable(name = "secondary_acts", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "work_type")
  @Column(name = "secondary_act_id")
  private Map<WorkType, List<UUID>> secondaryActs = new EnumMap<>(WorkType.class);

  @Transient
  private Map<WorkType, List<SecondaryAct>> secondaryActsObjList = new EnumMap<>(WorkType.class);

  @ElementCollection
  @CollectionTable(name = "journals", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @Column(name = "journal_id")
  private List<UUID> journals = new ArrayList<>();

  @Transient
  private List<SecondaryAct> journalsObjList = new ArrayList<>();

  @Column(name = "registry_id")
  private UUID registryId;

  @Transient
  private Registry registryObj;

  @ElementCollection
  @CollectionTable(name = "registries", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "work_type")
  @Column(name = "registry_id")
  private Map<WorkType, UUID> registries = new EnumMap<>(WorkType.class);

  @Transient
  private Map<WorkType, Registry> registriesObjects = new EnumMap<>(WorkType.class);

  @Column(name = "creation_date")
  private LocalDateTime creationDate = LocalDateTime.now();

  @Column(name = "creator_id")
  private UUID creatorId;

  @Transient
  private ApprovalStatus approvalStatus;

  @ElementCollection
  @CollectionTable(name = "attached_ks_acts", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @Column(name = "act_ks_info")
  private List<ActKsInfo> attachedKsActs = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "closing_number_templates", joinColumns = @JoinColumn(name = "closing_aosr_id"))
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "work_type")
  @Column(name = "closing_number_template")
  private Map<WorkType, ClosingNumberTemplate> closingNumberTemplates = new EnumMap<>(WorkType.class);

  public List<UUID> getAosrIds(WorkType workType) {
    return acts.getOrDefault(workType, Collections.emptyList());
  }

  public List<UUID> getSecondaryActIds(WorkType workType) {
    return secondaryActs.getOrDefault(workType, Collections.emptyList());
  }

  public List<UUID> getAllAosrIds() {
    return acts.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  public List<UUID> getAllSecondaryActIds() {
    return secondaryActs.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  @Override
  public ClosingAosr clone() throws CloneNotSupportedException {
    return (ClosingAosr) super.clone();
  }

  public long countAgeInMilliseconds() {
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - this.creationDate.toEpochSecond(ZoneOffset.UTC);
}
*/
