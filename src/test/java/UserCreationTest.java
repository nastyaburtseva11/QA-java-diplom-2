import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.UserGenerator.getRandomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserCreationTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Check that a user can be created")
    public void checkUserCanBeCreated() {
        user = getRandomUser();
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");
        boolean isUserCreated = response.extract().path("success");
        assertThat("Status code is not 200", statusCode, equalTo(200));
        assertTrue("Courier is not created", isUserCreated);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check that a exist user can not be created")
    public void checkRegisteredUserCanNotBeCreated() {
        user = getRandomUser();
        userClient.create(user);
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        String message = response.extract().path("message");
        assertThat("Status code is not 403", statusCode, equalTo(403));
        assertFalse("User is created", isUserCreated);
        assertThat("Incorrect message", message, equalTo("User already exists"));
    }
}
