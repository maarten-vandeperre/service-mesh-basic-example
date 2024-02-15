#!/bin/sh
while true; \
    do curl http://istio-ingressgateway-istio-system-maarten.apps.cluster-rhwr6.dynamic.redhatworkshops.io/service-a/service-a/hello; echo ""; \
sleep 2;done