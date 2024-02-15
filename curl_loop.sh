#!/bin/sh
while true; \
    do curl http://istio-ingressgateway-istio-system.apps.cluster-tgjck.tgjck.sandbox2112.opentlc.com/service-a/hello; echo ""; \
sleep 2;done