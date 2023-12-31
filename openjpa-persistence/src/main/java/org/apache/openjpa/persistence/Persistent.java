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
package org.apache.openjpa.persistence;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

/**
 * Metadata annotation for a persistent field.
 *
 * @author Abe White
 * @since 0.4.0
 * @published
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Persistent {

    String mappedBy() default "";

    CascadeType[] cascade() default {};

    boolean optional() default true;

    boolean embedded() default false;

    FetchType fetch() default FetchType.EAGER;
}
