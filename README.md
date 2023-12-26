# Getting Started 
## Overview
The above samples shows basic implenmentation of ISO8583 in with Camel as routing engine, deployed on top of Kubernetes cluster.

Component Highlights
- J8583 as custom data format 
- Camel-netty Open incoming TCP connection  
- KIND - Kubernetes in Docker
- Nginx - help with L4 traffic routing  

Tools 
- Skaffold
- KIND 


## Provisioning Kubernetes Cluster in KIND 
### Cluster Provisioning
- Run the following command to start KIND. 
```
make cluster.build 
make cluster.up 
```

You should see the following screen after startup.
![cluster-startup](/assets/images/cluster-startup.png)

#
- Validate the port forwarding by typing the following command 
```
docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Status}}"
```
![cluster-status](/assets/images/cluster-status.png)
Do Ensure port 9200 is available on host network.

##
- Deploy NGINX and ensure the pod is up and running as shown on the image below
```
make cluster.deploy.nginx
```
![nginx-ready](/assets/images/nginx-ready.png)

Do ensure NGINX service is mapped from 9200 -> 32500 
![nginx-service](/assets/images/nginx-service.png)

### Application Deployment
- Deploy the application with the following command
```
skaffold dev 
```
Expecterd output 
- Namespace 
![app-namespace](/assets/images/app-namespace.png)


- Pod 
![app-pod](/assets/images/app-pod.png)


- Service
![app-service](/assets/images/app-service.png)


### Send sample request 

```
telnet localhost 9200

## Sample request 
ISO0250000550210B23A80012EA080180000000014000004650000000000003000042813060446877413010304280428070903123173766123456123456=00123442579414474500637107053300TESTSOLAB                 TEST-3       DF MX484012B123PRO1+000013        0000P0312304ABCD040ABCD6421234099                          

## Sample response
ISO0150000........ 
```






