package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
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
        this.category = Category.builder().id(1).name(categoryName).build();
        this.name = name;
        this.status = status;
        this.photoUrls = new String[]{"some uri to photo"};
        this.tags = new Tag[]{Tag.builder().id(111).name("test_tag_111").build()};
    }
}
