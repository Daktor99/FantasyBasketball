package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.models.User;
import FantasyBasketball.services.clientService;
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
public class clientController {

    @Autowired
    clientService clientService;

    private static final Logger log = LoggerFactory.getLogger(fantasyGameController.class);

    private final HttpServletRequest request;

    // default constructor for fantasyGameController
    @Autowired
    public clientController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> postClient(@RequestBody Client newClient) {

        try {

            log.info("POST: " + request.getRequestURL());
            log.info(newClient.toString());

            // Checks to make sure the input is valid to insert in DB
            clientService.checkPostInputs(newClient);

            // Regular post
            List<Client> result = clientService.postClient(newClient);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/customize", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(client.toString());

            Integer client_id = client.getClientID();
            List<Client> oldClient = clientService.getByID(client_id);

            client.setEmail(oldClient.get(0).getEmail());
            client.setCompany_name(oldClient.get(0).getCompany_name());
            client.setClient_name(oldClient.get(0).getClient_name());

            // Checks to make sure the input is valid to insert in DB
            clientService.checkPutInputs(client);

            // Regular put
            List<Client> result = clientService.updateClient(client);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If user not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/terminate_account", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@RequestParam(value = "client_id", required = true) Integer client_id) {
        try {

            log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());

            clientService.deleteClientById(client_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (resourceNotFoundException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}