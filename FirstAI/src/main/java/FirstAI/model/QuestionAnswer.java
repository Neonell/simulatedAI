/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package FirstAI.model;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class QuestionAnswer extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"QuestionAnswer\",\"namespace\":\"FirstAI.model\",\"fields\":[{\"name\":\"subject\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"Subject\",\"default\":null},{\"name\":\"question\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"Question\",\"default\":null},{\"name\":\"answer\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"Answer\",\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** Subject */
   private java.lang.String subject;
  /** Question */
   private java.lang.String question;
  /** Answer */
   private java.lang.String answer;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public QuestionAnswer() {}

  /**
   * All-args constructor.
   */
  public QuestionAnswer(java.lang.String subject, java.lang.String question, java.lang.String answer) {
    this.subject = subject;
    this.question = question;
    this.answer = answer;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return subject;
    case 1: return question;
    case 2: return answer;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: subject = (java.lang.String)value$; break;
    case 1: question = (java.lang.String)value$; break;
    case 2: answer = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'subject' field.
   * Subject   */
  public java.lang.String getSubject() {
    return subject;
  }

  /**
   * Sets the value of the 'subject' field.
   * Subject   * @param value the value to set.
   */
  public void setSubject(java.lang.String value) {
    this.subject = value;
  }

  /**
   * Gets the value of the 'question' field.
   * Question   */
  public java.lang.String getQuestion() {
    return question;
  }

  /**
   * Sets the value of the 'question' field.
   * Question   * @param value the value to set.
   */
  public void setQuestion(java.lang.String value) {
    this.question = value;
  }

  /**
   * Gets the value of the 'answer' field.
   * Answer   */
  public java.lang.String getAnswer() {
    return answer;
  }

  /**
   * Sets the value of the 'answer' field.
   * Answer   * @param value the value to set.
   */
  public void setAnswer(java.lang.String value) {
    this.answer = value;
  }

  /** Creates a new QuestionAnswer RecordBuilder */
  public static FirstAI.model.QuestionAnswer.Builder newBuilder() {
    return new FirstAI.model.QuestionAnswer.Builder();
  }
  
  /** Creates a new QuestionAnswer RecordBuilder by copying an existing Builder */
  public static FirstAI.model.QuestionAnswer.Builder newBuilder(FirstAI.model.QuestionAnswer.Builder other) {
    return new FirstAI.model.QuestionAnswer.Builder(other);
  }
  
  /** Creates a new QuestionAnswer RecordBuilder by copying an existing QuestionAnswer instance */
  public static FirstAI.model.QuestionAnswer.Builder newBuilder(FirstAI.model.QuestionAnswer other) {
    return new FirstAI.model.QuestionAnswer.Builder(other);
  }
  
  /**
   * RecordBuilder for QuestionAnswer instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<QuestionAnswer>
    implements org.apache.avro.data.RecordBuilder<QuestionAnswer> {

    private java.lang.String subject;
    private java.lang.String question;
    private java.lang.String answer;

    /** Creates a new Builder */
    private Builder() {
      super(FirstAI.model.QuestionAnswer.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(FirstAI.model.QuestionAnswer.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.subject)) {
        this.subject = data().deepCopy(fields()[0].schema(), other.subject);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.question)) {
        this.question = data().deepCopy(fields()[1].schema(), other.question);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.answer)) {
        this.answer = data().deepCopy(fields()[2].schema(), other.answer);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing QuestionAnswer instance */
    private Builder(FirstAI.model.QuestionAnswer other) {
            super(FirstAI.model.QuestionAnswer.SCHEMA$);
      if (isValidValue(fields()[0], other.subject)) {
        this.subject = data().deepCopy(fields()[0].schema(), other.subject);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.question)) {
        this.question = data().deepCopy(fields()[1].schema(), other.question);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.answer)) {
        this.answer = data().deepCopy(fields()[2].schema(), other.answer);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'subject' field */
    public java.lang.String getSubject() {
      return subject;
    }
    
    /** Sets the value of the 'subject' field */
    public FirstAI.model.QuestionAnswer.Builder setSubject(java.lang.String value) {
      validate(fields()[0], value);
      this.subject = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'subject' field has been set */
    public boolean hasSubject() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'subject' field */
    public FirstAI.model.QuestionAnswer.Builder clearSubject() {
      subject = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'question' field */
    public java.lang.String getQuestion() {
      return question;
    }
    
    /** Sets the value of the 'question' field */
    public FirstAI.model.QuestionAnswer.Builder setQuestion(java.lang.String value) {
      validate(fields()[1], value);
      this.question = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'question' field has been set */
    public boolean hasQuestion() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'question' field */
    public FirstAI.model.QuestionAnswer.Builder clearQuestion() {
      question = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'answer' field */
    public java.lang.String getAnswer() {
      return answer;
    }
    
    /** Sets the value of the 'answer' field */
    public FirstAI.model.QuestionAnswer.Builder setAnswer(java.lang.String value) {
      validate(fields()[2], value);
      this.answer = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'answer' field has been set */
    public boolean hasAnswer() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'answer' field */
    public FirstAI.model.QuestionAnswer.Builder clearAnswer() {
      answer = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public QuestionAnswer build() {
      try {
        QuestionAnswer record = new QuestionAnswer();
        record.subject = fieldSetFlags()[0] ? this.subject : (java.lang.String) defaultValue(fields()[0]);
        record.question = fieldSetFlags()[1] ? this.question : (java.lang.String) defaultValue(fields()[1]);
        record.answer = fieldSetFlags()[2] ? this.answer : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}