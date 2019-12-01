import data.Pet;
import data.StatusType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

public class PetTest {

    private static Pet pet = new Pet(0, "Dog", "MyPetName_zzz", StatusType.trulala.toString());

    @Before
    public void preconditionsTest() {
        ValidatableResponse response = PetEndpoint.createPet(pet)
                .statusCode(is(200))
                .body("category.name", is(pet.getCategory().getName()))
                .body("name", is(pet.getName()));
        pet.setId(response.extract().path("id"));
    }

    @Test
    public void getPetById() {
        PetEndpoint.getPet(pet.getId())
                .statusCode(200)
                .body("id", is(pet.getId()))
                .body("name", is(pet.getName()));
    }

    @Test
    public void getPetByStatus() {
        PetEndpoint.getPetByStatus(StatusType.trulala)
                .statusCode(200)
                .body("status[0]", is(StatusType.trulala.toString())); //TODO check all statuses of pets

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
        PetEndpoint.deletePet(pet.getId())
                .statusCode(200);

        PetEndpoint.getPet(pet.getId())
                .statusCode(404)
                .body("code", is(1))
                .body("message", is("Pet not found"));
    }

    @Test
    public void updatePetTest() {
        pet.setName(pet.getName() + "_new");
        PetEndpoint.updatePet(pet)
                .statusCode(200)
                .body("name", is(pet.getName()));
    }

    @Test
    public void updatePetTestWithData() {
        PetEndpoint.updatePetWithData(pet.getId(), pet.getName() + "_new")
                .statusCode(200);

        PetEndpoint.getPet(pet.getId())
                .statusCode(200)
                .body("id", is(pet.getId()))
                .body("name", is(pet.getName() + "_new"));
    }

    @Test
    public void uploadImage() {
        PetEndpoint.uploadImageByPetId(pet.getId())
                .statusCode(200)
                .body("code", is(200))
                .body("message", containsString("doggy.jpeg"));
    }
}
