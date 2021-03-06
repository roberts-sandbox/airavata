    /*
     * Licensed to the Apache Software Foundation (ASF) under one or more
     * contributor license agreements.  See the NOTICE file distributed with
     * this work for additional information regarding copyright ownership.
     * The ASF licenses this file to You under the Apache License, Version 2.0
     * (the "License"); you may not use this file except in compliance with
     * the License.  You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.model.workspace.experiment;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all") public class JobDetails implements org.apache.thrift.TBase<JobDetails, JobDetails._Fields>, java.io.Serializable, Cloneable, Comparable<JobDetails> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("JobDetails");

  private static final org.apache.thrift.protocol.TField JOB_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("jobID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField JOB_DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("jobDescription", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CREATION_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("creationTime", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField JOB_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("jobStatus", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField APPLICATION_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("applicationStatus", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField ERRORS_FIELD_DESC = new org.apache.thrift.protocol.TField("errors", org.apache.thrift.protocol.TType.LIST, (short)6);
  private static final org.apache.thrift.protocol.TField COMPUTE_RESOURCE_CONSUMED_FIELD_DESC = new org.apache.thrift.protocol.TField("computeResourceConsumed", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new JobDetailsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new JobDetailsTupleSchemeFactory());
  }

  private String jobID; // required
  private String jobDescription; // required
  private long creationTime; // optional
  private JobStatus jobStatus; // optional
  private ApplicationStatus applicationStatus; // optional
  private List<ErrorDetails> errors; // optional
  private String computeResourceConsumed; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  @SuppressWarnings("all") public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    JOB_ID((short)1, "jobID"),
    JOB_DESCRIPTION((short)2, "jobDescription"),
    CREATION_TIME((short)3, "creationTime"),
    JOB_STATUS((short)4, "jobStatus"),
    APPLICATION_STATUS((short)5, "applicationStatus"),
    ERRORS((short)6, "errors"),
    COMPUTE_RESOURCE_CONSUMED((short)7, "computeResourceConsumed");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // JOB_ID
          return JOB_ID;
        case 2: // JOB_DESCRIPTION
          return JOB_DESCRIPTION;
        case 3: // CREATION_TIME
          return CREATION_TIME;
        case 4: // JOB_STATUS
          return JOB_STATUS;
        case 5: // APPLICATION_STATUS
          return APPLICATION_STATUS;
        case 6: // ERRORS
          return ERRORS;
        case 7: // COMPUTE_RESOURCE_CONSUMED
          return COMPUTE_RESOURCE_CONSUMED;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __CREATIONTIME_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.CREATION_TIME,_Fields.JOB_STATUS,_Fields.APPLICATION_STATUS,_Fields.ERRORS,_Fields.COMPUTE_RESOURCE_CONSUMED};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.JOB_ID, new org.apache.thrift.meta_data.FieldMetaData("jobID", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.JOB_DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("jobDescription", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATION_TIME, new org.apache.thrift.meta_data.FieldMetaData("creationTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.JOB_STATUS, new org.apache.thrift.meta_data.FieldMetaData("jobStatus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, JobStatus.class)));
    tmpMap.put(_Fields.APPLICATION_STATUS, new org.apache.thrift.meta_data.FieldMetaData("applicationStatus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ApplicationStatus.class)));
    tmpMap.put(_Fields.ERRORS, new org.apache.thrift.meta_data.FieldMetaData("errors", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ErrorDetails.class))));
    tmpMap.put(_Fields.COMPUTE_RESOURCE_CONSUMED, new org.apache.thrift.meta_data.FieldMetaData("computeResourceConsumed", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(JobDetails.class, metaDataMap);
  }

  public JobDetails() {
    this.jobID = "DO_NOT_SET_AT_CLIENTS";

  }

  public JobDetails(
    String jobID,
    String jobDescription)
  {
    this();
    this.jobID = jobID;
    this.jobDescription = jobDescription;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public JobDetails(JobDetails other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetJobID()) {
      this.jobID = other.jobID;
    }
    if (other.isSetJobDescription()) {
      this.jobDescription = other.jobDescription;
    }
    this.creationTime = other.creationTime;
    if (other.isSetJobStatus()) {
      this.jobStatus = new JobStatus(other.jobStatus);
    }
    if (other.isSetApplicationStatus()) {
      this.applicationStatus = new ApplicationStatus(other.applicationStatus);
    }
    if (other.isSetErrors()) {
      List<ErrorDetails> __this__errors = new ArrayList<ErrorDetails>(other.errors.size());
      for (ErrorDetails other_element : other.errors) {
        __this__errors.add(new ErrorDetails(other_element));
      }
      this.errors = __this__errors;
    }
    if (other.isSetComputeResourceConsumed()) {
      this.computeResourceConsumed = other.computeResourceConsumed;
    }
  }

  public JobDetails deepCopy() {
    return new JobDetails(this);
  }

  @Override
  public void clear() {
    this.jobID = "DO_NOT_SET_AT_CLIENTS";

    this.jobDescription = null;
    setCreationTimeIsSet(false);
    this.creationTime = 0;
    this.jobStatus = null;
    this.applicationStatus = null;
    this.errors = null;
    this.computeResourceConsumed = null;
  }

  public String getJobID() {
    return this.jobID;
  }

  public void setJobID(String jobID) {
    this.jobID = jobID;
  }

  public void unsetJobID() {
    this.jobID = null;
  }

  /** Returns true if field jobID is set (has been assigned a value) and false otherwise */
  public boolean isSetJobID() {
    return this.jobID != null;
  }

  public void setJobIDIsSet(boolean value) {
    if (!value) {
      this.jobID = null;
    }
  }

  public String getJobDescription() {
    return this.jobDescription;
  }

  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }

  public void unsetJobDescription() {
    this.jobDescription = null;
  }

  /** Returns true if field jobDescription is set (has been assigned a value) and false otherwise */
  public boolean isSetJobDescription() {
    return this.jobDescription != null;
  }

  public void setJobDescriptionIsSet(boolean value) {
    if (!value) {
      this.jobDescription = null;
    }
  }

  public long getCreationTime() {
    return this.creationTime;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
    setCreationTimeIsSet(true);
  }

  public void unsetCreationTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATIONTIME_ISSET_ID);
  }

  /** Returns true if field creationTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreationTime() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATIONTIME_ISSET_ID);
  }

  public void setCreationTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATIONTIME_ISSET_ID, value);
  }

  public JobStatus getJobStatus() {
    return this.jobStatus;
  }

  public void setJobStatus(JobStatus jobStatus) {
    this.jobStatus = jobStatus;
  }

  public void unsetJobStatus() {
    this.jobStatus = null;
  }

  /** Returns true if field jobStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetJobStatus() {
    return this.jobStatus != null;
  }

  public void setJobStatusIsSet(boolean value) {
    if (!value) {
      this.jobStatus = null;
    }
  }

  public ApplicationStatus getApplicationStatus() {
    return this.applicationStatus;
  }

  public void setApplicationStatus(ApplicationStatus applicationStatus) {
    this.applicationStatus = applicationStatus;
  }

  public void unsetApplicationStatus() {
    this.applicationStatus = null;
  }

  /** Returns true if field applicationStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetApplicationStatus() {
    return this.applicationStatus != null;
  }

  public void setApplicationStatusIsSet(boolean value) {
    if (!value) {
      this.applicationStatus = null;
    }
  }

  public int getErrorsSize() {
    return (this.errors == null) ? 0 : this.errors.size();
  }

  public java.util.Iterator<ErrorDetails> getErrorsIterator() {
    return (this.errors == null) ? null : this.errors.iterator();
  }

  public void addToErrors(ErrorDetails elem) {
    if (this.errors == null) {
      this.errors = new ArrayList<ErrorDetails>();
    }
    this.errors.add(elem);
  }

  public List<ErrorDetails> getErrors() {
    return this.errors;
  }

  public void setErrors(List<ErrorDetails> errors) {
    this.errors = errors;
  }

  public void unsetErrors() {
    this.errors = null;
  }

  /** Returns true if field errors is set (has been assigned a value) and false otherwise */
  public boolean isSetErrors() {
    return this.errors != null;
  }

  public void setErrorsIsSet(boolean value) {
    if (!value) {
      this.errors = null;
    }
  }

  public String getComputeResourceConsumed() {
    return this.computeResourceConsumed;
  }

  public void setComputeResourceConsumed(String computeResourceConsumed) {
    this.computeResourceConsumed = computeResourceConsumed;
  }

  public void unsetComputeResourceConsumed() {
    this.computeResourceConsumed = null;
  }

  /** Returns true if field computeResourceConsumed is set (has been assigned a value) and false otherwise */
  public boolean isSetComputeResourceConsumed() {
    return this.computeResourceConsumed != null;
  }

  public void setComputeResourceConsumedIsSet(boolean value) {
    if (!value) {
      this.computeResourceConsumed = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case JOB_ID:
      if (value == null) {
        unsetJobID();
      } else {
        setJobID((String)value);
      }
      break;

    case JOB_DESCRIPTION:
      if (value == null) {
        unsetJobDescription();
      } else {
        setJobDescription((String)value);
      }
      break;

    case CREATION_TIME:
      if (value == null) {
        unsetCreationTime();
      } else {
        setCreationTime((Long)value);
      }
      break;

    case JOB_STATUS:
      if (value == null) {
        unsetJobStatus();
      } else {
        setJobStatus((JobStatus)value);
      }
      break;

    case APPLICATION_STATUS:
      if (value == null) {
        unsetApplicationStatus();
      } else {
        setApplicationStatus((ApplicationStatus)value);
      }
      break;

    case ERRORS:
      if (value == null) {
        unsetErrors();
      } else {
        setErrors((List<ErrorDetails>)value);
      }
      break;

    case COMPUTE_RESOURCE_CONSUMED:
      if (value == null) {
        unsetComputeResourceConsumed();
      } else {
        setComputeResourceConsumed((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case JOB_ID:
      return getJobID();

    case JOB_DESCRIPTION:
      return getJobDescription();

    case CREATION_TIME:
      return Long.valueOf(getCreationTime());

    case JOB_STATUS:
      return getJobStatus();

    case APPLICATION_STATUS:
      return getApplicationStatus();

    case ERRORS:
      return getErrors();

    case COMPUTE_RESOURCE_CONSUMED:
      return getComputeResourceConsumed();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case JOB_ID:
      return isSetJobID();
    case JOB_DESCRIPTION:
      return isSetJobDescription();
    case CREATION_TIME:
      return isSetCreationTime();
    case JOB_STATUS:
      return isSetJobStatus();
    case APPLICATION_STATUS:
      return isSetApplicationStatus();
    case ERRORS:
      return isSetErrors();
    case COMPUTE_RESOURCE_CONSUMED:
      return isSetComputeResourceConsumed();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof JobDetails)
      return this.equals((JobDetails)that);
    return false;
  }

  public boolean equals(JobDetails that) {
    if (that == null)
      return false;

    boolean this_present_jobID = true && this.isSetJobID();
    boolean that_present_jobID = true && that.isSetJobID();
    if (this_present_jobID || that_present_jobID) {
      if (!(this_present_jobID && that_present_jobID))
        return false;
      if (!this.jobID.equals(that.jobID))
        return false;
    }

    boolean this_present_jobDescription = true && this.isSetJobDescription();
    boolean that_present_jobDescription = true && that.isSetJobDescription();
    if (this_present_jobDescription || that_present_jobDescription) {
      if (!(this_present_jobDescription && that_present_jobDescription))
        return false;
      if (!this.jobDescription.equals(that.jobDescription))
        return false;
    }

    boolean this_present_creationTime = true && this.isSetCreationTime();
    boolean that_present_creationTime = true && that.isSetCreationTime();
    if (this_present_creationTime || that_present_creationTime) {
      if (!(this_present_creationTime && that_present_creationTime))
        return false;
      if (this.creationTime != that.creationTime)
        return false;
    }

    boolean this_present_jobStatus = true && this.isSetJobStatus();
    boolean that_present_jobStatus = true && that.isSetJobStatus();
    if (this_present_jobStatus || that_present_jobStatus) {
      if (!(this_present_jobStatus && that_present_jobStatus))
        return false;
      if (!this.jobStatus.equals(that.jobStatus))
        return false;
    }

    boolean this_present_applicationStatus = true && this.isSetApplicationStatus();
    boolean that_present_applicationStatus = true && that.isSetApplicationStatus();
    if (this_present_applicationStatus || that_present_applicationStatus) {
      if (!(this_present_applicationStatus && that_present_applicationStatus))
        return false;
      if (!this.applicationStatus.equals(that.applicationStatus))
        return false;
    }

    boolean this_present_errors = true && this.isSetErrors();
    boolean that_present_errors = true && that.isSetErrors();
    if (this_present_errors || that_present_errors) {
      if (!(this_present_errors && that_present_errors))
        return false;
      if (!this.errors.equals(that.errors))
        return false;
    }

    boolean this_present_computeResourceConsumed = true && this.isSetComputeResourceConsumed();
    boolean that_present_computeResourceConsumed = true && that.isSetComputeResourceConsumed();
    if (this_present_computeResourceConsumed || that_present_computeResourceConsumed) {
      if (!(this_present_computeResourceConsumed && that_present_computeResourceConsumed))
        return false;
      if (!this.computeResourceConsumed.equals(that.computeResourceConsumed))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(JobDetails other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetJobID()).compareTo(other.isSetJobID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobID, other.jobID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJobDescription()).compareTo(other.isSetJobDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobDescription, other.jobDescription);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreationTime()).compareTo(other.isSetCreationTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreationTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.creationTime, other.creationTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJobStatus()).compareTo(other.isSetJobStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobStatus, other.jobStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetApplicationStatus()).compareTo(other.isSetApplicationStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApplicationStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.applicationStatus, other.applicationStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetErrors()).compareTo(other.isSetErrors());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrors()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errors, other.errors);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetComputeResourceConsumed()).compareTo(other.isSetComputeResourceConsumed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComputeResourceConsumed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.computeResourceConsumed, other.computeResourceConsumed);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("JobDetails(");
    boolean first = true;

    sb.append("jobID:");
    if (this.jobID == null) {
      sb.append("null");
    } else {
      sb.append(this.jobID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("jobDescription:");
    if (this.jobDescription == null) {
      sb.append("null");
    } else {
      sb.append(this.jobDescription);
    }
    first = false;
    if (isSetCreationTime()) {
      if (!first) sb.append(", ");
      sb.append("creationTime:");
      sb.append(this.creationTime);
      first = false;
    }
    if (isSetJobStatus()) {
      if (!first) sb.append(", ");
      sb.append("jobStatus:");
      if (this.jobStatus == null) {
        sb.append("null");
      } else {
        sb.append(this.jobStatus);
      }
      first = false;
    }
    if (isSetApplicationStatus()) {
      if (!first) sb.append(", ");
      sb.append("applicationStatus:");
      if (this.applicationStatus == null) {
        sb.append("null");
      } else {
        sb.append(this.applicationStatus);
      }
      first = false;
    }
    if (isSetErrors()) {
      if (!first) sb.append(", ");
      sb.append("errors:");
      if (this.errors == null) {
        sb.append("null");
      } else {
        sb.append(this.errors);
      }
      first = false;
    }
    if (isSetComputeResourceConsumed()) {
      if (!first) sb.append(", ");
      sb.append("computeResourceConsumed:");
      if (this.computeResourceConsumed == null) {
        sb.append("null");
      } else {
        sb.append(this.computeResourceConsumed);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetJobID()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'jobID' is unset! Struct:" + toString());
    }

    if (!isSetJobDescription()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'jobDescription' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (jobStatus != null) {
      jobStatus.validate();
    }
    if (applicationStatus != null) {
      applicationStatus.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class JobDetailsStandardSchemeFactory implements SchemeFactory {
    public JobDetailsStandardScheme getScheme() {
      return new JobDetailsStandardScheme();
    }
  }

  private static class JobDetailsStandardScheme extends StandardScheme<JobDetails> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, JobDetails struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // JOB_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.jobID = iprot.readString();
              struct.setJobIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // JOB_DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.jobDescription = iprot.readString();
              struct.setJobDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CREATION_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.creationTime = iprot.readI64();
              struct.setCreationTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // JOB_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.jobStatus = new JobStatus();
              struct.jobStatus.read(iprot);
              struct.setJobStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // APPLICATION_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.applicationStatus = new ApplicationStatus();
              struct.applicationStatus.read(iprot);
              struct.setApplicationStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ERRORS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.errors = new ArrayList<ErrorDetails>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  ErrorDetails _elem10;
                  _elem10 = new ErrorDetails();
                  _elem10.read(iprot);
                  struct.errors.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setErrorsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // COMPUTE_RESOURCE_CONSUMED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.computeResourceConsumed = iprot.readString();
              struct.setComputeResourceConsumedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, JobDetails struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.jobID != null) {
        oprot.writeFieldBegin(JOB_ID_FIELD_DESC);
        oprot.writeString(struct.jobID);
        oprot.writeFieldEnd();
      }
      if (struct.jobDescription != null) {
        oprot.writeFieldBegin(JOB_DESCRIPTION_FIELD_DESC);
        oprot.writeString(struct.jobDescription);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCreationTime()) {
        oprot.writeFieldBegin(CREATION_TIME_FIELD_DESC);
        oprot.writeI64(struct.creationTime);
        oprot.writeFieldEnd();
      }
      if (struct.jobStatus != null) {
        if (struct.isSetJobStatus()) {
          oprot.writeFieldBegin(JOB_STATUS_FIELD_DESC);
          struct.jobStatus.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.applicationStatus != null) {
        if (struct.isSetApplicationStatus()) {
          oprot.writeFieldBegin(APPLICATION_STATUS_FIELD_DESC);
          struct.applicationStatus.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.errors != null) {
        if (struct.isSetErrors()) {
          oprot.writeFieldBegin(ERRORS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.errors.size()));
            for (ErrorDetails _iter11 : struct.errors)
            {
              _iter11.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.computeResourceConsumed != null) {
        if (struct.isSetComputeResourceConsumed()) {
          oprot.writeFieldBegin(COMPUTE_RESOURCE_CONSUMED_FIELD_DESC);
          oprot.writeString(struct.computeResourceConsumed);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class JobDetailsTupleSchemeFactory implements SchemeFactory {
    public JobDetailsTupleScheme getScheme() {
      return new JobDetailsTupleScheme();
    }
  }

  private static class JobDetailsTupleScheme extends TupleScheme<JobDetails> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, JobDetails struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.jobID);
      oprot.writeString(struct.jobDescription);
      BitSet optionals = new BitSet();
      if (struct.isSetCreationTime()) {
        optionals.set(0);
      }
      if (struct.isSetJobStatus()) {
        optionals.set(1);
      }
      if (struct.isSetApplicationStatus()) {
        optionals.set(2);
      }
      if (struct.isSetErrors()) {
        optionals.set(3);
      }
      if (struct.isSetComputeResourceConsumed()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetCreationTime()) {
        oprot.writeI64(struct.creationTime);
      }
      if (struct.isSetJobStatus()) {
        struct.jobStatus.write(oprot);
      }
      if (struct.isSetApplicationStatus()) {
        struct.applicationStatus.write(oprot);
      }
      if (struct.isSetErrors()) {
        {
          oprot.writeI32(struct.errors.size());
          for (ErrorDetails _iter12 : struct.errors)
          {
            _iter12.write(oprot);
          }
        }
      }
      if (struct.isSetComputeResourceConsumed()) {
        oprot.writeString(struct.computeResourceConsumed);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, JobDetails struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.jobID = iprot.readString();
      struct.setJobIDIsSet(true);
      struct.jobDescription = iprot.readString();
      struct.setJobDescriptionIsSet(true);
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.creationTime = iprot.readI64();
        struct.setCreationTimeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.jobStatus = new JobStatus();
        struct.jobStatus.read(iprot);
        struct.setJobStatusIsSet(true);
      }
      if (incoming.get(2)) {
        struct.applicationStatus = new ApplicationStatus();
        struct.applicationStatus.read(iprot);
        struct.setApplicationStatusIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.errors = new ArrayList<ErrorDetails>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            ErrorDetails _elem15;
            _elem15 = new ErrorDetails();
            _elem15.read(iprot);
            struct.errors.add(_elem15);
          }
        }
        struct.setErrorsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.computeResourceConsumed = iprot.readString();
        struct.setComputeResourceConsumedIsSet(true);
      }
    }
  }

}

