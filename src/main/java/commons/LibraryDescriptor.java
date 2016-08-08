package commons;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Contains information for a dependency library, including its download link
 */
public class LibraryDescriptor {

    private String filename;
    private URL downloadLink;
    private commons.OsDetector.Os os;

    public LibraryDescriptor() {} // required for serialization

    public LibraryDescriptor(String filename, String downloadLink, commons.OsDetector.Os os)
            throws MalformedURLException {
        this.filename = filename;
        this.downloadLink = new URL(downloadLink);
        this.os = os;
    }

    public String getFileName() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public URL getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) throws MalformedURLException {
        this.downloadLink = new URL(downloadLink);
    }

    public commons.OsDetector.Os getOs() {
        return os;
    }

    public void setOs(commons.OsDetector.Os os) {
        this.os = os;
    }
}
