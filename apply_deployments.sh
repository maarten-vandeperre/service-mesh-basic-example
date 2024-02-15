#!/bin/sh
oc apply -f services/service-a/metadata/deployment_config.yaml
oc apply -f services/service-a/metadata/8080_service_config.yaml
oc apply -f services/service-b/metadata/deployment_config.yaml
oc apply -f services/service-b/metadata/8080_service_config.yaml
oc apply -f services/service-c/metadata/deployment_config.yaml
oc apply -f services/service-c/metadata/8080_service_config.yaml
