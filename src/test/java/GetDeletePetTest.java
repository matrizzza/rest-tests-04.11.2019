import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.core.Is.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetDeletePetTest {

    private static String petId;

    @Before
    public void preconditionsTest2GetPetById(){
        ValidatableResponse response = PetEndpoint.createPet();
        petId = response.extract().path("id");
    }

    @Test
    public void test1GetPetById() {
        PetEndpoint.given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(200)
                .body("name", is(PetEndpoint.getPetName()))
                .log().all()
        ;
    }

    @Test
    public void test3DeletePetById() {
        PetEndpoint.given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(200)
                .log().all();

        PetEndpoint.given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(404)
                .body("code", is(1))
                .body("message", is("Pet not found"))
                .log().all()
        ;
    }

}
