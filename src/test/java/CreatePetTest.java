import data.Pet;
import data.StatusType;
import org.junit.Test;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.core.Is.is;

public class CreatePetTest {

    private Pet pet =
            Pet.builder().id(0).categoryName("CategoryOfMyPet").name("MyPetName_zxz").status(StatusType.trulala).build();

    @Test
    public void createPetTest() {
        PetEndpoint.createPet(pet)
                .statusCode(is(200))
                .body("category.name", is(pet.getCategory().getName()))
                .body("name", is(pet.getName()))
                .body("status", is(pet.getStatus().toString()));
    }

    @Test
    public void TestTest(){
        PetEndpoint.getPetByStatus(data.StatusType.sold)
                .statusCode(200)
                .body("status", everyItem(is(StatusType.sold.toString())));
    }
}

