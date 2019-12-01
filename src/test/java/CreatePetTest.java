import data.Pet;
import data.StatusType;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class CreatePetTest {

    private Pet pet = new Pet(0, "Dog", "MyPetName_zzz", StatusType.trulala.toString());

    @Test
    public void createPetTest() {
        PetEndpoint.createPet(pet)
                .statusCode(is(200))
                .body("category.name", is(pet.getCategory().getName()))
                .body("name", is(pet.getName()));
    }
}

