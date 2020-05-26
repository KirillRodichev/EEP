package servlets;

import controllers.BodyGroupController;
import controllers.EquipmentController;
import model.Equipment;
import utils.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import static constants.Parameters.*;
import static utils.DTO.getDTO;
import static utils.Dispatch.forwardToEquipmentPage;
import static utils.JSON.stringify;
import static utils.DB.loadData;

@WebServlet("/equipment")
@MultipartConfig
public class EquipmentLoader extends HttpServlet {

    private void doSyncPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int size;
        int gymID = Integer.parseInt(req.getParameter(PID));
        List<Equipment> equipment = new ArrayList<>();
        Map<Integer, Set<String>> eqBodyGroups = new HashMap<>();
        List<String> allBodyGroups;
        List<Equipment> restEquipment;

        EquipmentController eqController = new EquipmentController();
        BodyGroupController bgController = new BodyGroupController();

        try {
            restEquipment = eqController.getAll();
            size = loadData(equipment, eqBodyGroups, restEquipment, gymID, eqController, bgController);
            allBodyGroups = bgController.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        forwardToEquipmentPage(req, resp, gymID, equipment, eqBodyGroups, allBodyGroups, restEquipment, size);
    }

    private void doAsyncPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int size;
        int gymID = Integer.parseInt(req.getParameter(GYM_ID));
        List<Equipment> equipment = new ArrayList<>();
        Map<Integer, Set<String>> eqBodyGroups = new HashMap<>();

        String sFilters = req.getParameter(FILTERS);
        Set<Integer> filters = JSON.jsonArrToSet(sFilters);
        String sPageNumber = req.getParameter(PAGE_NUMBER);
        String sPageSize = req.getParameter(PAGE_SIZE);
        int pageNumber = sPageNumber != null ? Integer.parseInt(sPageNumber) : INITIAL_PAGE_NUMBER;
        int pageSize = sPageSize != null ? Integer.parseInt(sPageSize) : INITIAL_PAGE_SIZE;

        try {
            size = loadData(equipment, eqBodyGroups, gymID, pageNumber, pageSize, filters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out = resp.getWriter();
        List<Object> dto = new ArrayList<>();
        dto.add(size);
        dto.addAll(getDTO(equipment, eqBodyGroups));
        out.print(stringify(dto));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter(ASYNC) == null) {
            doSyncPost(req, resp);
        } else {
            doAsyncPost(req, resp);
        }
    }
}