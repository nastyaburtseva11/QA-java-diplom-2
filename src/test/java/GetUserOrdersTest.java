import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetUserOrdersTest {

        private OrderClient orderClient;
        private UserClient userClient;
        private String accessToken;

        @Before
        public void setUp(){
            orderClient = new OrderClient();
        }
        @Test
        @DisplayName("Check that an authorized user can get its orders")
        public void checkAuthUserCanGetOrders(){
            userClient = new UserClient();
            User user = UserGenerator.getRandomUser();
            accessToken = userClient.create(user).extract().path("accessToken");
            accessToken = accessToken.substring(7);
            ValidatableResponse response = orderClient.gerOrder(accessToken);
            int statusCode = response.extract().statusCode();
            assertThat("Status code is not 200", statusCode, equalTo(200));
            boolean hasGotTheOrders = response.extract().path("success");
            assertTrue("Authorized user has not got the orders", hasGotTheOrders);
            userClient.delete(accessToken);
        }
        @Test
        @DisplayName("Check that an unauthorized user cannot get its orders")
        public void checkNotAuthUserCannotGetOrders(){
            ValidatableResponse response = orderClient.gerOrder("");
            int statusCode = response.extract().statusCode();
            assertThat("Status code is not 401", statusCode, equalTo(401));
            boolean hasGotTheOrders = response.extract().path("success");
            assertFalse("Unauthorized user has got the orders", hasGotTheOrders);
            String message = response.extract().path("message");
            assertThat("Error message is incorrect", message, equalTo("You should be authorised"));
        }
    }
