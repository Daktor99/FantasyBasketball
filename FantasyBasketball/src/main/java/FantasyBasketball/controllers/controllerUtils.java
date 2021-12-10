package FantasyBasketball.controllers;

import FantasyBasketball.models.Client;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class controllerUtils {

    public static Integer getClientId(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");
        return client.getClientID();
    }

    public static void logGetRequest(HttpServletRequest request, Logger log) {

        if (request.getQueryString() != null) {
            log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
        } else {
            log.info("GET: " + request.getRequestURL());
        }

    }

    public static void logPostRequest(HttpServletRequest request, Logger log, String objectString) {

        log.info("POST: " + request.getRequestURL());
        log.info(objectString);

    }

    public static void logPutRequest(HttpServletRequest request, Logger log, String objectString) {

        log.info("PUT: " + request.getRequestURL());
        log.info(objectString);

    }

    public static void logDeleteRequest(HttpServletRequest request, Logger log) {

        log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());

    }
}