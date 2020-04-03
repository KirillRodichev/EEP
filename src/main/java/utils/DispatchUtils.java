package utils;

import constants.DispatchAttrs;
import model.Gym;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DispatchUtils {

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
}
