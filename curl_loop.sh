#!/bin/sh
while true; \
    do curl http://istio-ingressgateway-istio-system.apps.ocp4-bm.redhat.arrowlabs.be/service-a/hello; echo ""; \
sleep 2;done