import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiPetTest {

    private static long petId;


    public RequestSpecification given(){
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON);
    }

    @Test
    public void test1CreatePet() {

        ValidatableResponse response =
                given()
                        .body(PetEndpoint.BODY)
                        .post(PetEndpoint.CREATE_PET)
                        .then()
                        .statusCode(is(200))
                        .body("category.name", is(not("")))
                        .log().all()
                        ;
        petId = response.extract().path("id");
    }

    @Test
    public void test2GetPetById() {
        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(200)
                .body("name", is("Vasiliy"))
                .log().all()
        ;
    }

    @Test
    public void test3DeletePetById() {
        given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(200)
                .log().all();
    }
}
