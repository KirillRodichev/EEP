package servlets;

import constants.DB;
import constants.DispatchAttrs;
import constants.Parameters;
import controllers.EquipmentController;
import model.Equipment;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;
import java.util.Collection;

import static utils.Dispatch.redirectToEquipmentPage;

@WebServlet("/createEquipment")
@MultipartConfig
public class CreateEquipment extends HttpServlet {

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String fileUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");

        final String contextPath = "." + req.getContextPath();
        final String equipmentImgFolderPath = "/assets/img/equipment/";
        final Part filePart = req.getPart(Parameters.EQUIPMENT_IMG_FILE);
        final String fileName = getFileName(filePart);

        try (OutputStream out = new FileOutputStream(new File(contextPath + equipmentImgFolderPath + fileName));
             InputStream fileContent = filePart.getInputStream();
        ) {
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int gymID = Integer.parseInt(req.getParameter(DispatchAttrs.GYM_ID));
        final String name = req.getParameter(Parameters.EQUIPMENT_NAME);
        final String description = req.getParameter(Parameters.EQUIPMENT_DESCRIPTION);
        final String bodyGroups = req.getParameter(Parameters.BODY_GROUPS);

        final File file1 = new File(".");
        String relativePath1 = file1.getPath();
        String absolutePath1 = file1.getAbsolutePath();
        String canonicalPath1 = file1.getCanonicalPath();
        System.out.println("PATHS DO POST");
        System.out.println(relativePath1);
        System.out.println(canonicalPath1);
        System.out.println(absolutePath1);

        final String imgName = fileUpload(req, resp);

        Equipment equipment = new Equipment(DB.EMPTY_FIELD, name, description, imgName);
        EquipmentController equipmentController = new EquipmentController();

        try {
            equipmentController.create(equipment, gymID, bodyGroups);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        redirectToEquipmentPage(req, resp, gymID);
    }
}