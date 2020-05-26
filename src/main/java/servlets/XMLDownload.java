package servlets;

import controllers.GymController;
import lombok.SneakyThrows;
import org.xml.sax.SAXException;
import utils.FileU;
import utils.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static constants.Parameters.BG_FILTERS;
import static constants.Parameters.GYM_ID;
import static utils.JSON.stringify;

@MultipartConfig
@WebServlet("/XMLDownload")
public class XMLDownload extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String TARGET_PATH = "D:\\My Documents\\Java\\TeamProjects\\EEP\\target\\EEP-1.0-SNAPSHOT\\";
        final String FILE_NAME = "gym.xml";

        int gymID = Integer.parseInt(req.getParameter(GYM_ID));
        String sBGFilters = req.getParameter(BG_FILTERS);
        Set<Integer> BGFilters;
        try {
            BGFilters = JSON.jsonArrToSet(sBGFilters);
        } catch (RuntimeException e) {
            BGFilters = null;
        }

        File XMLFile;
        File dest = new File(TARGET_PATH + FILE_NAME);

        try {
            XMLFile = new GymController().createXML(gymID, BGFilters);
            FileU.copyFile(XMLFile, dest);
        } catch (SQLException | JAXBException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        resp.getWriter().print(stringify("gym.xml"));
    }
}
