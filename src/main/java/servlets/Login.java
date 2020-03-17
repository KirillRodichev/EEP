package servlets;

import constants.ErrorMsg;
import constants.Parameters;
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
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Utils.forwardToCabinet;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gym gym = null;
        User user = null;
        RequestDispatcher rd;
        ArrayList<String> gymsNames = new ArrayList<>();
        ArrayList<String> citiesNames = new ArrayList<>();

        UserController userController = new UserController();
        CityController cityController = new CityController();
        GymController gymController = new GymController();

        try {

            user = userController.getByEmail(req.getParameter(Parameters.USER_EMAIL));
            if (user == null) {
                throw new RuntimeException(ErrorMsg.USER_ALREADY_EXISTS);
            }

            citiesNames = (ArrayList<String>) cityController.getAll();
            ArrayList<Gym> gyms = (ArrayList<Gym>) gymController.getAll();
            for (Gym g : gyms) {
                gymsNames.add(g.getName());
            }

            int gymID = gymController.getIdByUserId(user.getId());

            gym = gymController.getById(gymID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            req.setAttribute(ErrorMsg.ERROR_MESSAGE, e.getMessage());
            rd = req.getRequestDispatcher("pages/error.jsp");
            rd.forward(req, resp);
        }

        forwardToCabinet(req, resp, user, citiesNames, gymsNames, gym);
    }
}
