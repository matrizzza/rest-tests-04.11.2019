import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class CreatePetTest {

    @Test
    public void createPetTest() {
        PetEndpoint.createPet()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .body("name", is(PetEndpoint.getPetName()))
                .log().all();
    }
}
