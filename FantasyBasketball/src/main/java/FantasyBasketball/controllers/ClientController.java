package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    private static final Logger Log = LoggerFactory.getLogger(FantasyGameController.class);

    private final HttpServletRequest request;

    // default constructor for FantasyGameController
    @Autowired
    public ClientController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/getClient", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@RequestParam(value = "google_id") String google_id) {
        Log.info("Requesting client info with google_id {}", google_id);

        try {
            List<Client> result = clientService.getClientByGoogleId(google_id);
            Log.info("Client request successful");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            Log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> postClient(@RequestBody Client newClient) {

        try {

            Log.info("POST: " + request.getRequestURL());
            Log.info(newClient.toString());

            // Checks to make sure the input is valid to insert in DB
            clientService.checkPostInputs(newClient);

            // Regular post
            List<Client> result = clientService.postClient(newClient);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(ResourceException e) {
            // exception thrown if User instance is not formatted correctly
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on POST: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            Log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/customize", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        try {

            Log.info("PUT: " + request.getRequestURL());
            Log.info(client.toString());

            List<Client> oldClientList = clientService.getClientByGoogleId(client.getGoogle_id());

            Client oldClient = oldClientList.get(0);
            Integer client_id = oldClient.getClientID();

            client.setClientID(client_id);

//            client.setEmail(oldClient.get(0).getEmail());
//            client.setCompany_name(oldClient.get(0).getCompany_name());
//            client.setClient_name(oldClient.get(0).getClient_name());

            // Checks to make sure the input is valid to insert in DB
            clientService.checkPutInputs(client);

            // Regular put
            List<Client> result = clientService.updateClient(client);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceException e) {
            // exception thrown if User instance is not formatted correctly
            Log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ResourceNotFoundException e) {
            // If user not found in the database, throw exception not found
            Log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            Log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/terminate_account", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@RequestParam(value = "google_id") String google_id) {
        try {

            Log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());
            List<Client> clientList = clientService.getClientByGoogleId(google_id);
            Integer client_id = clientList.get(0).getClientID();
            clientService.deleteClientById(client_id);
            return new ResponseEntity<>(clientList, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            Log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
