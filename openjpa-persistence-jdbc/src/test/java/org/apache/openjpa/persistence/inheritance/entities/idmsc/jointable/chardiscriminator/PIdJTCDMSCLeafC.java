/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.openjpa.persistence.inheritance.entities.idmsc.jointable.chardiscriminator;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import org.apache.openjpa.persistence.inheritance.entities.testinterfaces.LeafC;

@Entity
@DiscriminatorValue("F")
public class PIdJTCDMSCLeafC
extends PIdJTCDMSCMappedSuperclass implements LeafC {
    private String leafCData;

    @Override
    public String getLeafCData()
    {
        return leafCData;
    }

    @Override
    public void setLeafCData(String leafCData) {
        this.leafCData = leafCData;
    }
}
