package servlets;

import constants.Parameters;
import controllers.EquipmentController;
import model.EquipmentDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static utils.FileU.imgUpload;

@MultipartConfig
@WebServlet("/updateEquipment")
public class UpdateEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int equipmentID = Integer.parseInt(req.getParameter(Parameters.EQUIPMENT_ID));
        final String name = req.getParameter(Parameters.EQUIPMENT_NAME);
        final String description = req.getParameter(Parameters.EQUIPMENT_DESCRIPTION);
        final Set<String> bgIDs = getBGIDs(req);
        final Part filePart = req.getPart(Parameters.EQUIPMENT_IMG_FILE);
        final String imgName = getImgName(filePart);

        EquipmentDTO equipment = new EquipmentDTO(equipmentID, name, description, imgName, bgIDs);
        EquipmentController equipmentController = new EquipmentController();

        try {
            equipmentController.update(equipment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getImgName(Part filePart) throws IOException, ServletException {
        String imgName;
        try {
            imgName = imgUpload(filePart);
        } catch (RuntimeException e) {
            imgName = null;
        }
        return imgName;
    }

    private Set<String> getBGIDs(HttpServletRequest req) {
        Set<String> bgIDs;
        try {
            bgIDs = new HashSet<>(Arrays.asList(req.getParameterValues(Parameters.BODY_GROUPS)));
        } catch (RuntimeException e) {
            bgIDs = null;
        }
        return bgIDs;
    }
}
