import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.UserGenerator.getRandomUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

public class UserLoginTest {

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
    @DisplayName("Check that a courier can login with valid password and email")
    public void checkUserCanBeLogin() {
        ValidatableResponse response = userClient.login(user);
        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        assertThat("Status code is not 200", statusCode, equalTo(200));
        assertThat("User is not login0", isUserLogin, equalTo(true));
        assertThat("AccessToken is null", accessToken, is(notNullValue()));
    }

    @Test
    @DisplayName("Check that a courier can not login with incorrect password")
    public void checkUserLoginWithIncorrectPassword() {
        user = user.setPassword(UserGenerator.getUserPassword());
        ValidatableResponse response = userClient.login(user);
        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        assertThat("Status code is not 401", statusCode, equalTo(401));
        assertThat("User is login", isUserLogin, equalTo(false));
    }

    @Test
    @DisplayName("Check that a courier can not login with incorrect email")
    public void checkUserLoginWithIncorrectEmail() {
        user = user.setEmail(UserGenerator.getUserEmail());
        ValidatableResponse response = userClient.login(user);
        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        assertThat("Status code is not 401", statusCode, equalTo(401));
        assertThat("User is login", isUserLogin, equalTo(false));
    }
}
