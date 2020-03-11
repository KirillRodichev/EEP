package servlets;

import constants.Columns;
import constants.DispatchAttrs;
import constants.Parameters;
import model.Equipment;
import oracleConnection.OracleConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static oracleConnection.OracleConnection.getOracleConnection;
import static oracleConnection.OracleConnection.getSingleIntResultSet;

@WebServlet("/equipment")
public class EquipmentLoader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Equipment> equipment = new ArrayList<>();
        Map<Integer, ArrayList<String>> equipmentBodyGroups = new HashMap<>();

        int gymId = Integer.parseInt(req.getParameter(Parameters.GYM_ID));
        ArrayList<Integer> gymIds = new ArrayList<>();
        ArrayList<Integer> bodyGroupIds = new ArrayList<>();
        ArrayList<ArrayList<Integer>> bGIdsList = new ArrayList<>();
        ArrayList<String> bodyGroups = new ArrayList<>();
        ArrayList<ArrayList<String>> bGList = new ArrayList<>();

        String selectEquipmentIds =
                "SELECT EQUIPMENT_ID FROM SYSTEM.GYMS_EQUIPMENT WHERE GYM_ID = ?";

        String selectEquipment =
                "SELECT * FROM SYSTEM.EQUIPMENT WHERE EQUIPMENT_ID = ?";

        String selectBodyGroupIds =
                "SELECT B_GROUP_ID FROM SYSTEM.B_GROUPS_EQUIPMENT WHERE EQUIPMENT_ID = ?";

        String selectBodyGroup =
                "SELECT B_GROUP_NAME FROM SYSTEM.BODY_GROUPS WHERE B_GROUP_ID = ?";

        try (Connection connection = getOracleConnection()) {

            ResultSet rsEquipmentIds = getSingleIntResultSet(selectEquipmentIds, connection, gymId);
            while (rsEquipmentIds.next()) {
                gymIds.add(rsEquipmentIds.getInt(Columns.GYM_ID));
            }

            for (Integer id : gymIds) {
                ResultSet rsEquipment = getSingleIntResultSet(selectEquipment, connection, id);
                while (rsEquipment.next()) {
                    equipment.add(new Equipment(
                            rsEquipment.getInt(Columns.EQUIPMENT_ID),
                            rsEquipment.getString(Columns.EQUIPMENT_NAME),
                            rsEquipment.getString(Columns.EQUIPMENT_DESCRIPTION),
                            rsEquipment.getString(Columns.EQUIPMENT_IMG_PATH)
                    ));
                }
            }

            for (Equipment eq : equipment) {
                ResultSet rsEq = getSingleIntResultSet(selectBodyGroupIds, connection, eq.getId());
                while (rsEq.next()) {
                    bodyGroupIds.add(rsEq.getInt(Columns.EQUIPMENT_ID));
                }
                bGIdsList.add(bodyGroupIds);
            }
            for (ArrayList<Integer> idList : bGIdsList) {
                for (Integer id : idList) {
                    ResultSet rsBodyGroup = getSingleIntResultSet(selectBodyGroup, connection, id);
                    while (rsBodyGroup.next()) {
                        bodyGroups.add(rsBodyGroup.getString(Columns.EQUIPMENT_ID));
                    }
                }
                bGList.add(bodyGroups);
            }
            for (int i = 0; i < equipment.size(); i++) {
                equipmentBodyGroups.put(equipment.get(i).getId(), bGList.get(i));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd;
        if (!equipment.isEmpty()) {
            req.setAttribute(DispatchAttrs.EQUIPMENT, equipment);
            req.setAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP, equipmentBodyGroups);
            rd = req.getRequestDispatcher("pages/equipment.jsp");
        } else {
            rd = req.getRequestDispatcher("pages/nosuccess.jsp");
        }
        rd.forward(req, resp);
    }
}
