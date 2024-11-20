package ru.danilov.testMigration.dep_for_model;

public interface EnumIntRepresentation {
  int toInt();

  static <T extends Enum<T> & EnumIntRepresentation>T fromInt(Integer id, T[] values)
  {
    if (id != null)
    {
      for (T value : values)
      {
        if (value.toInt() == id)
        {
          return value;
        }
      }
    }
    return null;
  }
}
