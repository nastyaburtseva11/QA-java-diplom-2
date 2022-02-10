import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.OrderGenerator;
import model.User;
import model.UserGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderCreateTest {
    private OrderClient orderClient;
    private Order order;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check that an order cannot be created by an unauthorized user")
    public void checkOrderWithoutAuthCannotBeCreated() {
        order = OrderGenerator.generateOrder();
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreated = response.extract().path("success");
        assertThat("Status code is not 400", statusCode, equalTo(400));
        assertThat("An order has been created", isOrderCreated, equalTo(false));

    }

    @Test
    @DisplayName("Check that an order can be created by an authorized user")
    public void checkOrderWithAuthCanBeCreated() {
        UserClient userClient = new UserClient();
        User user = UserGenerator.getRandomUser();
        String accessToken = userClient.create(user).extract().path("accessToken");
        accessToken = accessToken.substring(7);
        order = OrderGenerator.generateOrder();
        ValidatableResponse response = orderClient.createOrder(order, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreated = response.extract().path("success");
        int orderNumber = response.extract().path("order.number");
        userClient.delete(accessToken);
        assertThat("Order number is 0", orderNumber, is(not(0)));
        assertThat("An order has not been created", isOrderCreated, equalTo(true));
        assertThat("Status code is not 200", statusCode, equalTo(200));
    }

    @Test
    @DisplayName("Check that an order cannot be created without ingredients")
    public void checkOrderWithoutIngredientsCannotBeCreated() {
        order = new Order();
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        boolean isOrderCreated = response.extract().path("success");
        assertThat("Status code is not 400", statusCode, equalTo(400));
        assertThat("An order has been created", isOrderCreated, equalTo(false));
        assertThat("Ingredient ids are not provided", message, equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Check that an order cannot be created with incorrect ingredients' ids")
    public void checkOrderWithIncorrectIngredientsCannotBeCreated() {
        List<String> ids = new ArrayList<>();
        ids.add("wrong61c0c5a71d1f82001bdaaa73");
        ids.add("");
        order = new Order(ids);
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        assertThat("Status code is not 500", statusCode, equalTo(500));
    }
}