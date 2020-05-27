package servlets;

import constants.*;
import controllers.CityController;
import controllers.GymController;
import controllers.UserController;
import exceptions.LoginSignUpException;
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
import java.util.List;

import static utils.Dispatch.*;
import static utils.JSON.stringify;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {

    private User loadUser(HttpServletRequest req) throws SQLException, LoginSignUpException {
        final String name = req.getParameter(Parameters.USER_NAME);
        final String email = req.getParameter(Parameters.USER_EMAIL);
        final String password = req.getParameter(Parameters.USER_PASSWORD);
        final String mode = req.getParameter(Parameters.USER_MODE);

        User user;
        UserController userController = new UserController();

        if (userController.getByEmail(email) == null) {
            user = new User(DB.EMPTY_FIELD, name, email, password, mode);
            userController.create(user);
        } else {
            throw new LoginSignUpException(ErrorMsg.USER_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CityController cityController = new CityController();
        GymController gymController = new GymController();

        try {
            User user = loadUser(req);
            List<String> citiesNames = cityController.getAll();
            List<String> gymsNames = gymController.getAllNames();

            forwardToCabinet(req, resp, user, citiesNames, gymsNames);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (LoginSignUpException e) {
            resp.getWriter().print(stringify(e.getMessage()));
        }
    }
}
