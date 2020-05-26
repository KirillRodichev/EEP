package servlets;

import controllers.GymController;
import lombok.SneakyThrows;
import model.GymDTO;
import org.xml.sax.SAXException;
import xml.GymSSB;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;

import static constants.DispatchAttrs.XML_FILE;
import static constants.Parameters.GYM_ID;
import static utils.FileU.getFileName;

@MultipartConfig
@WebServlet("/XMLUpload")
public class XMLUpload extends HttpServlet {

    final String targetPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\target\\EEP-1.0-SNAPSHOT\\";

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gymID = Integer.parseInt(req.getParameter(GYM_ID));
        final Part filePart = req.getPart(XML_FILE);
        final String xmlName = fileUpload(filePart);

        GymDTO gymDTO;

        try {
            gymDTO = new GymSSB().importXML(new File(targetPath + xmlName));
        } catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        uploadGymToDB(gymDTO, gymID);
    }

    private String fileUpload(Part filePart) throws IOException, ServletException {
        final String fileName = getFileName(filePart);

        try (OutputStream fTargetOut = new FileOutputStream(new File(targetPath + fileName));
             InputStream fileContent = filePart.getInputStream();
        ) {
            int read;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                fTargetOut.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    private void uploadGymToDB(GymDTO gymDTO, int gymID) throws SQLException {
        GymController gymController = new GymController();

        if (gymDTO.getId() == gymID) {
            gymController.update(gymDTO);
        } else {
            // TODO replace with create method
            gymController.update(gymDTO, gymID);
        }
    }
}
