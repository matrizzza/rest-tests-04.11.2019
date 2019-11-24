import org.json.JSONArray;
import org.json.JSONObject;

import javax.json.Json;

class PetEndpoint {
    static final String CREATE_PET = "/pet";
    static final String GET_PET = "/pet/{petId}";
    static final String DELETE_PET = "/pet/{petId}";

    private static String petName = "Vasiliy";
    private static String petCategory = "Dog";

    static final String BODY = new JSONObject()
            .put("id", 0)
            .put("category", new JSONObject()
                    .put("id", 0)
                    .put("name", petCategory))
            .put("name", petName)
            .put("photoUrls", new JSONArray()
                    .put("string"))
            .put("tags", new JSONArray()
                    .put(new JSONObject()
                            .put("id", 0)
                            .put("name", "string")))
            .put("status", "available")
            .toString();

    static final String BODY_2 = Json.createObjectBuilder()
            .add("id", 0)
            .add("category", Json.createObjectBuilder()
                    .add("id", 0)
                    .add("name", petCategory))
            .add("name", petName)
            .add("photoUrls", Json.createArrayBuilder()
                    .add("string"))
            .add("tags", Json.createArrayBuilder()
                    .add(Json.createObjectBuilder()
                            .add("id", 0)
                            .add("name", "string")))
            .add("status", "available")
            .build()
            .toString();
}
