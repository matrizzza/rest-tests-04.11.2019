import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import javax.json.Json;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

class PetEndpoint {
    private static final String CREATE_PET = "/pet";
    static final String GET_PET = "/pet/{petId}";
    static final String DELETE_PET = "/pet/{petId}";
    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    private static String petName = "Vasiliy";
    private static String petCategory = "Dog";

    static String getPetName() {
        return petName;
    }

//    static final String BODY = new JSONObject()
//            .put("id", 0)
//            .put("category", new JSONObject()
//                    .put("id", 0)
//                    .put("name", petCategory))
//            .put("name", petName)
//            .put("photoUrls", new JSONArray()
//                    .put("string"))
//            .put("tags", new JSONArray()
//                    .put(new JSONObject()
//                            .put("id", 0)
//                            .put("name", "string")))
//            .put("status", "available")
//            .toString();

    private static final String BODY = Json.createObjectBuilder()
            .add("id", 0)
            .add("category", Json.createObjectBuilder()
                    .add("id", 0)
                    .add("name", petCategory))
            .add("name", petName)
            .add("photoUrls", Json.createArrayBuilder()
                    .add("string"))
            .add("tags", Json.createArrayBuilder()
                    .add(Json.createObjectBuilder()
                            .add("id", 0)
                            .add("name", "string")))
            .add("status", "available")
            .build()
            .toString();

    static RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .log().all()
                .contentType(ContentType.JSON);
    }

    static ValidatableResponse createPet() {
        return given()
                .body(PetEndpoint.BODY)
                .post(PetEndpoint.CREATE_PET)
                .then()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .body("name", is(petName))
                .log().all();
    }
}
