package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String path;
    private Long parentId;
    private List<CategoryDto> children;
}
