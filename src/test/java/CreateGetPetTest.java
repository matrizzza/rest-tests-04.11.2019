import data.Pet;
import data.StatusType;
import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class CreateGetPetTest {

    private Pet pet = new Pet(0, "Dog", "MyPetName_zzz", StatusType.trulala.toString());

    @Test
    public void createPetTest() {
        PetEndpoint.setBody();
        PetEndpoint.createPet(pet)
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .body("name", is(PetEndpoint.getPetName()))
                .log().all();
    }


}

