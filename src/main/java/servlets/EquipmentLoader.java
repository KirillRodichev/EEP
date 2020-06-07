package servlets;

import model.Equipment;
import model.LoadedEquipment;
import model.entity.EquipmentEntity;
import model.entity.interfaces.Accessible;
import services.BodyGroupService;
import services.EquipmentService;
import utils.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Parameters.*;
import static utils.DTO.getDTO;
import static utils.Dispatch.forwardToEquipmentPage;
import static utils.JSON.stringify;

@WebServlet("/equipment")
@MultipartConfig
public class EquipmentLoader extends HttpServlet {

    private void doSyncPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int size;
        int gymID = Integer.parseInt(req.getParameter(PID));
        List<EquipmentEntity> pageEquipment = new ArrayList<>();
        Map<Integer, List<String>> eqBodyGroups = new HashMap<>();
        List<String> allBodyGroups;
        List<EquipmentEntity> restEquipment;

        BodyGroupService bgService = new BodyGroupService();
        EquipmentService eqService = new EquipmentService();

        restEquipment = eqService.getAll();
        loadRest(eqService.getIDsByGymId(gymID), restEquipment);

        allBodyGroups = getNames(bgService.getAll());

        LoadedEquipment iDsForSinglePage = eqService.getIDsForSinglePage(INITIAL_PAGE_NUMBER, INITIAL_PAGE_SIZE, gymID);
        size = iDsForSinglePage.getEquipmentNumber();
        List<Integer> equipmentIDsForSinglePage = iDsForSinglePage.getEquipmentIDsForSinglePage();
        for (Integer id : equipmentIDsForSinglePage) {
            pageEquipment.add(eqService.get(id));
            List<String> singleEqBGs = getNames(eqService.get(id).getEqBodyGroups());
            eqBodyGroups.put(id, singleEqBGs);
        }

        forwardToEquipmentPage(req, resp, gymID, pageEquipment, eqBodyGroups, allBodyGroups, restEquipment, size);
    }

    private void doAsyncPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int size;
        int gymID = Integer.parseInt(req.getParameter(GYM_ID));
        List<Equipment> pageEquipment = new ArrayList<>();
        Map<Integer, List<String>> eqBodyGroups = new HashMap<>();

        String sFilters = req.getParameter(FILTERS);
        List<String> filters = JSON.jsonArrToList(sFilters);
        String sPageNumber = req.getParameter(PAGE_NUMBER);
        String sPageSize = req.getParameter(PAGE_SIZE);
        int pageNumber = sPageNumber != null ? Integer.parseInt(sPageNumber) : INITIAL_PAGE_NUMBER;
        int pageSize = sPageSize != null ? Integer.parseInt(sPageSize) : INITIAL_PAGE_SIZE;

        EquipmentService eqService = new EquipmentService();

        LoadedEquipment iDsForSinglePage = eqService.getIDsForSinglePage(pageNumber, pageSize, gymID, filters);
        size = iDsForSinglePage.getEquipmentNumber();
        List<Integer> equipmentIDsForSinglePage = iDsForSinglePage.getEquipmentIDsForSinglePage();
        for (Integer id : equipmentIDsForSinglePage) {
            pageEquipment.add(eqService.get(id));
            List<String> singleEqBGs = getNames(eqService.get(id).getEqBodyGroups());
            eqBodyGroups.put(id, singleEqBGs);
        }

        PrintWriter out = resp.getWriter();
        List<Object> dto = new ArrayList<>();
        dto.add(size);
        dto.addAll(getDTO(pageEquipment, eqBodyGroups));
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

    private <T> List<String> getNames(List<T> list) {
        List<String> res = new ArrayList<>();
        for (T elem : list) {
            res.add(((Accessible) elem).getName());
        }
        return res;
    }

    private void loadRest(List<Integer> existingIDs, List<EquipmentEntity> all) {
        List<EquipmentEntity> rest = new ArrayList<>(all);
        for (EquipmentEntity eq : rest) {
            for (Integer id : existingIDs) {
                if (eq.getId() == id) {
                    all.remove(eq);
                    break;
                }
            }
        }
    }
}