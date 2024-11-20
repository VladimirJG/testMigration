package ru.danilov.testMigration.dep_for_model;

import java.util.Optional;
import lombok.Getter;

public enum UnitType implements EnumIntRepresentation{
  UNDEFINED_UNIT(0, ""),
  PIECES(1, "шт."),
  KG(2, "кг."),
  M(3, "м"),
  M_2(4, "м²"),
  M_3(5, "м³"),
  TON(6, "т"),
  LITER(7, "л"),
  HA(8, "га"),
  /**
   * Бухта (бухта кабеля)
   */
  COIL(9, "бух."),
  /**
   * Километр
   */
  KM(10, "км"),
  /**
   * Пара
   */
  PAIR(11, "пар"),
  /**
   * Лист
   */
  SHEET(12, "лист"),
  /**
   * Миллилитр
   */
  ML(13, "мл"),
  /**
   * 10 шт
   */
  PIECES_10(14, "десяток штук"),
  /**
   * Комплект
   */
  KIT(15, "компл."),
  /**
   * 100 м
   */
  M_100(16, "сотен м"),
  /**
   * 10 м
   */
  M_10(17, "десятков м"),
  /**
   * 100 шт
   */
  PIECES_100(18, "сотен штук"),
  /**
   * Грамм
   */
  GR(19, "гр"),
  /**
   * Тысяч штук
   */
  PIECES_1000(20, "тысяч штук"),
  /**
   * Тысяча условных единиц
   */
  CONDITIONAL_UNIT_1000(21, "тыс. у.е."),
  /**
   * Рулон
   */
  ROLL(22, "рул."),
  /**
   * Упаковка
   */
  PACKAGING(23, "уп."),
  /**
   * Метр погонный
   */
  LM(24, "пог. м."),
  /**
   * Сантиметр
   */
  CM(25, "см");

  final private int _unitType;

  @Getter
  private final String _rusAbbreviation;

  UnitType(int unitType, String rusAbbreviation)
  {
    _unitType = unitType;
    _rusAbbreviation = rusAbbreviation;
  }

  public static UnitType fromInt(int id)
  {
    return Optional.ofNullable(EnumIntRepresentation.fromInt(id, UnitType.values()))
        .orElse(UnitType.UNDEFINED_UNIT);
  }

  @Override
  public int toInt()
  {
    return _unitType;
  }
}
