package ru.danilov.testMigration.migration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.danilov.testMigration.dep_for_model.SchedulerModel.WorkType;
import ru.danilov.testMigration.intermediate_classes.Act;
import ru.danilov.testMigration.intermediate_classes.ActKsInfo;
import ru.danilov.testMigration.intermediate_classes.SecondaryAct;
import ru.danilov.testMigration.model.ClosingAosrPsql;
import ru.danilov.testMigration.model.ClosingNumberTemplate;
import ru.danilov.testMigration.intermediate_classes.Journal;
import ru.danilov.testMigration.intermediate_classes.MigrationState;
import ru.danilov.testMigration.intermediate_classes.Registry;
import ru.danilov.testMigration.service.ClosingAosrPsqlService;
import ru.danilov.testMigration.service.MigrationStateService;

@Component
@Slf4j
public class MongoToPostgresMigration {
  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Value("${spring.data.mongodb.database}")
  private String mongoDatabaseName;

  private final ClosingAosrPsqlService closingAosrPsqlService;

  private final MigrationStateService migrationStateService;

  @Autowired
  public MongoToPostgresMigration(ClosingAosrPsqlService closingAosrPsqlService,
                                  MigrationStateService migrationStateService) {
    this.closingAosrPsqlService = closingAosrPsqlService;
    this.migrationStateService = migrationStateService;
  }

  public void run() throws Exception {
    log.info("Starting data migration from MongoDB to PostgreSQL");

    // Подключение к MongoDB
    log.info("Connecting to MongoDB at {}", mongoUri);
    MongoClient mongoClient = MongoClients.create(mongoUri);
    MongoDatabase database = mongoClient.getDatabase(mongoDatabaseName);
    String mongoCollectionName = "ClosingAosr";
    MongoCollection<Document> collection = database.getCollection(mongoCollectionName);

    // Условие переноса: endDate должно быть равно ISODate("2024-11-29T21:00:00.000Z")
    Document filter = new Document("endDate", new Document("$eq", new Date(1732885200000L)));

    // Перенос данных, удовлетворяющих условию
    log.info("Migrating data with endDate equal to 2024-11-29T21:00:00.000Z");
    List<ClosingAosrPsql> closingAosrPsqls = new ArrayList<>();
    for (Document doc : collection.find(filter)) {
      ClosingAosrPsql closingAosrPsql = mapMongoDocToPostgresEntity(doc);
      closingAosrPsqls.add(closingAosrPsql);
    }

    // Сохранение данных в PostgreSQL
    log.info("Saving {} documents to PostgreSQL", closingAosrPsqls.size());
    closingAosrPsqlService.saveAll(closingAosrPsqls);

    // Обновление состояния переноса
    log.info("Updating migration state");
    MigrationState newMigrationState = new MigrationState();
    newMigrationState.setLastMigrationDate(LocalDateTime.now());
    migrationStateService.save(newMigrationState);

    log.info("Data migration completed successfully");
    mongoClient.close();
  }

  private ClosingAosrPsql mapMongoDocToPostgresEntity(Document doc) {
    log.debug("Mapping MongoDB document to PostgreSQL entity: {}", doc);

    ClosingAosrPsql closingAosrPsql = new ClosingAosrPsql();

    // Преобразование ObjectId в UUID
    closingAosrPsql.setMongoEntityId(UUID.fromString(doc.getObjectId("_id").toString()));

    // Преобразование journalId
    String journalIdBase64 = doc.get("journalId", Document.class).getString("base64");
    closingAosrPsql.setJournalId(
        UUID.nameUUIDFromBytes(journalIdBase64.getBytes()).getMostSignificantBits());

    // Преобразование других полей
    closingAosrPsql.setWorkDocSection(doc.getString("workDocSection"));
    closingAosrPsql.setStartDate(
        LocalDate.parse(
            doc.getDate("startDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .toString()));
    closingAosrPsql.setEndDate(LocalDate.parse(
        doc.getDate("endDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .toString()));
    closingAosrPsql.setCreationDate(
        LocalDateTime.ofInstant(doc.getDate("creationDate").toInstant(), ZoneId.systemDefault()));

    // Преобразование creatorId
    String creatorIdBase64 = doc.get("creatorId", Document.class).getString("base64");
    closingAosrPsql.setCreatorId(
        UUID.nameUUIDFromBytes(creatorIdBase64.getBytes()).getMostSignificantBits());

    // Обработка acts
    Document actsDoc = doc.get("acts", Document.class);
    if (actsDoc != null) {
      log.debug("Processing acts: {}", actsDoc);
      for (Map.Entry<String, Object> entry : actsDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        List<ObjectId> objectIds = (List<ObjectId>) entry.getValue();
        for (ObjectId objectId : objectIds) {
          Act act = new Act();
          act.setClosingAosr(closingAosrPsql);
          act.setWorkType(workType);
          act.setActId((long) objectId.getTimestamp());
          closingAosrPsql.getActs().add(act);
        }
      }
    }

    // Обработка secondaryActs
    Document secondaryActsDoc = doc.get("secondaryActs", Document.class);
    if (secondaryActsDoc != null) {
      log.debug("Processing secondaryActs: {}", secondaryActsDoc);
      for (Map.Entry<String, Object> entry : secondaryActsDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        List<ObjectId> objectIds = (List<ObjectId>) entry.getValue();
        for (ObjectId objectId : objectIds) {
          SecondaryAct secondaryAct = new SecondaryAct();
          secondaryAct.setClosingAosr(closingAosrPsql);
          secondaryAct.setWorkType(workType);
          secondaryAct.setSecondaryActId((long) objectId.getTimestamp());
          closingAosrPsql.getSecondaryActs().add(secondaryAct);
        }
      }
    }

    // Обработка journals
    List<ObjectId> journals = doc.getList("journals", ObjectId.class);
    if (journals != null) {
      log.debug("Processing journals: {}", journals);
      for (ObjectId journalId : journals) {
        Journal journal = new Journal();
        journal.setClosingAosr(closingAosrPsql);
        journal.setJournalId((long) journalId.getTimestamp());
        closingAosrPsql.getJournals().add(journal);
      }
    }

    // Обработка registries
    Document registriesDoc = doc.get("registries", Document.class);
    if (registriesDoc != null) {
      log.debug("Processing registries: {}", registriesDoc);
      for (Map.Entry<String, Object> entry : registriesDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        ObjectId objectId = (ObjectId) entry.getValue();
        Registry registry = new Registry();
        registry.setClosingAosr(closingAosrPsql);
        registry.setWorkType(workType);
        registry.setRegistryId((long) objectId.getTimestamp());
        closingAosrPsql.getRegistries().add(registry);
      }
    }

    // Обработка attachedKsActs
    List<Document> attachedKsActs = doc.getList("attachedKsActs", Document.class);
    if (attachedKsActs != null) {
      log.debug("Processing attachedKsActs: {}", attachedKsActs);
      for (Document actDoc : attachedKsActs) {
        ActKsInfo actKsInfo = new ActKsInfo();
        // Преобразование полей ActKsInfo
        actKsInfo.setClosingAosr(closingAosrPsql);
        closingAosrPsql.getAttachedKsActs().add(actKsInfo);
      }
    }

    // Обработка closingNumberTemplates
    Document closingNumberTemplatesDoc = doc.get("closingNumberTemplates", Document.class);
    if (closingNumberTemplatesDoc != null) {
      log.debug("Processing closingNumberTemplates: {}", closingNumberTemplatesDoc);
      for (Map.Entry<String, Object> entry : closingNumberTemplatesDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        Document templateDoc = (Document) entry.getValue();
        ClosingNumberTemplate template = new ClosingNumberTemplate();
        // Преобразование полей ClosingNumberTemplate
        template.setClosingAosr(closingAosrPsql);
        template.setWorkType(workType);
        template.setTemplateId((long) templateDoc.getObjectId("_id").getTimestamp());
        closingAosrPsql.getClosingNumberTemplates().add(template);
      }
    }

    return closingAosrPsql;
  }
}
