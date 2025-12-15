# Keycloak Quota Event Listener

This is a custom Keycloak Event Listener that automatically sets a default quota attribute for newly registered users.

Note: This listener exists as a workaround for the Keycloak bug where custom attributes with default values block SSO logins (`VERIFY_PROFILE`) when users cannot edit the attribute. See https://github.com/keycloak/keycloak/issues/44785 for details.

## Building

```bash
mvn clean package
```

The JAR file will be created in `target/keycloak-quota-event-listener-1.0.0.jar`

### Using Docker

If you don't have Maven installed locally, you can build the JAR using Docker:

```bash
# Build the Docker image that performs the Maven build
docker build -t keycloak-quota-event-listener-builder .

# Run the build inside the container, writing the artifact to ./target on the host
docker run --rm -v "$(pwd)/target:/usr/src/app/target" keycloak-quota-event-listener-builder
```

After this, the JAR will be available on your host at `target/keycloak-quota-event-listener-1.0.0.jar`.

## Deployment

### Docker
```bash
docker cp target/keycloak-quota-event-listener-1.0.0.jar <container-name>:/opt/keycloak/providers/
docker restart <container-name>
```

### Standalone
```bash
cp target/keycloak-quota-event-listener-1.0.0.jar /opt/keycloak/providers/
systemctl restart keycloak
```

## Configuration

1. Log in to Keycloak Admin Console
2. Select your realm
3. Go to **Realm Settings** → **Events** → **Event Listeners** tab
4. Add `quota-default-event-listener` to the Event Listeners
5. Click **Save**

## Testing

Register a new user and verify that the `quota` attribute is automatically set to `5 GB`.

## Customization

To change the default quota value, edit `QuotaDefaultEventListenerProvider.java`:

```java
user.setSingleAttribute("quota", "10 GB");  // Change to your desired value
```

Then rebuild and redeploy.
