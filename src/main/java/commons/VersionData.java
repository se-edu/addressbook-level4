package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists latest application's version, main app download link and libraries descriptors
 */
@JsonPropertyOrder({ "version", "libraries" })
public class VersionData {
    @JsonProperty("version")
    private String versionString;
    private List<LibraryDescriptor> libraries = new ArrayList<>();

    public VersionData() {} // required for serialization

    public String getVersion() {
        return versionString;
    }

    public void setVersion(String versionString) {
        this.versionString = versionString;
    }

    public void setLibraries(List<LibraryDescriptor> libraries) {
        this.libraries = libraries;
    }

    public List<LibraryDescriptor> getLibraries() {
        return libraries;
    }
}
