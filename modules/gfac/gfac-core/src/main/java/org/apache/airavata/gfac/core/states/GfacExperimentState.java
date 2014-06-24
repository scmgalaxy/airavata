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
package org.apache.airavata.gfac.core.states;


public enum GfacExperimentState {
    LAUNCHED(0),
    ACCEPTED(1),
    INHANDLERSINVOKING(2),
    INHANDLERSINVOKED(3),
    PROVIDERINVOKING(4),
    PROVIDERINVOKED(5),
    OUTHANDLERSINVOKING(6),
    OUTHANDLERSINVOKED(7),
    COMPLETED(8),
    FAILED(9),
    UNKNOWN(10);

    private final int value;

    private GfacExperimentState(int value) {
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
     *
     * @return null if the value is not found.
     */
    public static GfacExperimentState findByValue(int value) {
        switch (value) {
            case 0:
                return INHANDLERSINVOKING;
            case 1:
                return INHANDLERSINVOKED;
            case 2:
                return PROVIDERINVOKING;
            case 3:
                return PROVIDERINVOKED;
            case 4:
                return OUTHANDLERSINVOKING;
            case 5:
                return OUTHANDLERSINVOKED;
            case 6:
                return COMPLETED;
            case 7:
                return FAILED;
            case 8:
                return UNKNOWN;
            default:
                return null;
        }
    }
}
