import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import javax.json.Json;
import java.io.File;

class PetEndpoint {
    private static final String DELETE_PET = "/pet/{petId}";
    private static final String GET_PET = "/pet/{petId}";
    private static final String GET_PET_BY_STATUS = "/pet/findByStatus";
    private static final String UPDATE_PET = "/pet";
    private static final String UPDATE_PET_WITH_DATA = "/pet/{petId}";
    private static final String CREATE_PET = "/pet";
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String UPLOAD_IMAGE_URI = "/pet/{petId}/uploadImage";

    @Getter
    @Setter
    private static String petName = "MyPetName_zzz";
    @Getter
    @Setter
    private static long petId = 0;
    @Getter
    @Setter
    private static String petCategory = "Dog";
    @Getter
    @Setter
    private static String body;
    @Getter
    @Setter
    private static String status = StatusType.available.toString();

    static {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    private static void setBody() {
        body = Json.createObjectBuilder()
                .add("id", petId)
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
                .add("status", status)
                .build()
                .toString();
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

    static ValidatableResponse getPetByStatus(StatusType statusType) {
        return given()
                .queryParam("status", statusType)
                .get(GET_PET_BY_STATUS)
                .then();
    }

    static ValidatableResponse createPet() {
        setBody();
        return given()
                .body(body)
                .post(CREATE_PET)
                .then();
    }

    static ValidatableResponse updatePet() {
        setBody();
        return given()
                .body(body)
                .put(UPDATE_PET)
                .then();
    }

    static ValidatableResponse updatePetWithData(String petName) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name", petName)
                .formParam("status", StatusType.sold)
                .post(UPDATE_PET_WITH_DATA, petId)
                .then();
    }

    static ValidatableResponse uploadImageByPetId() {
        return given()
                .contentType("multipart/form-data")
                .formParam("additionalMetadata", "doggy_image")  //work without this line... is it correct? O_o
                .multiPart(new File("D:\\doggy.jpeg"))
                .post(UPLOAD_IMAGE_URI, petId)
                .then()
                ;
    }

    static ValidatableResponse deletePet() {
        return given()
                .delete(DELETE_PET, petId)
                .then();
    }
}
