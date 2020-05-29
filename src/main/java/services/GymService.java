package services;

import dao.GymDAOImp;
import model.entity.GymEntity;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class GymService {
    
    private GymDAOImp gymDAOImp = new GymDAOImp();
    
    public GymService() {}

    public GymEntity get(int id) { return gymDAOImp.getById(id); }

    public int getIdByUserId(int userID) { return gymDAOImp.getIdByUserId(userID); }

    public List<String> getAllNames() { return gymDAOImp.getAllNames(); }

    public void create(GymEntity gym) { gymDAOImp.create(gym); }

    public File createXML(int gymID, Set<Integer> BGFilters)
            throws SQLException, FileNotFoundException, SAXException, JAXBException
    { return gymDAOImp.createXML(gymID, BGFilters); }

    public void delete(GymEntity gym) { gymDAOImp.delete(gym); }

    public void update(GymEntity gym) { gymDAOImp.update(gym); }

    public void update(GymEntity gym, int gymID) { gymDAOImp.update(gym, gymID); }

    public List<GymEntity> getAll() { return gymDAOImp.getAll(); }
}
