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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotated interface should be treated as a managed interface by OpenJPA.
 * New instances of this type can be created by invoking
 * {@link OpenJPAEntityManager#createInstance(Class)}.
 * Interfaces with this annotation should also be annotated with one of the JPA
 * entity annotations ({@link jakarta.persistence.Entity @Entity},
 * {@link jakarta.persistence.MappedSuperclass @MappedSuperclass},
 * or {@link jakarta.persistence.Embeddable @Embeddable}).
 *
 * @since 1.1.0
 * @published
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface ManagedInterface {
}
