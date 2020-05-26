package servlets;

import constants.DispatchAttrs;
import controllers.EquipmentController;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/removeEquipment")
@MultipartConfig
public class RemoveEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int equipmentID = Integer.parseInt(req.getParameter(DispatchAttrs.EQUIPMENT_ID));
        int gymID = Integer.parseInt(req.getParameter(DispatchAttrs.GYM_ID));

        EquipmentController equipmentController = new EquipmentController();

        try {
            equipmentController.remove(gymID, equipmentID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
