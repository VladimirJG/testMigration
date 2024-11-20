package ru.danilov.testMigration.enum_for_model;

import lombok.Getter;

public enum ApprovalStatus {
  /**
   * только что создан
   */
  NEW("Новое"),
  /**
   * только что переформирован
   */
  RECREATED("Обновлено"),
  /**
   * отклонён с замечаниями или без
   */
  REJECTED("Отклонено"),
  /**
   * на согласовании
   */
  APPROVAL("На согласовании"),
  /**
   * согласован всеми согласовантами
   */
  APPROVED("Согласовано"),
  /**
   * на подписании
   */
  SIGNING("На подписании"),
  /**
   * подписан всеми подписантами, конечный статус
   */
  SIGNED("Подписано");

  @Getter
  private final String rusVaLue;

  ApprovalStatus(String rusVaLue)
  {
    this.rusVaLue = rusVaLue;
  }
}
