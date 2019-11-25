import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import javax.json.Json;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

class PetEndpoint {
    static final String DELETE_PET = "/pet/{petId}";
    private static final String GET_PET = "/pet/{petId}";
    private static final String CREATE_PET = "/pet";
    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    private static String petName = "Vasiliy";
    private static String petCategory = "Dog";
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

    private static long petId;

    static long getPetId() {
        return petId;
    }

    static void setPetId(long petId) {
        PetEndpoint.petId = petId;
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

    static String getPetName() {
        return petName;
    }

    static RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .log().all()
                .contentType(ContentType.JSON);
    }

    static ValidatableResponse getPetRequest() {
        return given()
                .get(PetEndpoint.GET_PET, petId)
                .then();
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
