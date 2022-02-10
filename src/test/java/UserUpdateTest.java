import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.UserGenerator.getRandomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = getRandomUser();
        userClient = new UserClient();
        accessToken = userClient.create(user).extract().path("accessToken");
        accessToken = accessToken.substring(7);
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check that a password can update")
    public void checkUpdatePassword() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        assertThat("Status code is not 200", statusCode, equalTo(200));
        assertThat("Password not updated", isUserLogin, equalTo(true));
    }

    @Test
    @DisplayName("Check that a email can update")
    public void checkUpdateEmail() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isUserUpdate = response.extract().path("success");
        String actualEmail = response.extract().path("user.email");
        String expectedEmail = user.getEmail().toLowerCase();
        assertThat("Status code is not 200", statusCode, equalTo(200));
        assertThat("User is not update", isUserUpdate, equalTo(true));
        assertThat("Email not updated", actualEmail, equalTo(expectedEmail));
    }

    @Test
    @DisplayName("Check that a name can update")
    public void checkUpdateName() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isUserUpdate = response.extract().path("success");
        String actualName = response.extract().path("user.name");
        String expectedName = user.getName();
        assertThat("Status code is not 200", statusCode, equalTo(200));
        assertThat("User is not update", isUserUpdate, equalTo(true));
        assertThat("Email not updated", actualName, equalTo(expectedName));
    }

    @Test
    @DisplayName("Check that a password can not update without token")
    public void checkUpdatePasswordWithOutToken() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertThat("Status code is not 401", statusCode, equalTo(401));
        assertThat("Incorrect message", message, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check that a email can not update without token")
    public void checkUpdateEmailWithOutToken() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertThat("Status code is not 401", statusCode, equalTo(401));
        assertThat("Incorrect message", message, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check that a name can not update without token")
    public void checkUpdateNameWithOutToken() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.userUpdate(user);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertThat("Status code is not 401", statusCode, equalTo(401));
        assertThat("Incorrect message", message, equalTo("You should be authorised"));
    }
}

