import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class GetDeletePetTest {

    PetEndpoint petEndpoint = new PetEndpoint();

    @Before
    public void preconditionsTests(){
        ValidatableResponse response = PetEndpoint.createPet()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .body("name", is(PetEndpoint.getPetName()));
        PetEndpoint.setPetId(response.extract().path("id"));
    }

    @Test
    public void getPetById() {
        PetEndpoint.getPet()
                .statusCode(200)
                .body("id", is(PetEndpoint.getPetId()))
                .body("name", is(PetEndpoint.getPetName()));
    }

    @Test
    public void deletePetById() {
        PetEndpoint.deletePet()
                .statusCode(200);

        PetEndpoint.getPet()
                .statusCode(404)
                .body("code", is(1))
                .body("message", is("Pet not found"));
    }
}
