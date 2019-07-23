package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


//@XmlRootElement(name = "Youtube")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreatePlaylist {

    @JsonProperty("name")
    private String name;

    @JsonProperty("public")
    private boolean pPublic;

    @JsonProperty("collaborative")
    private boolean collaborative;

    @JsonProperty("description")
    private String description;

    public CreatePlaylist(String name) {
        this.name = name;
    }

    public CreatePlaylist(String name, boolean pPublic, boolean collaborative, String description) {
        this.name = name;
        this.pPublic = pPublic;
        this.collaborative = collaborative;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean ispPublic() {
        return pPublic;
    }

    public void setpPublic(boolean pPublic) {
        this.pPublic = pPublic;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreatePlaylist{" +
                "name='" + name + '\'' +
                ", pPublic=" + pPublic +
                ", collaborative=" + collaborative +
                ", description='" + description + '\'' +
                '}';
    }
}
