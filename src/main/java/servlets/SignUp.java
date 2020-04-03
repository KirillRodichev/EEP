package servlets;

import constants.*;
import controllers.CityController;
import controllers.GymController;
import controllers.UserController;
import model.Gym;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static utils.Dispatch.*;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {

    private User loadUser(HttpServletRequest req, UserController userController)
            throws SQLException, RuntimeException {
        User user;
        if (userController.getByEmail(req.getParameter(Parameters.USER_EMAIL)) == null) {
            user = new User(
                    DB.EMPTY_FIELD,
                    req.getParameter(Parameters.USER_NAME),
                    req.getParameter(Parameters.USER_EMAIL),
                    req.getParameter(Parameters.USER_PASSWORD),
                    req.getParameter(Parameters.USER_MODE)
            );
            userController.create(user);
        } else {
            throw new RuntimeException(ErrorMsg.USER_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        RequestDispatcher rd;
        ArrayList<String> gymsNames = new ArrayList<>();
        ArrayList<String> citiesNames = new ArrayList<>();

        UserController userController = new UserController();
        CityController cityController = new CityController();
        GymController gymController = new GymController();
        try {

            user = loadUser(req, userController);

            citiesNames = (ArrayList<String>) cityController.getAll();
            ArrayList<Gym> gyms = (ArrayList<Gym>) gymController.getAll();
            for (Gym g : gyms) {
                gymsNames.add(g.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            req.setAttribute(ErrorMsg.ERROR_MESSAGE, e.getMessage());
            rd = req.getRequestDispatcher("pages/error.jsp");
            rd.forward(req, resp);
        }

        forwardToCabinet(req, resp, user, citiesNames, gymsNames);
    }
}
