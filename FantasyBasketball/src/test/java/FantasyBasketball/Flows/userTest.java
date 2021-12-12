package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.userService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class userTest {

    @Autowired
    private userService userService;

    @MockBean
    private userRepository userRepo;

    // Test that the id is correctly updated by postUser method
    @Test
    public void testPostUser() {

        // Initialize user before postUser called
        User beforeUser = new User(null,
                1,
                "patip@gmail.com",
                "pap2154",
                "Pati",
                "Przewoznik");

        // Create newly inserted User
        User afterUser = new User(21,
                1,
                "patip@gmail.com",
                "pap2154",
                "Pati",
                "Przewoznik");

        // save the user
        Mockito.when(userRepo.save(beforeUser)).thenReturn(afterUser);

        //assert that the user_id gets correctly updated
        assertEquals(userService.postUser(beforeUser).get(0).getUserID(), 21);
    }

    //Test that user gets correctly updated
    @Test
    public void testUpdateUser() throws resourceNotFoundException {

        // Initialize updated user
        User updatedUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuelito",
                "Daktor");

        // user exists
        Mockito.when(userRepo.existsById(2)).thenReturn(true);

        // save the changes
        Mockito.when(userRepo.save(updatedUser)).thenReturn(updatedUser);

        // assert that user gets correctly updated by checking that all data members are equal to the updatedUser
        assertEquals(userService.updateUser(updatedUser).get(0).getUserID(), updatedUser.getUserID());
        assertEquals(userService.updateUser(updatedUser).get(0).getClientID(), updatedUser.getClientID());
        assertEquals(userService.updateUser(updatedUser).get(0).getEmail(), updatedUser.getEmail());
        assertEquals(userService.updateUser(updatedUser).get(0).getUsername(), updatedUser.getUsername());
        assertEquals(userService.updateUser(updatedUser).get(0).getFirstName(), updatedUser.getFirstName());
        assertEquals(userService.updateUser(updatedUser).get(0).getLastName(), updatedUser.getLastName());
    }

    //Make sure exception raised when the user does not exist
    @Test(expected = resourceNotFoundException.class)
    public void testExceptionUpdateUser() throws resourceNotFoundException {

        // Initialize updated user
        User updatedUser = new User(10,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuelito",
                "Daktor");

        // userID does not exist
        Mockito.when(userRepo.existsById(10)).thenReturn(false);

        //call updateUser method
        userService.updateUser(updatedUser);
    }

    // Make sure that delete raises exception when user not found
    @Test(expected = resourceNotFoundException.class)
    public void testExceptionDeleteUserById() throws resourceNotFoundException {

        // userID does not exist
        Mockito.when(userRepo.existsById(2)).thenReturn(false);

        //call deleteUserById method
        userService.deleteUserById(2);
    }

    @Test
    public void testIfInvalidEmail() {

        // Initialize a string containing > 128 chars
        String test = "abcdefghijklmnopqrstuvwxyz" +
                "abcdefghijklmnopqrstuvwxyz" +
                "abcdefghijklmnopqrstuvwxyz" +
                "abcdefghijklmnopqrstuvwxyz" +
                "abcdefghijklmnopqrstuvwxyz";

        // assert that checkIfInvalid returns true
        assertTrue(userService.checkIfInvalid(test));


        //  Change the string to within the 128 limit but containing only blanks
        test = "   ";

        // assert that checkIfInvalid returns true
        assertTrue(userService.checkIfInvalid(test));


        // Change the String to a valid string
        test = "This is a valid string.";

        //assert that checkIfInvalid returns false
        assertFalse(userService.checkIfInvalid(test));

    }

    // Test that exception is raised when email is invalid
    @Test(expected = resourceException.class)
    public void testCheckInputsEmail1() throws resourceException {

        // Initialize test user with invalid email
        User testUser = new User(2,
                1,
                "emanueldgmail.com",
                "edaka",
                "Emanuelito",
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when email is null
    @Test(expected = resourceException.class)
    public void testCheckInputsEmail2() throws resourceException {

        // Initialize test user with invalid email
        User testUser = new User(2,
                1,
                "emanueldemanueldemanueldemanueldemanueldemanueldemanueld" +
                        "emanueldemanueldemanueldemanueldemanueldemanueldemanueld" +
                        "emanueldemanueldemanueldemanueldemanueldemanueld" +
                        "emanueldemanueldemanueldemanueldemanueldemanueld@gmail.com",
                "edaka",
                "Emanuelito",
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when username is invalid
    @Test(expected = resourceException.class)
    public void testCheckInputsUsername1() throws resourceException {

        // Initialize test user with invalid username
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "     ",
                "Emanuelito",
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when username is null
    @Test(expected = resourceException.class)
    public void testCheckInputsUsername2() throws resourceException {

        // Initialize test user with invalid username
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                null,
                "Emanuelito",
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when first_name is invalid
    @Test(expected = resourceException.class)
    public void testCheckInputsFirstName1() throws resourceException {

        // Initialize test user with invalid first name
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                "     ",
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when first_name is null
    @Test(expected = resourceException.class)
    public void testCheckInputsFirstName2() throws resourceException {

        // Initialize test user with invalid first name
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                null,
                "Daktor");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when first_name is invalid
    @Test(expected = resourceException.class)
    public void testCheckInputsLastName1() throws resourceException {

        // Initialize test user with invalid last name
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                "     ");

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when first_name is null
    @Test(expected = resourceException.class)
    public void testCheckInputsLastName2() throws resourceException {

        // Initialize test user with invalid last name
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                null);

        userService.checkInputs(testUser);
    }

    // Test that exception is raised when user_id provided that is not null
    @Test(expected = resourceException.class)
    public void testCheckPostInputs() throws resourceException {

        // Initialize test user with user_id that is not null
        User testUser = new User(2,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");

        userService.checkPostInputs(testUser);
    }

    // Test that exception is raised when user_id provided that is not null
    @Test
    public void testCheckPostInputs2() throws resourceException {

        // Initialize test user with user_id that is not null
        User testUser = new User(null,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");

        userService.checkPostInputs(testUser);
    }

    // Test that exception is raised when user_id not provided
    @Test (expected = resourceException.class)
    public void testCheckPutInputs2() throws resourceException {
        // Initialize test user with user_id that is null
        User testUser = new User(null,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");

        userService.checkPutInputs(testUser);
    }

    // Test that exception is raised when user_id not provided
    @Test
    public void testCheckPutInputs() throws resourceException {
        // Initialize test user with user_id that is null
        User testUser = new User(1,
                1,
                "emanueld@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");

        userService.checkPutInputs(testUser);
    }

    @Test
    public void testCheckGetByIdExists() throws resourceNotFoundException {

        Integer user_id = 1;
        User newUser = new User(1,
                1,
                "emanueldaka@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");
        Optional<User> optUser = Optional.of(newUser);
        Mockito.when(userRepo.findById(user_id)).thenReturn(optUser);

        User result = userService.getByID(user_id).get(0);

        assertEquals(user_id, result.getClientID());
    }

    @Test(expected = resourceNotFoundException.class)
    public void testCheckGetByIdNotExists() throws resourceNotFoundException {

        Integer client_id = 1;
        Optional<User> optUser = Optional.empty();
        Mockito.when(userRepo.findById(client_id)).thenReturn(optUser);
    }

    @Test
    public void testGetUsersByTemplate() {

        User newUser = new User(1,
                1,
                "emanueldaka@gmail.com",
                "edaka",
                "Emanuel",
                "Daka");

        Mockito.when(userRepo.findByTemplate(null,
                null,
                "emanueldaka@gmail.com",
                null,
                null,
                null)).thenReturn(List.of(newUser));
        User result = userService.getUsersByTemplate(null,
                null,
                "emanueldaka@gmail.com",
                null,
                null,
                null).get(0);
        assertEquals(result.getUserID(), result.getUserID());

    }

}
