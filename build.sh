#!/bin/bash
echo "Building Keycloak Quota Event Listener..."
mvn clean package
if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful!"
    echo "JAR file: target/keycloak-quota-event-listener-1.0.0.jar"
    echo ""
    echo "Next steps:"
    echo "1. Copy the JAR to Keycloak: docker cp target/keycloak-quota-event-listener-1.0.0.jar <container>:/opt/keycloak/providers/"
    echo "2. Restart Keycloak: docker restart <container>"
    echo "3. Enable in Admin Console: Realm Settings → Events → Event Listeners"
else
    echo "Build failed!"
    exit 1
fi
