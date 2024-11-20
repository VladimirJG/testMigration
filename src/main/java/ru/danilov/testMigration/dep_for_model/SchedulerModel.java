package ru.danilov.testMigration.dep_for_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

public final class SchedulerModel {
  public enum WorkType implements EnumIntRepresentation
  {
    UNDEFINED(0, "Не определен"),
    OTHER(1, "Прочие"),

    CONCRETING(2, "Бетонирование"),

    WELDING(3, "Сварочные"),

    CABLING(4, "Прокладка кабеля"),

    PALIFICATION(5, "Забивка свай"),

    DRILLING_WELLS(6, "Бурение скважин под сваи"),

    INSTALLATION_STRUCTURE(7, "Монтаж строительных конструкций"),

    ANTI_CORROSION_PROTECTION(8, "Антикоррозионная защита сварных соединений"),

    AUTOMATIC_FIRE_EXTINGUISHING_INSTALLATIONS(9, "Автоматические установки пожаротушения"),

    INTERNAL_WATER_SUPPLY_AND_SEWERAGE_NETWORKS(10, "Внутренние сети водопровода и канализации"),

    INTERNAL_POWER_SUPPLY_NETWORKS(11, "Внутренние сети электроснабжения"),

    INSTALLATION_AND_COMMISSIONING_OF_AUTOMATION_SYSTEMS(12, "Монтаж и наладка систем автоматизации"),

    INSTALLATION_OF_ELEVATORS(13, "Монтаж лифтов"),

    INSTALLATION_OF_TECHNOLOGICAL_EQUIPMENT_AND_PIPELINES(14, "Монтаж технологического оборудования и трубопроводов"),

    EXTERNAL_WATER_SUPPLY_AND_SEWERAGE_NETWORKS(15, "Наружные сети водопровода и канализации"),

    EXTERNAL_POWER_SUPPLY_NETWORKS(16, "Наружные сети электроснабжения"),

    CIVIL_WORKS(17, "Общестроительные работы"),

    FIRE_AND_SECURITY_AND_FIRE_ALARMS(18, "Пожарная и охранно-пожарная сигнализация"),

    HEATING_AND_VENTILATION_NETWORKS(19, "Сети отопления и вентиляции"),

    HEATING_NETWORK(20, "Тепловые сети"),

    CONSTRUCTION_OF_FOUNDATIONS(21, "Устройство фундаментов"),

    BEARING_AND_ENCLOSING_STRUCTURES(22, "Несущие и ограждающие конструкции"),

    ROOF(23, "Кровля"),

    EXTERNAL_FINISHING_WORKS(24, "Наружные отделочные работы (фасад)"),

    INTERIOR_FINISHING_WORKS(25, "Внутренние отделочные работы (полы, стены, потолки)"),

    VENTILATION_AND_AIR_CONDITIONING(26, "Вентиляция и кондиционирование"),

    GAS_PIPELINE(27, "Газопровод"),

    COMMUNICATION_SIGNALING_AND_AUTOMATION_SYSTEMS(28, "Системы связи, сигнализации и автоматизации"),

    IMPROVEMENT_OF_THE_TERRITORY(29, "Благоустройство территории"),

    ROAD_CONSTRUCTION(30, "Устройство автомобильных дорог"),

    TECHNOLOGICAL_SOLUTIONS(31, "Технологические решения");

    final private int _workType;
    @Getter
    private final String rusDescription;

    WorkType(int workType,
             String rusDescription)
    {
      _workType = workType;
      this.rusDescription = rusDescription;
    }

    public static WorkType fromInt(int idValue)
    {
      return Optional.ofNullable(EnumIntRepresentation.fromInt(idValue, WorkType.values()))
          .orElse(WorkType.UNDEFINED);
    }

    @Override
    public int toInt()
    {
      return _workType;
    }
  }

  /**
   * Тип записи в таблице Scheduler
   */
  public enum SchedulerType implements EnumIntRepresentation
  {

    /**
     * Раздел рабочей документации - может содержать в себе конструкции и материалы
     */
    SECTION(0),

    /**
     * Конструкция - содержит в себе работы. Не может содержать другие конструкции
     */
    CONSTRUCTION(1),

    /**
     * Работа - может содержать в себе другие работы
     */
    WORK(2);

    private final int _schedulerType;

    SchedulerType(int schedulerType)
    {
      _schedulerType = schedulerType;
    }

    public static SchedulerType fromInt(int idValue)
    {
      return EnumIntRepresentation.fromInt(idValue, SchedulerType.values());
    }

    @Override
    public int toInt()
    {
      return _schedulerType;
    }
  }

  @Data
  @Accessors(chain = true)
  public static class SectionWorkType
  {
    WorkType workType = WorkType.UNDEFINED;
    String workDocSection;
  }

  @Data
  @Accessors(chain = true)
  public static class Scheduler
  {
    private UUID journalId;
    private int stageId;
    private int parentStageId;
    /**
     * workName
     */
    //todo переименовать в базе(workName), исправить все методы
    private String title;
    /**
     * ID наименования работ в КСИ. В КСИ это называется "Типом работ", не стоит путать с нашим {@link WorkType}.
     * Если null, значит для текущего наименования нет маппинга с типом работ из КСИ.
     * <p>
     * Подробнее: http://ksi.faufcc.ru/
     */
    private String ksiTitleId;
    /**
     * Код работы КСИ, будет пустым - если наименование работы введено вручную
     * <p>
     * Подробнее: http://ksi.faufcc.ru/
     */
    private String ksiCode;
    private String workDocSection;
    private String constructionName;
    private WorkType workType;
    private float workAmount;
    private UnitType unit;
    private float completedAmount;
    private float coveredAmount;
    private Float restAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String projectDoc;
    private List<DocumentModel> normativeDocs = new ArrayList<>();
    private EntitySignatureState state = EntitySignatureState.UNDEFINED;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm['Z']")
    private LocalDateTime createDateTime;
    /**
     * Маркер наличия работы.
     * <p>
     * true - если нет работы.
     * false - если есть работа.
     */
    private boolean canDelete;

    private SchedulerType schedulerType;

    /**
     * Нужна для понимания, чья это интеграция
     */
    IntegrationCompany integration;
  }

  @Data
  @Accessors(chain = true)
  public static class SchedulerSignatureAttr
  {
    private EntitySignatureState state;
    private byte[] hash;
    private String signature;
  }
}
