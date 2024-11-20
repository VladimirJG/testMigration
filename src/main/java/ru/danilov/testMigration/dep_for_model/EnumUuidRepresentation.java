package ru.danilov.testMigration.dep_for_model;

import java.util.UUID;

public interface EnumUuidRepresentation {
  UUID toUuid();

  static <T extends Enum<T> & EnumUuidRepresentation> T fromUuid(UUID uuid, T[] values)
  {
    for (T value : values)
    {
      if (value.toUuid().equals(uuid))
      {
        return value;
      }
    }
    return null;
  }
}
