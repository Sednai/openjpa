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

import org.apache.openjpa.jdbc.schema.Table;


/**
 * @author knienart
 * @version $Id: PartitionRecord.java 667952 2020-01-08 15:24:46Z gjevarda $
 * Holder of the information about the partition, with the range and attribute associated. 
 * It's main role is to get the table name for a specific range.
 */
public class PartitionRecord<T> {

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * Construct partitioning range with a specific table name associated with it
	 * @param parentName TODO
	 * @param tableName
	 * @param attributeName
	 * @param level
	 * @param table TODO
	 * @param range
	 */
	public PartitionRecord(String parentName, String tableName, String attributeName, T left, T right, short level, Table table) {
		this.parentName = parentName;
		this.tableName = tableName;
		this.attributeName = attributeName;
		this.range = new Pair<T,T>(left,right);
		this.level = level;
		this.table = table;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartitionRecord other = (PartitionRecord) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (range == null) {
			if (other.range != null)
				return false;
		} else if (!range.equals(other.range))
			return false;
		return true;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @return the range
	 */
	public Pair<T, T> getRange() {
		return range;
	}
	/**
	 * @return the level
	 */
	public Short getLevel() {
		return level;
	}
	/**
	 * @return the table
	 */
	protected Table getTable() {
		return table;
	}
	protected final String parentName;
	protected final String tableName;
	protected final String attributeName;
	protected final Pair<T,T> range;
	protected final short level;
	protected Table table;
	@Override
	public String toString() {
		return "PartitionRecord [parentName=" + parentName + ", tableName=" + tableName + ", attributeName="
				+ attributeName + ", range=" + range + ", level=" + level + ", table=" + table + "]";
	}
	

}
