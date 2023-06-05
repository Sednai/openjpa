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
package org.apache.openjpa.jdbc.sql.partitionedinsert;

import java.io.Serializable;

/** C++ style pair helper used in parsers
 *
 * @author knienart
 * @version $Id: Pair.java 667952 2020-01-08 15:24:46Z gjevarda $
 * @param <L>
 * @param <R>
 */

public class Pair<L, R> implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private L left;
    private R right;
 
    /**
     * 
     * @return
     */
    public R getRight() {
        return right;
    }
 
    /**
     * 
     * @return
     */
    public L getLeft() {
        return left;
    }
    
    /**
     * 
     * @param right
     */
    public void setRight(R right) {
        this.right = right;
    }
    
    /**
     * 
     * @param left
     */
    public void setLeft(L left) {
        this.left = left;
    }
 
    /**
     * 
     * @param left
     * @param right
     */
    public Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }
    
    /**
     * 
     * @param left
     * @param right
     * @return
     */
	public static <A, B> Pair<A, B> create(A left, B right) {
        return new Pair<A, B>(left, right);
    }
 
	@Override
    public final boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
 
        final Pair<?, ?> other = (Pair) o;
        return equal(getLeft(), other.getLeft()) && equal(getRight(), other.getRight());
    }
    
	/**
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
 
    @Override
    public int hashCode() {
        int hLeft = getLeft() == null ? 0 : getLeft().hashCode();
        int hRight = getRight() == null ? 0 : getRight().hashCode();
 
        return hLeft + (57 * hRight);
    }

	@Override
	public String toString() {
		return "Pair [left=" + left + ", right=" + right + "]";
	}

    
}

