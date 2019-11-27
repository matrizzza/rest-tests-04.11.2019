import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class GetUpdateDeletePetTest {

    @Before
    public void preconditionsTest() {
        PetEndpoint.setPetId(0);
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

    @Test
    public void updatePetTest() {
        PetEndpoint.setPetName(PetEndpoint.getPetName() + "_new");
        PetEndpoint.updatePet()
                .statusCode(200)
                .body("name", is(PetEndpoint.getPetName()))
        ;
    }

    @Test
    public void updatePetTestWithData() {
        PetEndpoint.setPetName(PetEndpoint.getPetName() + "_new");
        PetEndpoint.updatePetWithData(PetEndpoint.getPetName())
                .statusCode(200)
        ;
        /*
            Странно, почему не приходит Json в ответ на POST !!!
            проверяем GET-ом предыдущий POST
         */
        PetEndpoint.getPet()
                .statusCode(200)
                .body("id", is(PetEndpoint.getPetId()))
                .body("name", is(PetEndpoint.getPetName()))
        ;
    }

    @Test
    public void uploadImage() {
        PetEndpoint.uploadImageByPetId()
                .statusCode(200)
        .body("code", is(200))
        .body("message", containsString("doggy.jpeg"))
        ;
    }
}
