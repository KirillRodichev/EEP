package servlets;

import constants.DispatchAttrs;
import constants.Parameters;
import controllers.BodyGroupController;
import controllers.EquipmentController;
import model.Equipment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/equipment")
public class EquipmentLoader extends HttpServlet {

    private void loadEquipment(
            EquipmentController equipmentController, ArrayList<Integer> IDs, ArrayList<Equipment> equipment)
            throws SQLException {
        for (Integer id : IDs) {
            equipment.add(equipmentController.getById(id));
        }
    }

    private void loadBodyGroupsIDs(
            BodyGroupController controller, ArrayList<Equipment> equipment, ArrayList<ArrayList<Integer>> IDsList
            ) throws SQLException {
        for (Equipment eq : equipment) {
            List<Integer> IDs = controller.getIdsListByEquipmentId(eq.getId());
            IDsList.add((ArrayList<Integer>) IDs);
        }
    }

    private void loadBodyGroups(
            BodyGroupController controller, ArrayList<ArrayList<Integer>> IDsList,
            ArrayList<ArrayList<String>> bodyGroupsList
    ) throws SQLException {
        for (ArrayList<Integer> idList : IDsList) {
            List<String> bodyGroups = new ArrayList<>();
            for (Integer id : idList) {
                bodyGroups.add(controller.getById(id));
            }
            bodyGroupsList.add(new ArrayList<>(bodyGroups));
        }
    }

    private void loadMap(
            ArrayList<Equipment> equipment, ArrayList<ArrayList<String>> bodyGroupsList,
            Map<Integer, ArrayList<String>> map
    ) {
        for (int i = 0; i < equipment.size(); i++) {
            map.put(equipment.get(i).getId(), bodyGroupsList.get(i));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Equipment> equipment = new ArrayList<>();
        Map<Integer, ArrayList<String>> equipmentBodyGroups = new HashMap<>();

        int gymID = Integer.parseInt(req.getParameter(Parameters.GYM_ID));
        ArrayList<Integer> equipmentIDs;
        ArrayList<ArrayList<Integer>> bodyGroupsIDsList = new ArrayList<>();
        ArrayList<ArrayList<String>> bodyGroupsList = new ArrayList<>();
        ArrayList<String> allBodyGroups ;

        EquipmentController equipmentController = new EquipmentController();
        BodyGroupController bodyGroupController = new BodyGroupController();

        try {

            equipmentIDs = (ArrayList<Integer>) equipmentController.getIdListByGymId(gymID);

            loadEquipment(equipmentController, equipmentIDs, equipment);

            loadBodyGroupsIDs(bodyGroupController, equipment, bodyGroupsIDsList);

            loadBodyGroups(bodyGroupController, bodyGroupsIDsList, bodyGroupsList);

            loadMap(equipment, bodyGroupsList, equipmentBodyGroups);

            allBodyGroups = (ArrayList<String>) bodyGroupController.getAll();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd;
        if (!equipment.isEmpty()) {
            req.setAttribute(DispatchAttrs.BODY_GROUPS, allBodyGroups);
            req.setAttribute(DispatchAttrs.EQUIPMENT, equipment);
            req.setAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP, equipmentBodyGroups);
            rd = req.getRequestDispatcher("pages/equipment.jsp");
        } else {
            rd = req.getRequestDispatcher("pages/nosuccess.jsp");
        }
        rd.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}