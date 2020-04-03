package servlets;

import constants.Parameters;
import controllers.EquipmentController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteEquipment")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gymID = Integer.parseInt(req.getParameter(Parameters.GYM_ID));

        EquipmentController equipmentController = new EquipmentController();

        try {
            equipmentController.delete(gymID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd;
        rd = req.getRequestDispatcher("pages/equipment.jsp");
        rd.forward(req, resp);
    }
}
