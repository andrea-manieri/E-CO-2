/*
 * Copyright 2016 Michele Sparamonti & Spiros Koulouzis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.edisonproject.training.tfidf.avro;  
/**
 *
 * @author Michele Sparamonti (michele.sparamonti@eng.it)
 */
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TfidfDocument extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TfidfDocument\",\"namespace\":\"tfidfDocument.avro\",\"fields\":[{\"name\":\"documentId\",\"type\":\"string\"},{\"name\":\"words\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"values\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence documentId;
  @Deprecated public java.util.List<java.lang.CharSequence> words;
  @Deprecated public java.util.List<java.lang.CharSequence> values;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public TfidfDocument() {}

  /**
   * All-args constructor.
   */
  public TfidfDocument(java.lang.CharSequence documentId, java.util.List<java.lang.CharSequence> words, java.util.List<java.lang.CharSequence> values) {
    this.documentId = documentId;
    this.words = words;
    this.values = values;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return documentId;
    case 1: return words;
    case 2: return values;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: documentId = (java.lang.CharSequence)value$; break;
    case 1: words = (java.util.List<java.lang.CharSequence>)value$; break;
    case 2: values = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'documentId' field.
   */
  public java.lang.CharSequence getDocumentId() {
    return documentId;
  }

  /**
   * Sets the value of the 'documentId' field.
   * @param value the value to set.
   */
  public void setDocumentId(java.lang.CharSequence value) {
    this.documentId = value;
  }

  /**
   * Gets the value of the 'words' field.
   */
  public java.util.List<java.lang.CharSequence> getWords() {
    return words;
  }

  /**
   * Sets the value of the 'words' field.
   * @param value the value to set.
   */
  public void setWords(java.util.List<java.lang.CharSequence> value) {
    this.words = value;
  }

  /**
   * Gets the value of the 'values' field.
   */
  public java.util.List<java.lang.CharSequence> getValues() {
    return values;
  }

  /**
   * Sets the value of the 'values' field.
   * @param value the value to set.
   */
  public void setValues(java.util.List<java.lang.CharSequence> value) {
    this.values = value;
  }

  /** Creates a new TfidfDocument RecordBuilder */
  public static TfidfDocument.Builder newBuilder() {
    return new TfidfDocument.Builder();
  }
  
  /** Creates a new TfidfDocument RecordBuilder by copying an existing Builder */
  public static TfidfDocument.Builder newBuilder(TfidfDocument.Builder other) {
    return new TfidfDocument.Builder(other);
  }
  
  /** Creates a new TfidfDocument RecordBuilder by copying an existing TfidfDocument instance */
  public static TfidfDocument.Builder newBuilder(TfidfDocument other) {
    return new TfidfDocument.Builder(other);
  }
  
  /**
   * RecordBuilder for TfidfDocument instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TfidfDocument>
    implements org.apache.avro.data.RecordBuilder<TfidfDocument> {

    private java.lang.CharSequence documentId;
    private java.util.List<java.lang.CharSequence> words;
    private java.util.List<java.lang.CharSequence> values;

    /** Creates a new Builder */
    private Builder() {
      super(TfidfDocument.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(TfidfDocument.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.documentId)) {
        this.documentId = data().deepCopy(fields()[0].schema(), other.documentId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.words)) {
        this.words = data().deepCopy(fields()[1].schema(), other.words);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.values)) {
        this.values = data().deepCopy(fields()[2].schema(), other.values);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing TfidfDocument instance */
    private Builder(TfidfDocument other) {
            super(TfidfDocument.SCHEMA$);
      if (isValidValue(fields()[0], other.documentId)) {
        this.documentId = data().deepCopy(fields()[0].schema(), other.documentId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.words)) {
        this.words = data().deepCopy(fields()[1].schema(), other.words);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.values)) {
        this.values = data().deepCopy(fields()[2].schema(), other.values);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'documentId' field */
    public java.lang.CharSequence getDocumentId() {
      return documentId;
    }
    
    /** Sets the value of the 'documentId' field */
    public TfidfDocument.Builder setDocumentId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.documentId = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'documentId' field has been set */
    public boolean hasDocumentId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'documentId' field */
    public TfidfDocument.Builder clearDocumentId() {
      documentId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'words' field */
    public java.util.List<java.lang.CharSequence> getWords() {
      return words;
    }
    
    /** Sets the value of the 'words' field */
    public TfidfDocument.Builder setWords(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[1], value);
      this.words = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'words' field has been set */
    public boolean hasWords() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'words' field */
    public TfidfDocument.Builder clearWords() {
      words = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'values' field */
    public java.util.List<java.lang.CharSequence> getValues() {
      return values;
    }
    
    /** Sets the value of the 'values' field */
    public TfidfDocument.Builder setValues(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.values = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'values' field has been set */
    public boolean hasValues() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'values' field */
    public TfidfDocument.Builder clearValues() {
      values = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public TfidfDocument build() {
      try {
        TfidfDocument record = new TfidfDocument();
        record.documentId = fieldSetFlags()[0] ? this.documentId : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.words = fieldSetFlags()[1] ? this.words : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[1]);
        record.values = fieldSetFlags()[2] ? this.values : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
