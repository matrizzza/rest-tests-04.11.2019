import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiPetTest {

    private static ApiPet apiPet;

    @BeforeClass
    public static void setMethod(){
        apiPet = new ApiPet();
    }

    @Test
    public void test1CreatePet() {

        ValidatableResponse response =
                given()
                        .body(apiPet.getBody())
                        .contentType(ContentType.JSON)
                        .post(apiPet.getUri())
                        .then()
                        .statusCode(is(200))
                        .body("category.name", is(not("")))
                        .log().all()
                        ;
        apiPet.setPetId(response.extract().path("id"));
    }

    @Test
    public void test2GetPetById() {
        given()
                .contentType(ContentType.JSON)
                .get(apiPet.getUri() + apiPet.getPetId())
                .then()
                .statusCode(200)
                .body("name", is("Vasiliy"))
                .log().all()
        ;
    }

    @Test
    public void test3DeletePetById() {
        given()
                .contentType(ContentType.JSON)
                .delete(apiPet.getUri() + apiPet.getPetId())
                .then()
                .statusCode(200)
                .log().all();
    }
}
