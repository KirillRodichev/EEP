package servlets;

import constants.Parameters;
import model.entity.BodyGroupEntity;
import model.entity.EquipmentEntity;
import services.BodyGroupService;
import services.EquipmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constants.DispatchAttrs.EQUIPMENT;
import static constants.ErrorMsg.MERGE_EXEC_ERR;
import static constants.ErrorMsg.REQ_PARAMS_ERR;
import static utils.DB.ableToUpdate;
import static utils.Dispatch.sendErrMsg;
import static utils.FileU.getImgName;

@MultipartConfig
@WebServlet("/updateEquipment")
public class UpdateEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int equipmentID;
        String name, description, imgName;
        List<String> bgIDs;
        try {
            equipmentID = Integer.parseInt(req.getParameter(Parameters.EQUIPMENT_ID));
            name = req.getParameter(Parameters.EQUIPMENT_NAME);
            description = req.getParameter(Parameters.EQUIPMENT_DESCRIPTION);
            bgIDs = getBGIDs(req);
            imgName = getImgName(req.getPart(Parameters.EQUIPMENT_IMG_FILE), EQUIPMENT);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }

        EquipmentService equipmentService = new EquipmentService();
        BodyGroupService bgService = new BodyGroupService();
        List<BodyGroupEntity> bodyGroups = new ArrayList<>();

        EquipmentEntity entity = equipmentService.get(equipmentID);
        if (bgIDs != null) {
            for (String bgID : bgIDs) {
                bodyGroups.add(bgService.get(Integer.parseInt(bgID)));
            }
        }
        EquipmentEntity equipment = new EquipmentEntity();
        fillFields(equipment, entity, name, description, imgName, bodyGroups);

        try {
            equipmentService.merge(equipment);
        } catch (RuntimeException e) {
            sendErrMsg(resp, MERGE_EXEC_ERR);
            throw new RuntimeException(e);
        }
    }

    private List<String> getBGIDs(HttpServletRequest req) {
        List<String> bgIDs;
        try {
            bgIDs = new ArrayList<>(Arrays.asList(req.getParameterValues(Parameters.BODY_GROUPS)));
        } catch (RuntimeException e) {
            bgIDs = null;
        }
        return bgIDs;
    }

    private void fillFields(
            EquipmentEntity equipment, EquipmentEntity entity,
            String name, String description, String imgName, List<BodyGroupEntity> bodyGroups
    ) {
        equipment.setId(entity.getId());
        if (ableToUpdate(name))
            equipment.setName(name);
        else
            equipment.setName(entity.getName());
        if (ableToUpdate(description))
            equipment.setDescription(description);
        else
            equipment.setDescription(entity.getDescription());
        if (ableToUpdate(imgName))
            equipment.setImgPath(imgName);
        else
            equipment.setImgPath(entity.getImgPath());
        if (ableToUpdate(bodyGroups))
            equipment.setBodyGroups(bodyGroups);
        else
            equipment.setBodyGroups(entity.getBodyGroups());
        equipment.setEqGyms(entity.getEqGyms());
    }
}
