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
package org.apache.openjpa.persistence.cascade.pudefault;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class PUDEntityA01 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    private String strData;

    @ManyToMany
    private Collection<PUDEntityB> colM2M;

    @OneToMany
    private Collection<PUDEntityB> colO2M;

    @ManyToOne
    private PUDEntityB m2o;

    @OneToOne
    private PUDEntityB o2o;

    public PUDEntityA01() {
        colM2M = new ArrayList<>();
        colO2M = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public Collection<PUDEntityB> getColM2M() {
        return colM2M;
    }

    public void setColM2M(Collection<PUDEntityB> colM2M) {
        this.colM2M = colM2M;
    }

    public Collection<PUDEntityB> getColO2M() {
        return colO2M;
    }

    public void setColO2M(Collection<PUDEntityB> colO2M) {
        this.colO2M = colO2M;
    }

    public PUDEntityB getM2o() {
        return m2o;
    }

    public void setM2o(PUDEntityB m2o) {
        this.m2o = m2o;
    }

    public PUDEntityB getO2o() {
        return o2o;
    }

    public void setO2o(PUDEntityB o2o) {
        this.o2o = o2o;
    }

    @Override
    public String toString() {
        return "PUDEntityA01 [id=" + id + ", strData=" + strData + ", colM2M="
            + colM2M + ", colO2M=" + colO2M + ", m2o=" + m2o + ", o2o=" + o2o
            + "]";
    }


}
