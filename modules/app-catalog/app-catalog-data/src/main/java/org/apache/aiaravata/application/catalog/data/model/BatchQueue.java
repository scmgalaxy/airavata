/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.aiaravata.application.catalog.data.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

@DataCache
@Entity
@Table(name = "BATCH_QUEUE")
@IdClass(BatchQueue_PK.class)
public class BatchQueue implements Serializable {
	
	@Id
	@Column(name = "COMPUTE_RESOURCE_ID")
	private String computeResourceId;
	
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "COMPUTE_RESOURCE_ID")
	private ComputeResource computeResource;
	
	@Column(name = "MAX_RUNTIME")
	private int maxRuntime;
	
	@Column(name = "MAX_JOB_IN_QUEUE")
	private int maxJobInQueue;
	
	@Column(name = "QUEUE_DESCRIPTION")
	private String queueDescription;
	
	@Id
	@Column(name = "QUEUE_NAME")
	private String queueName;
	
	@Column(name = "MAX_PROCESSORS")
	private int maxProcessors;
	
	@Column(name = "MAX_NODES")
	private int maxNodes;
	
	public String getComputeResourceId() {
		return computeResourceId;
	}
	
	public ComputeResource getComputeResource() {
		return computeResource;
	}
	
	public int getMaxRuntime() {
		return maxRuntime;
	}
	
	public int getMaxJobInQueue() {
		return maxJobInQueue;
	}
	
	public String getQueueDescription() {
		return queueDescription;
	}
	
	public String getQueueName() {
		return queueName;
	}
	
	public int getMaxProcessors() {
		return maxProcessors;
	}
	
	public int getMaxNodes() {
		return maxNodes;
	}
	
	public void setComputeResourceId(String computeResourceId) {
		this.computeResourceId=computeResourceId;
	}
	
	public void setComputeResource(ComputeResource computeResource) {
		this.computeResource=computeResource;
	}
	
	public void setMaxRuntime(int maxRuntime) {
		this.maxRuntime=maxRuntime;
	}
	
	public void setMaxJobInQueue(int maxJobInQueue) {
		this.maxJobInQueue=maxJobInQueue;
	}
	
	public void setQueueDescription(String queueDescription) {
		this.queueDescription=queueDescription;
	}
	
	public void setQueueName(String queueName) {
		this.queueName=queueName;
	}
	
	public void setMaxProcessors(int maxProcessors) {
		this.maxProcessors=maxProcessors;
	}
	
	public void setMaxNodes(int maxNodes) {
		this.maxNodes=maxNodes;
	}
}
