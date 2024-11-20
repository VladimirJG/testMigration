package ru.danilov.testMigration.dep_for_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class DocumentModel {
  private String documentId;
  private String documentName;
  private String documentNumber;
  private DocumentType documentType;
  private List<DocumentModel> subDocuments;
  /**
   * Дата начала действия документа
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate issueDate;
  /**
   * Дата окночания действия документа
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate expirationDate;
  /**
   * Ссылка на документ в документарии
   */
  private String documentUrl;
  /**
   * Производитель
   */
  private String manufacturer;
  /**
   * Показатель главного материала
   */
  private boolean isMain;
  /**
   * Организация, выдавшая документ
   */
  private String organization;
  /**
   * Описание документа
   */
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm['Z']")
  private LocalDateTime createDateTime;

  public enum DocumentType implements EnumIntRepresentation {
    UNDEFINED_DOC(0),
    ACT(1),
    CERTIFICATE(2),
    PERMIT(3),
    EXECUTIVE_SCHEME(4),
    WORKING_DRAFT(5),
    WORKING_DRAWING(6),
    LAB(7),
    NORMATIVE_DOC(8);

    private final int _documentType;

    DocumentType(int documentType) {
      _documentType = documentType;
    }

    public static DocumentType fromInt(int id) {
      return Optional.ofNullable(EnumIntRepresentation.fromInt(id, DocumentType.values()))
          .orElse(DocumentType.UNDEFINED_DOC);
    }

    @Override
    public int toInt() {
      return _documentType;
    }
  }
}
