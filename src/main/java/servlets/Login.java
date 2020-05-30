package servlets;

import constants.Parameters;
import exceptions.LoginSignUpException;
import model.entity.CityEntity;
import model.entity.GymEntity;
import model.entity.UserEntity;
import services.CityService;
import services.GymService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.ErrorMsg.*;
import static utils.Dispatch.forwardToCabinet;
import static utils.Dispatch.sendErrMsg;
import static utils.JSON.stringify;

@MultipartConfig
@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email, password;
        try {
            email = req.getParameter(Parameters.USER_EMAIL);
            password = req.getParameter(Parameters.USER_PASSWORD);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }

        UserService userService = new UserService();
        GymService gymService = new GymService();
        CityService cityService = new CityService();

        try {
            UserEntity user = userService.getByEmail(email);
            validate(user, password);

            List<CityEntity> cities = cityService.getAll();
            List<String> gymNames = gymService.getAllNames();
            List<String> cityNames = new ArrayList<>();
            for (CityEntity city : cities) {
                cityNames.add(city.getName());
            }
            final int gymID = gymService.getIdByUserId(user.getId());
            GymEntity gym = gymService.get(gymID);

            forwardToCabinet(req, resp, user, gym, cityNames, gymNames);

        } catch (LoginSignUpException e) {
            resp.getWriter().print(stringify(e.getMessage()));
        }
    }

    private void validate(UserEntity user, String password)
            throws LoginSignUpException {
        if (user != null) {
            String dbPassword = new UserService().getPassword(user.getId());
            if (password != null) {
                if (!dbPassword.equals(password)) {
                    throw new LoginSignUpException(WRONG_PASSWORD);
                }
            } else {
                throw new LoginSignUpException(WRONG_PASSWORD);
            }
        } else {
            throw new LoginSignUpException(USER_NOT_FOUND);
        }
    }
}
