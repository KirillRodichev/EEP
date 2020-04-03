package servlets;

import constants.DispatchAttrs;
import constants.Parameters;
import controllers.BodyGroupController;
import controllers.EquipmentController;
import model.Equipment;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static utils.Dispatch.forwardToEquipmentPage;
import static utils.LoadEquipment.loadData;

@WebServlet("/equipment")
public class EquipmentLoader extends HttpServlet {

    private void proceedData(HttpServletRequest req, HttpServletResponse resp, int gymID) throws ServletException, IOException {
        List<Equipment> equipment = new ArrayList<>();
        Map<Integer, List<String>> equipmentBodyGroups = new HashMap<>();
        List<String> allBodyGroups;
        List<Equipment> restEquipment;

        EquipmentController equipmentController = new EquipmentController();
        BodyGroupController bodyGroupController = new BodyGroupController();

        try {
            restEquipment = equipmentController.getAll();
            loadData(equipment, equipmentBodyGroups, restEquipment, gymID, equipmentController, bodyGroupController);
            allBodyGroups = bodyGroupController.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        forwardToEquipmentPage(req, resp, gymID, equipment, equipmentBodyGroups, allBodyGroups, restEquipment);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gymID = Integer.parseInt(req.getParameter(Parameters.GYM_ID));
        proceedData(req, resp, gymID);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int gymID = (int) session.getAttribute(DispatchAttrs.GYM_ID);
        proceedData(req, resp, gymID);
    }
}