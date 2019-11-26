import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import javax.json.Json;

class PetEndpoint {
    private static final String DELETE_PET = "/pet/{petId}";
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

    static {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    static long getPetId() {
        return petId;
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

    static void setPetId(long petId) {
        PetEndpoint.petId = petId;
    }

    static String getPetName() {
        return petName;
    }

    private static RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    static ValidatableResponse getPet() {
        return given()
                .get(PetEndpoint.GET_PET, petId)
                .then();
    }

    static ValidatableResponse createPet() {
        return given()
                .body(BODY)
                .post(CREATE_PET)
                .then();
    }

    static ValidatableResponse deletePet() {
        return given()
                .delete(PetEndpoint.DELETE_PET, PetEndpoint.getPetId())
                .then();
    }
}
