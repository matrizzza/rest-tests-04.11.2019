import data.Pet;
import data.StatusType;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

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

    static {
//        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
//        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    private static RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    static ValidatableResponse getPet(long petId) {
        return given()
                .get(GET_PET, petId)
                .then();
    }

    static ValidatableResponse getPetByStatus(StatusType statusType) {
        return given()
                .queryParam("status", statusType)
                .get(GET_PET_BY_STATUS)
                .then();
    }

    static ValidatableResponse createPet(Pet pet) {
        return given()
                .body(pet)
                .post(CREATE_PET)
                .then();
    }

    static ValidatableResponse updatePet(Pet pet) {
        return given()
                .body(pet)
                .put(UPDATE_PET)
                .then();
    }

    static ValidatableResponse updatePetWithData(long petId, String petName) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name", petName)
                .formParam("status", StatusType.sold)
                .post(UPDATE_PET_WITH_DATA, petId)
                .then();
    }

    static ValidatableResponse uploadImageByPetId(long petId) {
        return given()
                .contentType("multipart/form-data")
                .formParam("additionalMetadata", "doggy_image")  //work without this line... is it correct? O_o
                .multiPart(new File("D:\\doggy.jpeg"))
                .post(UPLOAD_IMAGE_URI, petId)
                .then();
    }

    static ValidatableResponse deletePet(long petId) {
        return given()
                .delete(DELETE_PET, petId)
                .then();
    }
}
