package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Pet {
    private long id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private String status;

    public Pet(long id, String categoryName, String name, String status) {
        this.id = id;
        this.category = new Category(0, categoryName);
        this.name = name;
        this.status = status;
        this.photoUrls = new String[]{};
        this.tags = new Tag[]{};
    }
}
