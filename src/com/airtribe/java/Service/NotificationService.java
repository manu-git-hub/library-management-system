package com.airtribe.java.Service;

import java.util.logging.Logger;

public class NotificationService {

    private static final Logger logger =
            Logger.getLogger(NotificationService.class.getName());

    public void notifyUser(String patronId, String message) {
        logger.info("Notification → Patron: " + patronId + " | Message: " + message);
    }
}