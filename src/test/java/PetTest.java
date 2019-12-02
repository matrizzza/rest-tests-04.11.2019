import data.Pet;
import data.StatusType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.core.Is.is;

public class PetTest {

    private static Pet pet =
            Pet.builder()
                    .id(0)
                    .categoryName("CategoryOfMyPet")
                    .name("MyPetName_zxz")
                    .status(StatusType.trulala)
                    .build();

    URL filePath = getClass().getResource("doggy.jpeg");
    File file = new File(filePath.getPath());

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
                .body("status", everyItem(is(StatusType.trulala.toString()))); //check all statuses of pets DONE!

        PetEndpoint.getPetByStatus(data.StatusType.sold)
                .statusCode(200)
                .body("status", everyItem(is(StatusType.sold.toString())));
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
        Pet updatedPet = pet.toBuilder().name(pet.getName() + "_new").build();

        PetEndpoint.updatePet(updatedPet)
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
        PetEndpoint.uploadImageByPetId(pet.getId(), file)
                .statusCode(200)
                .body("code", is(200))
                .body("message", containsString("doggy.jpeg"));
    }
}
