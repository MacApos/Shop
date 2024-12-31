package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private Long id;
    private String name;
    private String path;
    private Long parentId;
    private List<CategoryDto> children;

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }
}
