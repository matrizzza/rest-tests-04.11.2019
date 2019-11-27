import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class CreateGetPetTest {

    @Test
    public void createPetTest() {
        PetEndpoint.createPet()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .body("name", is(PetEndpoint.getPetName()))
                .log().all();
    }

    @Test
    public void getPetByStatus(){
        PetEndpoint.getPetByStatus(StatusType.available)
                .statusCode(200);

        PetEndpoint.getPetByStatus(StatusType.pending)
                .statusCode(200);

        PetEndpoint.getPetByStatus(StatusType.sold)
                .statusCode(200);

//        PetEndpoint.getPetByStatus(StatusType.invalid)
//                .statusCode(400);
    }
}
