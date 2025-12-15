package com.example.keycloak.eventlistener;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

/**
 * Event Listener that sets default quota attribute for new users
 */
public class QuotaDefaultEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;

    public QuotaDefaultEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        // Only process REGISTER events
        if (event.getType() == EventType.REGISTER) {
            RealmModel realm = session.realms().getRealm(event.getRealmId());
            UserModel user = session.users().getUserById(realm, event.getUserId());
            
            if (user != null) {
                // Check if quota attribute is missing or empty
                String quota = user.getFirstAttribute("quota");
                if (quota == null || quota.isEmpty()) {
                    // Set default quota value
                    user.setSingleAttribute("quota", "5 GB");
                    System.out.println("Set default quota for user: " + user.getUsername());
                }
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // Not needed for this use case
    }

    @Override
    public void close() {
        // No cleanup needed
    }
}
