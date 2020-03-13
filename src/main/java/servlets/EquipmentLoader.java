package servlets;

import constants.Columns;
import constants.DispatchAttrs;
import constants.Parameters;
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
import java.util.Map;

import static oracleConnection.OracleConnection.getOracleConnection;
import static oracleConnection.OracleConnection.getSingleIntResultSet;

@WebServlet("/equipment")
public class EquipmentLoader extends HttpServlet {

    private void loadEquipmentIDs(String sql, Connection con, int val, ArrayList<Integer> IDs) throws SQLException {
        ResultSet rsEquipmentIds = getSingleIntResultSet(sql, con, val);
        while (rsEquipmentIds.next()) {
            IDs.add(rsEquipmentIds.getInt(Columns.GYM_ID));
        }
    }

    private void loadEquipment(String sql, Connection con, ArrayList<Integer> IDs, ArrayList<Equipment> equipment)
            throws SQLException {
        for (Integer id : IDs) {
            ResultSet rsEquipment = getSingleIntResultSet(sql, con, id);
            while (rsEquipment.next()) {
                equipment.add(new Equipment(
                        rsEquipment.getInt(Columns.EQUIPMENT_ID),
                        rsEquipment.getString(Columns.EQUIPMENT_NAME),
                        rsEquipment.getString(Columns.EQUIPMENT_DESCRIPTION),
                        rsEquipment.getString(Columns.EQUIPMENT_IMG_PATH)
                ));
            }
        }
    }

    private void loadBodyGroupsIDs(
            String sql,
            Connection con,
            ArrayList<Equipment> equipment,
            ArrayList<Integer> IDs,
            ArrayList<ArrayList<Integer>> IDsList
            ) throws SQLException {
        for (Equipment eq : equipment) {
            ResultSet rsEq = getSingleIntResultSet(sql, con, eq.getId());
            while (rsEq.next()) {
                IDs.add(rsEq.getInt(Columns.EQUIPMENT_ID));
            }
            IDsList.add((ArrayList<Integer>) IDs.clone());
            IDs.clear();
        }
    }

    private void loadBodyGroups(
            String sql,
            Connection con,
            ArrayList<ArrayList<Integer>> IDsList,
            ArrayList<String> bodyGroups,
            ArrayList<ArrayList<String>> bodyGroupsList
    ) throws SQLException {
        for (ArrayList<Integer> idList : IDsList) {
            for (Integer id : idList) {
                ResultSet rsBodyGroup = getSingleIntResultSet(sql, con, id);
                while (rsBodyGroup.next()) {
                    bodyGroups.add(rsBodyGroup.getString(Columns.EQUIPMENT_ID));
                }
            }
            bodyGroupsList.add((ArrayList<String>) bodyGroups.clone());
            bodyGroups.clear();
        }
    }

    private void loadAllBodyGroups(String sql, Connection con, ArrayList<String> allBodyGroups) throws SQLException {
        Statement statementBgs = con.createStatement();
        ResultSet rsBgs = statementBgs.executeQuery(sql);
        while (rsBgs.next()) {
            allBodyGroups.add(rsBgs.getString(Columns.EQUIPMENT_ID));
        }
    }

    private void loadMap(
            ArrayList<Equipment> equipment,
            ArrayList<ArrayList<String>> bodyGroupsList,
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
        ArrayList<Integer> equipmentIDs = new ArrayList<>();
        ArrayList<Integer> bodyGroupsIDs = new ArrayList<>();
        ArrayList<ArrayList<Integer>> bodyGroupsIDsList = new ArrayList<>();
        ArrayList<String> bodyGroups = new ArrayList<>();
        ArrayList<ArrayList<String>> bodyGroupsList = new ArrayList<>();
        ArrayList<String> allBodyGroups = new ArrayList<>();

        String selectEquipmentIds =
                "SELECT EQUIPMENT_ID FROM GYMS_EQUIPMENT WHERE GYM_ID = ?";

        String selectEquipment =
                "SELECT * FROM EQUIPMENT WHERE EQUIPMENT_ID = ?";

        String selectBodyGroupsIDs =
                "SELECT B_GROUP_ID FROM B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";

        String selectBodyGroup =
                "SELECT B_GROUP_NAME FROM BODY_GROUPS WHERE B_GROUP_ID = ?";

        String selectBodyGroups =
                "SELECT B_GROUP_NAME FROM BODY_GROUPS";

        try (Connection connection = getOracleConnection()) {

            loadEquipmentIDs(selectEquipmentIds, connection, gymID, equipmentIDs);

            loadEquipment(selectEquipment, connection, equipmentIDs, equipment);

            loadBodyGroupsIDs(selectBodyGroupsIDs, connection, equipment, bodyGroupsIDs, bodyGroupsIDsList);

            loadBodyGroups(selectBodyGroup, connection, bodyGroupsIDsList, bodyGroups, bodyGroupsList);

            loadMap(equipment, bodyGroupsList, equipmentBodyGroups);

            loadAllBodyGroups(selectBodyGroups, connection, allBodyGroups);

        } catch (SQLException | ClassNotFoundException e) {
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
}
