package utils;

import constants.DispatchAttrs;
import model.Equipment;
import model.Gym;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Dispatch {

    public static void forwardToCabinet(
            HttpServletRequest req,
            HttpServletResponse resp,
            User user,
            ArrayList<String> cities,
            ArrayList<String> gyms,
            Gym gym
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        req.setAttribute(DispatchAttrs.GYMS, gyms);
        req.setAttribute(DispatchAttrs.USER, user);
        req.setAttribute(DispatchAttrs.GYM, Objects.requireNonNullElse(gym, null));
        rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }

    public static void forwardToCabinet(
            HttpServletRequest req,
            HttpServletResponse resp,
            User user,
            ArrayList<String> cities,
            ArrayList<String> gyms
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        req.setAttribute(DispatchAttrs.CITIES, cities);
        req.setAttribute(DispatchAttrs.USER, user);
        req.setAttribute(DispatchAttrs.GYMS, gyms);
        rd = req.getRequestDispatcher("pages/cabinet.jsp");
        rd.forward(req, resp);
    }

    public static void forwardToEquipmentPage(
            HttpServletRequest req, HttpServletResponse resp, int gymID, List<Equipment> equipment,
            Map<Integer, List<String>> equipmentBodyGroups, List<String> allBodyGroups, List<Equipment> restEquipment
    ) throws ServletException, IOException {
        RequestDispatcher rd;
        if (!equipment.isEmpty()) {
            req.setAttribute(DispatchAttrs.REST_EQUIPMENT, restEquipment);
            req.setAttribute(DispatchAttrs.BODY_GROUPS, allBodyGroups);
            req.setAttribute(DispatchAttrs.EQUIPMENT, equipment);
            req.setAttribute(DispatchAttrs.EQUIPMENT_BODY_GROUP_MAP, equipmentBodyGroups);
            req.setAttribute(DispatchAttrs.GYM, gymID);
            rd = req.getRequestDispatcher("pages/equipment.jsp");
        } else {
            rd = req.getRequestDispatcher("pages/nosuccess.jsp");
        }
        rd.forward(req, resp);
    }

    public static void redirectToEquipmentPage(
            HttpServletRequest req, HttpServletResponse resp, int gymID) throws IOException {
        HttpSession session = req.getSession(false);
        session.setAttribute(DispatchAttrs.GYM_ID, gymID);
        resp.sendRedirect(req.getContextPath() + "/equipment");
    }
}
