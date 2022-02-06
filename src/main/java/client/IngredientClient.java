package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.IngredientResponse;

import static io.restassured.RestAssured.given;

public class IngredientClient extends RestAssuredClient {

    private static final String USER_PATH = "/api/ingredients";

    @Step("Send GET request to " + USER_PATH)
    public IngredientResponse getIngredients (){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(USER_PATH)
                .body()
                .as(IngredientResponse.class);
    }
    }