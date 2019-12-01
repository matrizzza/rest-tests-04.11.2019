package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PUBLIC)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Tag {
    private long id;
    private String name;
}
