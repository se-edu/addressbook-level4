package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Helps with reading from and writing to XML files.
 */
public class XmlUtil {

    /**
     * Returns the xml data in the file as an object of the specified type.
     *
     * @param filePath       Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(Path filePath, Class<T> classToConvert)
            throws FileNotFoundException, JAXBException {

        requireNonNull(filePath);
        requireNonNull(classToConvert);

        if (!FileUtil.isFileExists(filePath)) {
            throw new FileNotFoundException("File not found : " + filePath.toAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(filePath.toFile()));
    }

    /**
     * Saves the data in the file in xml format.
     *
     * @param filePath Points to a valid xml file containing data that match the {@code classToConvert}.
     *                 Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if there is an error during converting the data
     *                               into xml and writing to the file.
     */
    public static <T> void saveDataToFile(Path filePath, T data) throws FileNotFoundException, JAXBException {

        requireNonNull(filePath);
        requireNonNull(data);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found : " + filePath.toAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(data, filePath.toFile());
    }

}
