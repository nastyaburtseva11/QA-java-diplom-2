import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UserCreateValidationTest {

    private final String email;
    private final String password;
    private final String name;

    public UserCreateValidationTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserGenerator.getUserEmail(), UserGenerator.getUserPassword(), null},
                {UserGenerator.getUserEmail(), null, UserGenerator.getUserName()},
                {null, UserGenerator.getUserPassword(), UserGenerator.getUserName()}
        };
    }

    @Test
    @DisplayName("Check that a courier without login/password/name can not be created")
    public void checkInvalidRequestIsNotAllowed() {

        User user = new User(email, password, name);
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        boolean isUserCreated = response.extract().path("success");
        assertThat("Status Code is not 403", statusCode, equalTo(403));
        assertThat("User is created", isUserCreated, equalTo(false));
        assertThat("Error message is incorrect", errorMessage, equalTo("Email, password and name are required fields"));
    }
}
