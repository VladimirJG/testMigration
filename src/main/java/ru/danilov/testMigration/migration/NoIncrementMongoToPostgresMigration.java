/*
package ru.danilov.testMigration.migration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.danilov.testMigration.dep_for_model.SchedulerModel.WorkType;
import ru.danilov.testMigration.intermediate_classes.Act;
import ru.danilov.testMigration.intermediate_classes.SecondaryAct;
import ru.danilov.testMigration.intermediate_classes.ActKsInfo;
import ru.danilov.testMigration.model.ClosingAosrPsql;
import ru.danilov.testMigration.model.ClosingNumberTemplate;

public class NoIncrementMongoToPostgresMigration {
  public static void main(String[] args) {
    // Подключение к MongoDB
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("yourDatabaseName");
    MongoCollection<Document> collection = database.getCollection("ClosingAosr");

    // Подключение к PostgreSQL с использованием Hibernate
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      for (Document doc : collection.find()) {
        ClosingAosrPsql closingAosrPsql = mapMongoDocToPostgresEntity(doc);
        session.save(closingAosrPsql);
      }

      transaction.commit();
    } finally {
      sessionFactory.close();
    }

    mongoClient.close();
  }

  private static ClosingAosrPsql mapMongoDocToPostgresEntity(Document doc) {
    ClosingAosrPsql closingAosrPsql = new ClosingAosrPsql();

    // Преобразование ObjectId в UUID
    closingAosrPsql.setMongoEntityId(UUID.fromString(doc.getObjectId("_id").toString()));

    // Преобразование journalId
    String journalIdBase64 = doc.get("journalId", Document.class).getString("base64");
    closingAosrPsql.setJournalId(UUID.nameUUIDFromBytes(journalIdBase64.getBytes()).getMostSignificantBits());

    // Преобразование других полей
    closingAosrPsql.setWorkDocSection(doc.getString("workDocSection"));
    closingAosrPsql.setStartDate(LocalDate.parse(doc.getDate("startDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()));
    closingAosrPsql.setEndDate(LocalDate.parse(doc.getDate("endDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()));
    closingAosrPsql.setCreationDate(LocalDateTime.ofInstant(doc.getDate("creationDate").toInstant(), ZoneId.systemDefault()));

    // Преобразование creatorId
    String creatorIdBase64 = doc.get("creatorId", Document.class).getString("base64");
    closingAosrPsql.setCreatorId(UUID.nameUUIDFromBytes(creatorIdBase64.getBytes()).getMostSignificantBits());

    // Обработка acts
    Document actsDoc = doc.get("acts", Document.class);
    if (actsDoc != null) {
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
      List<Long> journalIds = journals.stream().map(ObjectId::getTimestamp).map(timestamp -> (long) timestamp).collect(Collectors.toList());
      closingAosrPsql.setJournals(journalIds);
    }

    // Обработка registries
    Document registriesDoc = doc.get("registries", Document.class);
    if (registriesDoc != null) {
      Map<WorkType, Long> registries = new EnumMap<>(WorkType.class);
      for (Map.Entry<String, Object> entry : registriesDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        ObjectId objectId = (ObjectId) entry.getValue();
        registries.put(workType, (long) objectId.getTimestamp());
      }
      closingAosrPsql.setRegistries(registries);
    }

    // Обработка attachedKsActs
    List<Document> attachedKsActs = doc.getList("attachedKsActs", Document.class);
    if (attachedKsActs != null) {
      List<ActKsInfo> actKsInfos = new ArrayList<>();
      for (Document actDoc : attachedKsActs) {
        ActKsInfo actKsInfo = new ActKsInfo();
        // Преобразование полей ActKsInfo
        actKsInfos.add(actKsInfo);
      }
      closingAosrPsql.setAttachedKsActs(actKsInfos);
    }

    // Обработка closingNumberTemplates
    Document closingNumberTemplatesDoc = doc.get("closingNumberTemplates", Document.class);
    if (closingNumberTemplatesDoc != null) {
      Map<WorkType, Long> closingNumberTemplates = new EnumMap<>(WorkType.class);
      for (Map.Entry<String, Object> entry : closingNumberTemplatesDoc.entrySet()) {
        WorkType workType = WorkType.valueOf(entry.getKey());
        Document templateDoc = (Document) entry.getValue();
        ClosingNumberTemplate template = new ClosingNumberTemplate();
        // Преобразование полей ClosingNumberTemplate
        closingNumberTemplates.put(workType, template.getId());
      }
      closingAosrPsql.setClosingNumberTemplates(closingNumberTemplates);
    }

    return closingAosrPsql;
  }
}
*/
