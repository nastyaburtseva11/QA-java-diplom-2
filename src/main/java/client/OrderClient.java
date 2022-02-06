package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.User;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient{

    private static final String USER_PATH = "/api/orders";

    @Step("Send POST request to " + USER_PATH)
    public ValidatableResponse createOrder (Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(USER_PATH)
                .then();
    }

    @Step("Send POST request to " + USER_PATH)
    public ValidatableResponse createOrder (Order order, String accessToken){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(order)
                .when()
                .post(USER_PATH)
                .then();
    }

    @Step("Send GET request to " + USER_PATH)
    public ValidatableResponse gerOrder (String accessToken){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(USER_PATH)
                .then();
    }

}
