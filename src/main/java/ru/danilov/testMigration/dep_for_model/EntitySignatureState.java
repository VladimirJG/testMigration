package ru.danilov.testMigration.dep_for_model;

import java.util.Optional;

public enum EntitySignatureState implements EnumIntRepresentation{
  UNDEFINED(0),
  DRAFT(1),       // черновик
  AGREEMENT(2),   // чистовик
  SIGNING(3),     // на согласовании
  SIGNED(4),      // подписан, согласован
  REJECTED(5);    // отклонен = чистовик

  final private int _stateType;

  EntitySignatureState(int stateType)
  {
    _stateType = stateType;
  }

  public static EntitySignatureState fromInt(int idValue)
  {
    return Optional.ofNullable(EnumIntRepresentation.fromInt(idValue, EntitySignatureState.values()))
        .orElse(EntitySignatureState.UNDEFINED);
  }

  @Override
  public int toInt()
  {
    return _stateType;
  }
}
