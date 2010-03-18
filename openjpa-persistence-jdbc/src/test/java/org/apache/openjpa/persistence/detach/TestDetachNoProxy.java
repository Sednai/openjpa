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
package org.apache.openjpa.persistence.detach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.openjpa.conf.Compatibility;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.test.SingleEMFTestCase;

public class TestDetachNoProxy extends SingleEMFTestCase {
    
    private static final int numEntities = 3;
    private static final String PROXY = new String("$proxy");
    private Log log;
    
    public void setUp() {
        setUp(DROP_TABLES, Entity20.class);
        log = emf.getConfiguration().getLog("test");
        
        // check and set Compatibility values to new 2.0 values
        Compatibility compat = emf.getConfiguration().getCompatibilityInstance();
        assertNotNull(compat);
        if (log.isTraceEnabled()) {
            log.trace("Before set, FlushBeforeDetach=" + compat.getFlushBeforeDetach());
            log.trace("Before set, CopyOnDetach=" + compat.getCopyOnDetach());
            log.trace("Before set, CascadeWithDetach=" + compat.getCascadeWithDetach());
        }
        compat.setFlushBeforeDetach(false);
        compat.setCopyOnDetach(false);
        compat.setCascadeWithDetach(false);
        if (log.isTraceEnabled()) {
            log.trace("After set, FlushBeforeDetach=" + compat.getFlushBeforeDetach());
            log.trace("After set, CopyOnDetach=" + compat.getCopyOnDetach());
            log.trace("After set, CascadeWithDetach=" + compat.getCascadeWithDetach());
        }
        createEntities(numEntities);
    }
    
    private void createEntities(int count) {
        Entity20 e20 = null;
        OpenJPAEntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (int i=0; i<count; i++) {
            e20 = new Entity20(i);
            em.persist(e20);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    /* 
     * Verify that an in-place detached entity does not use the proxy classes.
     */
    public void testDetach() {
        if (log.isTraceEnabled())
            log.trace("***** testDetach() *****");
        Integer id = new Integer(0);
        OpenJPAEntityManager em = emf.createEntityManager();
        
        em.clear();
        Entity20 e20 = em.find(Entity20.class, id);
        if (log.isTraceEnabled())
            log.trace("** after find");
        assertTrue(em.contains(e20));
        assertFalse(em.isDetached(e20));
        verifySerializable(e20, true, false);
        
        // new openjpa-2.0.0 behavior, where detach() doesn't return updated entity, but does it in-place
        em.detach(e20);
        if (log.isTraceEnabled())
            log.trace("** after detach");
        // in-place updated entity should not have any proxy classes and should be detached
        assertFalse(em.contains(e20));
        assertTrue(em.isDetached(e20));
        verifySerializable(e20, false, false);
               
        em.close();
    }

    /* 
     * Verify that a detachCopy() returned entity does not contain any proxy classes.
     */
    public void testDetachCopy() {
        if (log.isTraceEnabled())
            log.trace("***** testDetachCopy() *****");
        Integer id = new Integer(0);
        OpenJPAEntityManager em = emf.createEntityManager();
        em.clear();

        Entity20 e20 = em.find(Entity20.class, id);
        if (log.isTraceEnabled())
            log.trace("** after find");
        assertTrue(em.contains(e20));
        assertFalse(em.isDetached(e20));
        verifySerializable(e20, true, false);
        
        // Test new detachCopy() method added in 2.0.0
        Entity20 e20copy = em.detachCopy(e20);
        if (log.isTraceEnabled())
            log.trace("** after detachCopy");
        // verify e20 is same as above
        assertTrue(em.contains(e20));
        assertFalse(em.isDetached(e20));
        verifySerializable(e20, true, false);
        // verify copy does not have any proxy classes (in-place updated) is detached
        assertFalse(em.contains(e20copy));
        assertTrue(em.isDetached(e20copy));
        verifySerializable(e20copy, false, false);
        
        em.close();
    }

    /*
     * Verify that in-place detachAll entities do not use the proxy classes.
     */
    public void testDetachAll() {
        if (log.isTraceEnabled())
            log.trace("***** testDetachAll() *****");
        OpenJPAEntityManager em = emf.createEntityManager();
        em.clear();

        ArrayList<Entity20> e20List = new ArrayList<Entity20>(numEntities);
        for (int i=0; i<numEntities; i++) {
            Entity20 e20 = em.find(Entity20.class, new Integer(i));
            e20List.add(e20);
            if (log.isTraceEnabled())
                log.trace("** after find Entity20(" + i + ")");
            assertTrue(em.contains(e20));
            assertFalse(em.isDetached(e20));
            verifySerializable(e20, true, false);            
        }

        // new openjpa-2.0.0 behavior, where detachAll() updates entities in-place
        // ArrayList<Entity20> e20ListCopy = new ArrayList<Entity20>(em.detachAll(e20List));
        // em.detachAll(e20List);   // for some reason calling with Collection causes a NPE, so use Object[] instead
        em.detachAll(e20List.get(0), e20List.get(1), e20List.get(2));
        for (int i=0; i<numEntities; i++) {
            if (log.isTraceEnabled())
                log.trace("** after EM.detachAll() verify e20List(" + i + ")");
            Entity20 e20 = e20List.get(i);
            // entity should not have any proxy classes (in-place updated) and is detached
            assertFalse(em.contains(e20));
            assertTrue(em.isDetached(e20));
            verifySerializable(e20, false, false);
        }

        em.close();
    }

    /*
     * Verify that after EM.clear() entities still contain proxy classes.
     */
    public void testClear() {
        if (log.isTraceEnabled())
            log.trace("***** testClear() *****");
        OpenJPAEntityManager em = emf.createEntityManager();
        em.clear();

        ArrayList<Entity20> e20List = new ArrayList<Entity20>(numEntities);
        for (int i=0; i<numEntities; i++) {
            Entity20 e20 = em.find(Entity20.class, new Integer(i));
            e20List.add(e20);
            if (log.isTraceEnabled())
                log.trace("** after find Entity20(" + i + ")");
            assertTrue(em.contains(e20));
            assertFalse(em.isDetached(e20));
            verifySerializable(e20, true, false);            
        }

        em.clear();

        for (int i=0; i<numEntities; i++) {
            if (log.isTraceEnabled())
                log.trace("** after EM.clear() verify Entity20(" + i + ")");
            Entity20 e20 = e20List.get(i);
            assertFalse(em.contains(e20));
            assertTrue(em.isDetached(e20));
            // entity should still have proxy classes and is detached,
            // Old 1.2.x Behavior -
            //   the $proxy classes are not removed during serialization
            // verifySerializable(e20, true, true);
            // OPENJPA-1097 New behavior - $proxy classes are removed
            verifySerializable(e20, true, false);
        }

        em.close();
    }

    /**
     * Test that the entity is/is not using our $proxy classes before
     * and after serialization.
     *
     * @param e20 Entity to test.
     * @param usesProxyBefore verify that the entity uses the $proxy classes
     *        before serialization if true and does not if false.
     * @param usesProxyAfter verify that the entity uses the $proxy classes
     *        after serialization if true and does not if false.
     */
    private void verifySerializable(Entity20 e20, boolean usesProxyBefore,
            boolean usesProxyAfter) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] e20bytes = null;
        
        if (log.isTraceEnabled())
            log.trace("verifySerializable() - before serialize");
        verifyEntities(e20, usesProxyBefore);

        // first serialize
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(e20);
            e20bytes = baos.toByteArray();
        } catch (IOException e) {
            fail(e.toString());
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
            }
        }
        
        // then deserialize and assert no $proxy classes exist
        ByteArrayInputStream bais = new ByteArrayInputStream(e20bytes);
        ObjectInputStream ois = null;
        Entity20 e20new = null;
        try {
            ois = new ObjectInputStream(bais);
            e20new = (Entity20) ois.readObject();
            if (log.isTraceEnabled())
                log.trace("verifySerializable() - after deserialize");
            verifyEntities(e20new, usesProxyAfter);
        } catch (IOException e) {
            fail(e.toString());
        } catch (ClassNotFoundException e) {
            fail(e.toString());
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
            }
        }

    }

    private void verifyEntities(Entity20 e20, boolean usesProxy) {
        if (log.isTraceEnabled()) {
            log.trace("verifyEntities() - asserting expected proxy usage is " + usesProxy);
            printClassNames(e20);
        }
        assertTrue("Expected sqlDate endsWith($proxy) to return " + usesProxy,
            usesProxy == e20.getDate().getClass().getCanonicalName().endsWith(PROXY));
        assertTrue("Expected sqlTime endsWith($proxy) to return " + usesProxy,
            usesProxy == e20.getTime().getClass().getCanonicalName().endsWith(PROXY));
        assertTrue("Expected sqlTimestamp endsWith($proxy) to return " + usesProxy,
            usesProxy == e20.getTimestamp().getClass().getCanonicalName().endsWith(PROXY));
        
    }
    
    private void printClassNames(Entity20 e20) {
        if (log.isTraceEnabled()) {
            log.trace("sqlDate = " + e20.getDate().getClass().getCanonicalName());
            log.trace("sqlTime = " + e20.getTime().getClass().getCanonicalName());
            log.trace("sqlTimestamp = " + e20.getTimestamp().getClass().getCanonicalName());
        }
    }
}