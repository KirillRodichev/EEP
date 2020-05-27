package servlets;

import constants.Parameters;
import controllers.CityController;
import controllers.GymController;
import controllers.UserController;
import exceptions.LoginSignUpException;
import model.Gym;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static constants.ErrorMsg.USER_NOT_FOUND;
import static constants.ErrorMsg.WRONG_PASSWORD;
import static utils.Dispatch.forwardToCabinet;
import static utils.JSON.stringify;

@MultipartConfig
@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String email = req.getParameter(Parameters.USER_EMAIL);
        final String password = req.getParameter(Parameters.USER_PASSWORD);

        UserController userController = new UserController();
        CityController cityController = new CityController();
        GymController gymController = new GymController();

        try {
            User user = userController.getByEmail(email);
            validate(user, password, userController);

            List<String> cityNames = cityController.getAll();
            List<String> gymNames = gymController.getAllNames();

            final int gymID = gymController.getIdByUserId(user.getId());
            Gym gym = gymController.getById(gymID);

            forwardToCabinet(req, resp, user, cityNames, gymNames, gym);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (LoginSignUpException e) {
            resp.getWriter().print(stringify(e.getMessage()));
        }
    }

    private void validate(User user, String password, UserController userController)
            throws SQLException, LoginSignUpException {
        if (user != null) {
            String dbPassword = userController.getPassword(user.getId());
            if (dbPassword != password) {
                throw new LoginSignUpException(WRONG_PASSWORD);
            }
        } else {
            throw new LoginSignUpException(USER_NOT_FOUND);
        }
    }
}
