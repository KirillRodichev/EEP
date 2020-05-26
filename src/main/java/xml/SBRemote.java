package xml;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SBRemote<T> {

    public File exportXML(T obj) throws JAXBException, FileNotFoundException, SAXException;

    public T importXML(File file) throws IOException, SAXException, ParserConfigurationException;
}
