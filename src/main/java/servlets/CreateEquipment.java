package servlets;

import constants.DB;
import constants.DispatchAttrs;
import constants.Parameters;
import exceptions.IllegalImageRelation;
import model.entity.BodyGroupEntity;
import model.entity.EquipmentEntity;
import model.entity.GymEntity;
import services.BodyGroupService;
import services.EquipmentService;
import services.GymService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static constants.DispatchAttrs.EQUIPMENT;
import static constants.ErrorMsg.CREATE_EXEC_ERR;
import static constants.ErrorMsg.REQ_PARAMS_ERR;
import static utils.Dispatch.sendErrMsg;
import static utils.FileU.imgUpload;
import static utils.JSON.jsonArrToSet;

@WebServlet("/createEquipment")
@MultipartConfig
public class CreateEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gymID;
        String name, description, imgName;
        Set<String> bgIDs;
        Part filePart;
        try {
            gymID = Integer.parseInt(req.getParameter(DispatchAttrs.GYM_ID));
            name = req.getParameter(Parameters.EQUIPMENT_NAME);
            description = req.getParameter(Parameters.EQUIPMENT_DESCRIPTION);
            bgIDs = jsonArrToSet(req.getParameter(Parameters.BODY_GROUPS));
            filePart = req.getPart(Parameters.EQUIPMENT_IMG_FILE);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }
        try {
            imgName = imgUpload(filePart, EQUIPMENT);
        } catch (IllegalImageRelation e) {
            sendErrMsg(resp, e.getMessage());
            throw new RuntimeException(e);
        }

        List<GymEntity> eqGyms = new ArrayList<>();
        List<BodyGroupEntity> eqBodyGroups = new ArrayList<>();
        BodyGroupService bgService = new BodyGroupService();
        GymService gymService = new GymService();
        EquipmentService eService = new EquipmentService();

        eqGyms.add(gymService.get(gymID));
        if (bgIDs != null) {
            for (String bgID : bgIDs) {
                eqBodyGroups.add(bgService.get(Integer.parseInt(bgID)));
            }
        }

        EquipmentEntity equipment = new EquipmentEntity(DB.EMPTY_FIELD, name, description, imgName, eqGyms, eqBodyGroups);

        for (BodyGroupEntity bg : eqBodyGroups) {
            List<EquipmentEntity> bgEq = new ArrayList<>();
            bgEq.add(equipment);
            bg.setBgEquipment(bgEq);
        }
        try {
            eService.create(equipment);
        } catch (RuntimeException e) {
            sendErrMsg(resp, CREATE_EXEC_ERR);
        }
    }
}