package servlets;

import constants.DB;
import constants.DispatchAttrs;
import constants.Parameters;
import controllers.EquipmentController;
import model.Equipment;
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
import java.util.Set;

import static utils.FileU.imgUpload;
import static utils.JSON.jsonArrToSet;

@MultipartConfig
@WebServlet("/updateEquipment")
public class UpdateEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int gymID = Integer.parseInt(req.getParameter(DispatchAttrs.GYM_ID));
        final String name = req.getParameter(Parameters.EQUIPMENT_NAME);
        final String description = req.getParameter(Parameters.EQUIPMENT_DESCRIPTION);
        final Set<String> bgIDs = jsonArrToSet(req.getParameter(Parameters.BODY_GROUPS));
        final Part filePart = req.getPart(Parameters.EQUIPMENT_IMG_FILE);
        final String imgName = imgUpload(filePart);

        EquipmentDTO equipment = new EquipmentDTO(DB.EMPTY_FIELD, name, description, imgName, bgIDs);
        EquipmentController equipmentController = new EquipmentController();

        try {
            equipmentController.update(equipment, gymID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
