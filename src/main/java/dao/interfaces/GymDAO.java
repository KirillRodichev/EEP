package dao.interfaces;

import model.entity.GymEntity;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface GymDAO {
    int getIdByUserId(int userID);

    List<String> getAllNames();

    void update(GymEntity gym, int gymID);

    void merge(GymEntity prev, GymEntity cur);

    File createXML(int gymID, Set<Integer> BGFilters) throws SQLException, JAXBException, SAXException, FileNotFoundException;
}
