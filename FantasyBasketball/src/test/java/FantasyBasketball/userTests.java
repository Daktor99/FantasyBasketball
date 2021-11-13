package FantasyBasketball;

import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.userService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class userTests {

    @Autowired
    private userService userService;

    @MockBean
    private userRepository userRepo;

    @Before
    public void setUp() {
        //tearDown function
    }

    @After
    public void tearDown() {
        //tearDown function
    }

    @Test
    public void testGetUsersByTemplate() {

    }

    @Test
    public void testPostUser() {

    }

    @Test
    public void testUpdateUser() {

    }

    @Test
    public void testDeleteUserById() {

    }

    @Test
    public void testIdValidEmail() {

    }

    @Test
    public void testCheckPostInputs() {

    }

    @Test
    public void testCheckPutInputs() {

    }

    @Test
    public void testCheckInputs() {

    }
}
