package servlets;

import constants.DispatchAttrs;
import services.EquipmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.ErrorMsg.REMOVE_EXEC_ERR;
import static constants.ErrorMsg.REQ_PARAMS_ERR;
import static utils.Dispatch.sendErrMsg;

@WebServlet("/removeEquipment")
@MultipartConfig
public class RemoveEquipment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int equipmentID, gymID;
        try {
            equipmentID = Integer.parseInt(req.getParameter(DispatchAttrs.EQUIPMENT_ID));
            gymID = Integer.parseInt(req.getParameter(DispatchAttrs.GYM_ID));
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }

        EquipmentService equipmentService = new EquipmentService();

        try {
            equipmentService.remove(gymID, equipmentID);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REMOVE_EXEC_ERR);
            throw new RuntimeException(e);
        }
    }
}
