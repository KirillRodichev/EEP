package servlets;

import constants.DB;
import constants.ErrorMsg;
import constants.Parameters;
import exceptions.LoginSignUpException;
import model.entity.CityEntity;
import model.entity.GymEntity;
import model.entity.UserEntity;
import services.CityService;
import services.GymService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.ErrorMsg.REQ_PARAMS_ERR;
import static utils.Dispatch.forwardToCabinet;
import static utils.Dispatch.sendErrMsg;
import static utils.JSON.stringify;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {

    private UserEntity loadUser(HttpServletRequest req, HttpServletResponse resp) throws LoginSignUpException, IOException {
        String name, email, password, mode;
        try {
            email = req.getParameter(Parameters.USER_EMAIL);
            password = req.getParameter(Parameters.USER_PASSWORD);
            name = req.getParameter(Parameters.USER_NAME);
            mode = req.getParameter(Parameters.USER_MODE);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }

        UserEntity user;
        UserService userService = new UserService();

        if (userService.getByEmail(email) == null) {
            user = new UserEntity(DB.EMPTY_FIELD, name, email, password, mode, new CityEntity(), new GymEntity());
            userService.create(user);
        } else {
            throw new LoginSignUpException(ErrorMsg.USER_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CityService cityService = new CityService();
        GymService gymService = new GymService();

        try {
            UserEntity user = loadUser(req, resp);
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
}
