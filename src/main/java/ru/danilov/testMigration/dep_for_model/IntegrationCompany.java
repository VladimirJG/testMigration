package ru.danilov.testMigration.dep_for_model;

import java.util.UUID;

public enum IntegrationCompany implements EnumUuidRepresentation {
  EXON("398cbe39-afc5-11ec-8e10-d00d14db6f91"),
  ULAB("158c211e-afc5-11ec-8e10-d00d14db6f91"),
  CYNTEKA("4d573b61-afc5-11ec-8e10-d00d14db6f91"),
  CONSTRUCTION_BUS("b4f638df-0c51-4139-9247-2eaa98e842b3"),
  OSNOVA("1b38df0b-cfce-412d-b7f2-6f2da3ecd902"),
  PILOT("43eaa512-482f-4467-9b67-9f262661cbf6"),
  LEVEL("05f64718-645e-4972-a2bd-9188c3f299c5"),
  A101("4a6c2b99-5870-4a65-8152-ba4ecbb27747"),
  TECHZOR("40174ebf-3a4a-49a3-9190-f24cd9f5adb9");

  private final UUID _integrationId;

  IntegrationCompany(String id) {
    _integrationId = UUID.fromString(id);
  }

  @Override
  public UUID toUuid() {
    return _integrationId;
  }
}
