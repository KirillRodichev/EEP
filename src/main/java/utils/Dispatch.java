package utils;

import constants.DispatchAttrs;
import model.Equipment;
import model.entity.EquipmentEntity;
import model.entity.GymEntity;
import model.entity.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static constants.ErrorMsg.ILLEGAL_IMG_REL;

public class Dispatch {
    public static void forwardToCabinet(
            HttpServletRequest req,
            HttpServletResponse resp,
            UserEntity user,
            GymEntity gym,
            List<String> cities,
            List<String> gyms
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        req.setAttribute(DispatchAttrs.USER, user);
        req.setAttribute(DispatchAttrs.GYM, gym);
        req.setAttribute(DispatchAttrs.GYMS, gyms);
        rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }

    public static void forwardToEquipmentPage(
            HttpServletRequest req, HttpServletResponse resp, int gymID, List<EquipmentEntity> equipment,
            Map<Integer, List<String>> equipmentBodyGroups, List<String> allBodyGroups, List<EquipmentEntity> restEquipment, int size
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        if (!equipment.isEmpty()) {
            req.setAttribute(DispatchAttrs.REST_EQUIPMENT, restEquipment);
            req.setAttribute(DispatchAttrs.BODY_GROUPS, allBodyGroups);
            req.setAttribute(DispatchAttrs.EQUIPMENT, equipment);
            req.setAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP, equipmentBodyGroups);
            req.setAttribute(DispatchAttrs.GYM, gymID);
            req.setAttribute(DispatchAttrs.SIZE, size);
            rd = req.getRequestDispatcher("pages/equipment.jsp");
        } else {
            rd = req.getRequestDispatcher("pages/nosuccess.jsp");
        }
        rd.forward(req, resp);
    }

    public static void sendErrMsg(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(message);
        resp.flushBuffer();
    }
}
