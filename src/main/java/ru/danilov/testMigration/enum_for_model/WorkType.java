/*
package ru.danilov.testMigration.enum_for_model;

import java.util.Optional;
import lombok.Getter;
import ru.danilov.testMigration.dep_for_model.EnumIntRepresentation;
import ru.danilov.testMigration.dep_for_model.SchedulerModel;

public enum WorkType {
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

  public static SchedulerModel.WorkType fromInt(int idValue)
  {
    return Optional.ofNullable(EnumIntRepresentation.fromInt(idValue, SchedulerModel.WorkType.values()))
        .orElse(SchedulerModel.WorkType.UNDEFINED);
  }

  public int toInt()
  {
    return _workType;
  }
}
*/
