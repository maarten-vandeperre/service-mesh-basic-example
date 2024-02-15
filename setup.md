# Setup
## Install the operators
With default settings
* Red Hat OpenShift distributed tracing platform - in custom namespace, required by operator
* Elasticsearch (ECK) Operator - in OpenShift operators namespace
* Kiali Operator - in OpenShift operators namespace

Wait for the 3 operators to be   

* Red Hat OpenShift Service Mesh  - in OpenShift operators namespace (can take some minutes)

## Configure the service mesh
* Create a namespace for the config and control plane (e.g., istio-system). I opt for istio-system-maarten.
* Create a namespace for the deployments. I opt for app-playground.
* ```shell
    oc login
    ```
* ```shell
    sh apply_deployments.sh
    ```
* Create a route for service a and test it on /service-a/hello
* Create a control plane with default configuration and wait until it's ready.
* Create a service mesh member role with member the deployment namespace (in my example: app-playground). 
This should trigger the creation of a service mesh member.
* Create an ingress gateway to allow ingress traffic to the mesh.
    ```shell
    oc apply -f services/service-a/metadata/sm_1_ingress_gateway.yaml 
    ```
* Create a virtual service to redirect the ingress traffic to the Vert.x application.
    ```shell
    oc apply -f services/service-a/metadata/sm_1_virtual_service.yaml 
    ```
* If the ingress, virtual service and gateway configuration works, you should be able to test it on /service-a/service-a/hello

## Description

### Control plane
The control plane manages the configuration and policies for the service mesh. 
The OpenShift Service Mesh Operator installation makes the operator available in all namespaces, 
so you can install the control plane in any project. Red Hat recommends to deploy the control plane in a separate project.

## TODO
* Jaeger, Prometheus demo