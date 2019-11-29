import data.Pet;
import data.StatusType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class GetUpdateDeletePetTest {

    private Pet pet = new Pet(0, "Dog", "MyPetName_zzz", StatusType.trulala.toString());

    @Before
    public void preconditionsTest() {
        PetEndpoint.setPetId(0);
        PetEndpoint.setBody();
        ValidatableResponse response = PetEndpoint.createPet(pet)
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
    public void getPetByStatus() {
        PetEndpoint.getPetByStatus(StatusType.trulala)
                .statusCode(200)
                .body("status[0]", is(StatusType.trulala.toString()));

//        PetEndpoint.getPetByStatus(data.StatusType.pending)
//                .statusCode(200);
//
//        PetEndpoint.getPetByStatus(data.StatusType.sold)
//                .statusCode(200);

//        PetEndpoint.getPetByStatus(data.StatusType.invalid)
//                .statusCode(400);
    }


    @Test
    public void deletePetById() {
        PetEndpoint.deletePet()
                .statusCode(200);

        PetEndpoint.getPet()
                .statusCode(404)
                .body("code", is(1))
                .body("message", is("data.Pet not found"));
    }

    @Test
    public void updatePetTest() {
        PetEndpoint.setPetName(PetEndpoint.getPetName() + "_new");
        PetEndpoint.setBody();
        PetEndpoint.updatePet()
                .statusCode(200)
                .body("name", is(PetEndpoint.getPetName()));
    }

    @Test
    public void updatePetTestWithData() {
        PetEndpoint.updatePetWithData(PetEndpoint.getPetId(), PetEndpoint.getPetName() + "_new")
                .statusCode(200);
        /*
            Странно, почему не приходит Json в ответ на POST !!!
            проверяем GET-ом предыдущий POST
         */
        PetEndpoint.getPet()
                .statusCode(200)
                .body("id", is(PetEndpoint.getPetId()))
                .body("name", is(PetEndpoint.getPetName() + "_new"));
    }

    @Test
    public void uploadImage() {
        PetEndpoint.uploadImageByPetId()
                .statusCode(200)
                .body("code", is(200))
                .body("message", containsString("doggy.jpeg"));
    }
}
