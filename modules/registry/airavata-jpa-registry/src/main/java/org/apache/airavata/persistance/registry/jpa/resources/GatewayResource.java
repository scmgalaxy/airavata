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
package org.apache.airavata.persistance.registry.jpa.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.airavata.persistance.registry.jpa.Resource;
import org.apache.airavata.persistance.registry.jpa.ResourceType;
import org.apache.airavata.persistance.registry.jpa.model.Application_Descriptor;
import org.apache.airavata.persistance.registry.jpa.model.Experiment;
import org.apache.airavata.persistance.registry.jpa.model.Gateway;
import org.apache.airavata.persistance.registry.jpa.model.Host_Descriptor;
import org.apache.airavata.persistance.registry.jpa.model.Project;
import org.apache.airavata.persistance.registry.jpa.model.Published_Workflow;
import org.apache.airavata.persistance.registry.jpa.model.Service_Descriptor;
import org.apache.airavata.persistance.registry.jpa.model.Users;

public class GatewayResource extends AbstractResource {
    private String gatewayName;
    private String owner;

    public GatewayResource(String gatewayName) {
    	setGatewayName(gatewayName);
	}
    
    public GatewayResource() {
	}
    
    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Resource create(ResourceType type) {
        if (type == ResourceType.PROJECT) {
            ProjectResource projectResource = new ProjectResource();
            projectResource.setGateway(this);
            return projectResource;
        } else if (type == ResourceType.USER) {
            UserResource userResource = new UserResource();
            userResource.setGatewayName(this.getGatewayName());
            return userResource;
        } else if (type == ResourceType.PUBLISHED_WORKFLOW) {
            PublishWorkflowResource publishWorkflowResource = new PublishWorkflowResource();
            publishWorkflowResource.setGateway(this);
            return publishWorkflowResource;
        }else if(type == ResourceType.USER_WORKFLOW){
            UserWorkflowResource userWorkflowResource = new UserWorkflowResource();
            userWorkflowResource.setGateway(this);
            return userWorkflowResource;
        }else if (type == ResourceType.HOST_DESCRIPTOR) {
            HostDescriptorResource hostDescriptorResource = new HostDescriptorResource();
            hostDescriptorResource.setGatewayName(gatewayName);
            return hostDescriptorResource;
        } else if (type == ResourceType.SERVICE_DESCRIPTOR) {
            ServiceDescriptorResource serviceDescriptorResource = new ServiceDescriptorResource();
            serviceDescriptorResource.setGatewayName(gatewayName);
            return serviceDescriptorResource;
        } else if (type == ResourceType.APPLICATION_DESCRIPTOR) {
            ApplicationDescriptorResource applicationDescriptorResource = new ApplicationDescriptorResource();
            applicationDescriptorResource.setGatewayName(gatewayName);
            return applicationDescriptorResource;
        } else if(type == ResourceType.EXPERIMENT){
            ExperimentResource experimentResource =new ExperimentResource();
            experimentResource.setGateway(this);
            return experimentResource;
        }else {
            return null;
        }
    }

    public void remove(ResourceType type, Object name) {
        begin();
        if (type == ResourceType.USER) {
            Query q = em.createQuery("Delete p FROM Users p WHERE p.user_name = :usr_name and p.gateway_name = :gate_name");
            q.setParameter("usr_name", name);
            q.setParameter("gate_name", gatewayName);
            q.executeUpdate();
        }
        else if (type == ResourceType.PUBLISHED_WORKFLOW) {
            Query q = em.createQuery("Delete p FROM Published_Workflow p WHERE p.publish_workflow_name = :pub_workflow_id and p.gateway_name = :gate_name");
            q.setParameter("pub_workflow_id", name);
            q.setParameter("gate_name", gatewayName);
            q.executeUpdate();
            end();
        } else if (type == ResourceType.HOST_DESCRIPTOR) {
            begin();
            Query q = em.createQuery("Delete p FROM Host_Descriptor p WHERE p.host_descriptor_ID = :host_desc_id and p.gateway_name = :gate_name");
            q.setParameter("host_desc_id", name);
            q.setParameter("gate_name", gatewayName);
            q.executeUpdate();
        } else if (type == ResourceType.SERVICE_DESCRIPTOR) {
            Query q = em.createQuery("Delete p FROM Service_Descriptor p WHERE p.service_descriptor_ID = :service_desc_id and p.gateway_name = :gate_name");
            q.setParameter("service_desc_id", name);
            q.setParameter("gate_name", gatewayName);
            q.executeUpdate();
        }else if(type == ResourceType.EXPERIMENT){
            Query q = em.createQuery("Delete p FROM Experiment p WHERE p.experiment_ID = :exp_id and p.gateway_name = :gate_name");
            q.setParameter("exp_id", name);
            q.setParameter("gate_name", gatewayName);
            q.executeUpdate();
        }

//        else if (type == ResourceType.APPLICATION_DESCRIPTOR) {
//            Query q = em.createQuery("Delete p FROM Application_Descriptor p WHERE p.application_descriptor_ID = :app_desc_id and p.gateway_name = :gate_name");
//            q.setParameter("app_desc_id", name);
//            q.setParameter("gate_name", gatewayName);
//            q.executeUpdate();
//        }

        end();


    }

    public void removeMe(Object[] keys) {
    }

    public Resource get(ResourceType type, Object name) {
        begin();
//        if (type == ResourceType.PROJECT) {
//            Query q = em.createQuery("SELECT p FROM Project p WHERE p.project_name = :proj_name and p.gateway_name =:gate_name");
//            q.setParameter("proj_name", name);
//            q.setParameter("gate_name", gatewayName);
//            Project eproject = (Project) q.getSingleResult();
//            ProjectResource projectResource = new ProjectResource(eproject.getProject_ID());
//            projectResource.setName(eproject.getProject_name());
//            end();
//            return projectResource;
//        } else
        if (type == ResourceType.USER) {
            Query q = em.createQuery("SELECT p FROM Gateway_Worker p WHERE p.user_name = :username and p.gateway_name =:gate_name");
            q.setParameter("username", name);
            q.setParameter("gate_name", gatewayName);
            Users eUser = (Users) q.getSingleResult();
            UserResource userResource = new UserResource();
            userResource.setUserName(eUser.getUser_name());
            userResource.setGatewayName(gatewayName);
            end();
            return userResource;
        } else if (type == ResourceType.PUBLISHED_WORKFLOW) {
            Query q = em.createQuery("SELECT p FROM Published_Workflow p WHERE p.publish_workflow_name = :pub_workflow_name and p.gateway_name =:gate_name");
            q.setParameter("pub_workflow_name", name);
            q.setParameter("gate_name", gatewayName);
            Published_Workflow ePub_workflow = (Published_Workflow) q.getSingleResult();
            PublishWorkflowResource publishWorkflowResource = new PublishWorkflowResource(this);
            publishWorkflowResource.setName(ePub_workflow.getPublish_workflow_name());
            publishWorkflowResource.setContent(ePub_workflow.getWorkflow_content());
            publishWorkflowResource.setPublishedDate(ePub_workflow.getPublished_date());
            publishWorkflowResource.setVersion(ePub_workflow.getVersion());
            end();
            return publishWorkflowResource;
        } else if (type == ResourceType.SERVICE_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Service_Descriptor p WHERE p.service_descriptor_ID = :service_desc_id and p.gateway_name =:gate_name");
            q.setParameter("service_desc_id", name);
            q.setParameter("gate_name", gatewayName);
            Service_Descriptor eServiceDesc = (Service_Descriptor) q.getSingleResult();
            ServiceDescriptorResource serviceDescriptorResource = new ServiceDescriptorResource(eServiceDesc.getService_descriptor_ID());
            serviceDescriptorResource.setGatewayName(eServiceDesc.getGateway().getGateway_name());
            serviceDescriptorResource.setContent(eServiceDesc.getService_descriptor_xml());
            end();
            return serviceDescriptorResource;
        } else if (type == ResourceType.HOST_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Host_Descriptor p WHERE p.host_descriptor_ID = :host_desc_id and p.gateway_name =:gate_name");
            q.setParameter("host_desc_id", name);
            q.setParameter("gate_name", gatewayName);
            Host_Descriptor eHostDesc = (Host_Descriptor) q.getSingleResult();
            HostDescriptorResource hostDescriptorResource = new HostDescriptorResource(eHostDesc.getHost_descriptor_ID());
            hostDescriptorResource.setGatewayName(eHostDesc.getGateway().getGateway_name());
            hostDescriptorResource.setContent(eHostDesc.getHost_descriptor_xml());
            end();
            return hostDescriptorResource;
        } else if (type == ResourceType.APPLICATION_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Application_Descriptor p WHERE p.application_descriptor_ID = :app_desc_id and p.gateway_name =:gate_name");
            q.setParameter("app_desc_id", name);
            q.setParameter("gate_name", gatewayName);
            Application_Descriptor eappDesc = (Application_Descriptor) q.getSingleResult();
            ApplicationDescriptorResource applicationDescriptorResource = new ApplicationDescriptorResource(eappDesc.getApplication_descriptor_ID());
            applicationDescriptorResource.setGatewayName(eappDesc.getGateway().getGateway_name());
            applicationDescriptorResource.setContent(eappDesc.getApplication_descriptor_xml());
            applicationDescriptorResource.setHostDescName(eappDesc.getHost_descriptor_ID());
            applicationDescriptorResource.setServiceDescName(eappDesc.getService_descriptor_ID());
            end();
            return applicationDescriptorResource;
        } else if(type == ResourceType.EXPERIMENT){
            Query q = em.createQuery("SELECT p FROM Experiment p WHERE p.experiment_ID = :ex_ID and p.gateway_name =:gate_name");
            q.setParameter("ex_ID", name);
            q.setParameter("gate_name", gatewayName);
            Experiment experiment = (Experiment)q.getSingleResult();
            ExperimentResource experimentResource = new ExperimentResource(experiment.getExperiment_ID());
            experimentResource.setWorker(new WorkerResource(experiment.getUser().getUser_name(),this));
            experimentResource.setGateway(this);
            experimentResource.setSubmittedDate(experiment.getSubmitted_date());
            return experimentResource;
        } else {
                return null;
        }
    }

    public List<Resource> getMe(Object[] keys) {
        throw new UnsupportedOperationException();
    }

    public List<Resource> get(ResourceType type) {
        List<Resource> resourceList = new ArrayList<Resource>();
        begin();
        if (type == ResourceType.PROJECT) {
            Query q = em.createQuery("SELECT p FROM Project p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Project project = (Project) result;
                    ProjectResource projectResource = new ProjectResource(project.getProject_ID());
                    projectResource.setGateway(this);
                    projectResource.setName(project.getProject_name());
                    resourceList.add(projectResource);
                }
            }
        }
        else if (type == ResourceType.USER) {
            Query q = em.createQuery("SELECT p FROM Users p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Users user = (Users) result;
                    UserResource userResource = new UserResource();
                    userResource.setGatewayName(gatewayName);
                    userResource.setUserName(user.getUser_name());
                    resourceList.add(userResource);
                }
            }
        }
        else if (type == ResourceType.PUBLISHED_WORKFLOW) {
            Query q = em.createQuery("SELECT p FROM Published_Workflow p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List<?> results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Published_Workflow publishedWorkflow = (Published_Workflow) result;
                    PublishWorkflowResource publishWorkflowResource = new PublishWorkflowResource(this);
                    publishWorkflowResource.setName(publishedWorkflow.getPublish_workflow_name());
                    publishWorkflowResource.setContent(publishedWorkflow.getWorkflow_content());
                    publishWorkflowResource.setPublishedDate(publishedWorkflow.getPublished_date());
                    publishWorkflowResource.setVersion(publishedWorkflow.getVersion());
                    resourceList.add(publishWorkflowResource);
                }
            }
        } else if (type == ResourceType.HOST_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Host_Descriptor p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Host_Descriptor hostDescriptor = (Host_Descriptor) result;
                    HostDescriptorResource hostDescriptorResource = new HostDescriptorResource(hostDescriptor.getHost_descriptor_ID());
                    hostDescriptorResource.setGatewayName(gatewayName);
                    hostDescriptorResource.setContent(hostDescriptor.getHost_descriptor_xml());
                    resourceList.add(hostDescriptorResource);
                }
            }
        } else if (type == ResourceType.SERVICE_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Service_Descriptor p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Service_Descriptor serviceDescriptor = (Service_Descriptor) result;
                    ServiceDescriptorResource serviceDescriptorResource = new ServiceDescriptorResource(serviceDescriptor.getService_descriptor_ID());
                    serviceDescriptorResource.setGatewayName(gatewayName);
                    serviceDescriptorResource.setContent(serviceDescriptor.getService_descriptor_xml());
                    resourceList.add(serviceDescriptorResource);
                }
            }
        } else if (type == ResourceType.APPLICATION_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Application_Descriptor p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Application_Descriptor applicationDescriptor = (Application_Descriptor) result;
                    ApplicationDescriptorResource applicationDescriptorResource = new ApplicationDescriptorResource(applicationDescriptor.getApplication_descriptor_ID(),applicationDescriptor.getGateway().getGateway_name(),
                            applicationDescriptor.getHost_descriptor_ID(), applicationDescriptor.getService_descriptor_ID());
                    applicationDescriptorResource.setContent(applicationDescriptor.getApplication_descriptor_xml());
                    applicationDescriptorResource.setUpdatedUser(applicationDescriptor.getUser().getUser_name());
                    resourceList.add(applicationDescriptorResource);
                }
            }
        } else if (type == ResourceType.EXPERIMENT) {
            Query q = em.createQuery("SELECT p FROM Experiment p WHERE p.gateway_name =:gate_name");
            q.setParameter("gate_name", gatewayName);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Experiment experiment = (Experiment) result;
                    ExperimentResource experimentResource = new ExperimentResource(experiment.getExperiment_ID());
                    experimentResource.setGateway(this);
                    experimentResource.setWorker(new WorkerResource(experiment.getUser().getUser_name(),this));
                    experimentResource.setSubmittedDate(experiment.getSubmitted_date());
                    ProjectResource project = new ProjectResource(experimentResource.getWorker(),this,experiment.getProject().getProject_ID());
                    project.setName(experiment.getProject().getProject_name());
					experimentResource.setProject(project);
                    resourceList.add(experimentResource);
                }
            }
        }
        end();
        return resourceList;

    }

    public void save() {
        // save me..
        begin();
        Gateway gateway = new Gateway();
        gateway.setGateway_name(gatewayName);
        em.persist(gateway);
        System.out.println(gateway);
        end();
    }

    public void save(boolean isAppendable) {
         throw new UnsupportedOperationException();
    }

    public boolean isExists(ResourceType type, Object name) {
        begin();
//        if (type == ResourceType.PROJECT) {
//            Query q = em.createQuery("SELECT p FROM Project p WHERE p.gateway_name =:gate_name and p.project_name =:proj_name");
//            q.setParameter("gate_name", gatewayName);
//            q.setParameter("proj_name", name);
//            Project project = (Project) q.getSingleResult();
//            return project != null;
//        } else

        if (type == ResourceType.USER) {
            Query q = em.createQuery("SELECT p FROM Gateway_Worker p WHERE p.gateway_name =:gate_name and p.user_name =:usr_name");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("usr_name", name);
            Users users = (Users) q.getSingleResult();
            return users != null;
        } else if (type == ResourceType.PUBLISHED_WORKFLOW) {
            Query q = em.createQuery("SELECT p FROM Published_Workflow p WHERE p.gateway_name =:gate_name and p.Published_Workflow =:pub_wf_name");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("pub_wf_name", name);
            Published_Workflow publishedWrkflow = (Published_Workflow) q.getSingleResult();
            return publishedWrkflow != null;
        } else if (type == ResourceType.HOST_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Host_Descriptor p WHERE p.gateway_name =:gate_name and p.host_descriptor_ID =:host_desc_name");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("host_desc_name", name);
            Host_Descriptor hostDescriptor = (Host_Descriptor) q.getSingleResult();
            return hostDescriptor != null;
        } else if (type == ResourceType.SERVICE_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Service_Descriptor p WHERE p.gateway_name =:gate_name and p.service_descriptor_ID =:service_desc_name");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("service_desc_name", name);
            Service_Descriptor serviceDescriptor = (Service_Descriptor) q.getSingleResult();
            return serviceDescriptor != null;
        } else if (type == ResourceType.APPLICATION_DESCRIPTOR) {
            Query q = em.createQuery("SELECT p FROM Application_Descriptor p WHERE p.gateway_name =:gate_name and p.application_descriptor_ID =:app_desc_name");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("app_desc_name", name);
            Application_Descriptor applicationDescriptor = (Application_Descriptor) q.getSingleResult();
            return applicationDescriptor != null;
        } else if (type == ResourceType.EXPERIMENT) {
            Query q = em.createQuery("SELECT p FROM Experiment p WHERE p.gateway_name =:gate_name and p.experiment_ID =:ex_ID");
            q.setParameter("gate_name", gatewayName);
            q.setParameter("ex_ID", name);
            Experiment experiment = (Experiment) q.getSingleResult();
            return experiment != null;
        }
        end();
        return false;
    }

    public boolean isHostDescriptorExists(String descriptorName){
    	return isExists(ResourceType.HOST_DESCRIPTOR, descriptorName);
    }
    
    public HostDescriptorResource createHostDescriptorResource(String hostDescriptorName){
    	HostDescriptorResource hdr = (HostDescriptorResource)create(ResourceType.HOST_DESCRIPTOR);
    	hdr.setHostDescName(hostDescriptorName);
    	return hdr;
    }
    
    public HostDescriptorResource getHostDescriptorResource(String hostDescriptorName){
    	return (HostDescriptorResource)get(ResourceType.HOST_DESCRIPTOR,hostDescriptorName);
    }
    
    public void removeHostDescriptor(String descriptorName){
    	remove(ResourceType.HOST_DESCRIPTOR, descriptorName);
    }
    
    public List<HostDescriptorResource> getHostDescriptorResources(){
    	List<HostDescriptorResource> results=new ArrayList<HostDescriptorResource>();
    	List<Resource> list = get(ResourceType.HOST_DESCRIPTOR);
    	for (Resource resource : list) {
    		results.add((HostDescriptorResource) resource);
		}
    	return results;
    }
    
    public boolean isServiceDescriptorExists(String descriptorName){
    	return isExists(ResourceType.SERVICE_DESCRIPTOR, descriptorName);
    }
    
    public ServiceDescriptorResource createServiceDescriptorResource(String descriptorName){
    	ServiceDescriptorResource hdr = (ServiceDescriptorResource)create(ResourceType.SERVICE_DESCRIPTOR);
    	hdr.setServiceDescName(descriptorName);
    	return hdr;
    }
    
    public ServiceDescriptorResource getServiceDescriptorResource(String descriptorName){
    	return (ServiceDescriptorResource)get(ResourceType.SERVICE_DESCRIPTOR,descriptorName);
    }
    
    public void removeServiceDescriptor(String descriptorName){
    	remove(ResourceType.SERVICE_DESCRIPTOR, descriptorName);
    }

    public List<ServiceDescriptorResource> getServiceDescriptorResources(){
    	List<ServiceDescriptorResource> results=new ArrayList<ServiceDescriptorResource>();
    	List<Resource> list = get(ResourceType.SERVICE_DESCRIPTOR);
    	for (Resource resource : list) {
    		results.add((ServiceDescriptorResource) resource);
		}
    	return results;
    }
    
    public boolean isApplicationDescriptorExists(String descriptorName){
    	return isExists(ResourceType.APPLICATION_DESCRIPTOR, descriptorName);
    }
    
    public ApplicationDescriptorResource createApplicationDescriptorResource(String descriptorName){
    	ApplicationDescriptorResource hdr = (ApplicationDescriptorResource)create(ResourceType.APPLICATION_DESCRIPTOR);
    	hdr.setName(descriptorName);
    	return hdr;
    }
    
    public ApplicationDescriptorResource getApplicationDescriptorResource(String descriptorName){
    	return (ApplicationDescriptorResource)get(ResourceType.APPLICATION_DESCRIPTOR,descriptorName);
    }
    
    public void removeApplicationDescriptor(String descriptorName){
    	remove(ResourceType.APPLICATION_DESCRIPTOR, descriptorName);
    }

    public List<ApplicationDescriptorResource> getApplicationDescriptorResources(){
    	List<ApplicationDescriptorResource> results=new ArrayList<ApplicationDescriptorResource>();
    	List<Resource> list = get(ResourceType.APPLICATION_DESCRIPTOR);
    	for (Resource resource : list) {
    		results.add((ApplicationDescriptorResource) resource);
		}
    	return results;
    }
    
    public List<ApplicationDescriptorResource> getApplicationDescriptorResources(String serviceName,String hostName){
    	List<ApplicationDescriptorResource> resourceList = new ArrayList<ApplicationDescriptorResource>();
        begin();
        String qString = "SELECT p FROM Application_Descriptor p WHERE p.gateway_name =:gate_name and p.service_descriptor_ID =:service_name";
        if (hostName!=null){
        	qString+=" and p.host_descriptor_ID =:host_name";
        }
		Query q = em.createQuery(qString);
        q.setParameter("gate_name", gatewayName);
        q.setParameter("service_name", serviceName);
        if (hostName!=null){
        	q.setParameter("host_name",hostName);
        }
        List<?> results = q.getResultList();
        if (results.size() != 0) {
            for (Object result : results) {
                Application_Descriptor applicationDescriptor = (Application_Descriptor) result;
                ApplicationDescriptorResource applicationDescriptorResource = new ApplicationDescriptorResource(applicationDescriptor.getApplication_descriptor_ID(),applicationDescriptor.getGateway().getGateway_name(),
                        applicationDescriptor.getHost_descriptor_ID(), applicationDescriptor.getService_descriptor_ID());
                applicationDescriptorResource.setContent(applicationDescriptor.getApplication_descriptor_xml());
                applicationDescriptorResource.setUpdatedUser(applicationDescriptor.getUser().getUser_name());
                resourceList.add(applicationDescriptorResource);
            }
        }
        end();
        return resourceList;
    }
    
    public boolean isPublishedWorkflowExists(String workflowTemplateName){
    	return isExists(ResourceType.PUBLISHED_WORKFLOW, workflowTemplateName);
    }
    
    public PublishWorkflowResource createPublishedWorkflow(String workflowTemplateName){
    	PublishWorkflowResource publishedWorkflowResource = (PublishWorkflowResource)create(ResourceType.PUBLISHED_WORKFLOW);
    	publishedWorkflowResource.setName(workflowTemplateName);
    	publishedWorkflowResource.setPath("/");
    	publishedWorkflowResource.setVersion("1.0");
    	return publishedWorkflowResource;
    }
    
    public PublishWorkflowResource getPublishedWorkflow(String workflowTemplateName){
    	return (PublishWorkflowResource)get(ResourceType.PUBLISHED_WORKFLOW,workflowTemplateName);
    }
    
    public List<PublishWorkflowResource> getPublishedWorkflows(){
    	List<PublishWorkflowResource> result=new ArrayList<PublishWorkflowResource>();
    	List<Resource> list = get(ResourceType.PUBLISHED_WORKFLOW);
    	for (Resource resource : list) {
			result.add((PublishWorkflowResource) resource);
		}
    	return result;
    }
    
    public void removePublishedWorkflow(String workflowTemplateName){
    	remove(ResourceType.PUBLISHED_WORKFLOW, workflowTemplateName);
    }
}
