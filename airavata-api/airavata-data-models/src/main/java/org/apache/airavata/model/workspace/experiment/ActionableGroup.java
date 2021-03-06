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


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

@SuppressWarnings("all") public enum ActionableGroup implements org.apache.thrift.TEnum {
  RESOURCE_ADMINS(0),
  AIRAVATA_ADMINS(1),
  GATEWAYS_ADMINS(2),
  USER(3),
  CANNOT_BE_DETERMINED(4);

  private final int value;

  private ActionableGroup(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ActionableGroup findByValue(int value) { 
    switch (value) {
      case 0:
        return RESOURCE_ADMINS;
      case 1:
        return AIRAVATA_ADMINS;
      case 2:
        return GATEWAYS_ADMINS;
      case 3:
        return USER;
      case 4:
        return CANNOT_BE_DETERMINED;
      default:
        return null;
    }
  }
}
