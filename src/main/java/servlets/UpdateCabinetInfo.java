package servlets;

import constants.DB;
import controllers.CityController;
import controllers.GymController;
import controllers.UserController;
import lombok.SneakyThrows;
import model.Gym;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static constants.Parameters.*;
import static utils.DTO.getDTO;
import static utils.JSON.stringify;

@MultipartConfig
@WebServlet("/updateCabinetInfo")
public class UpdateCabinetInfo extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int id = Integer.parseInt(req.getParameter(GYM_ID));
        final String name = req.getParameter(GYM_NAME);
        final String logoPath = req.getParameter(GYM_LOGO_PATH);
        final String websiteURL = req.getParameter(GYM_WEBSITE_URL);
        final String website = req.getParameter(GYM_WEBSITE);
        final String phone = req.getParameter(GYM_PHONE);
        final String address = req.getParameter(GYM_ADDRESS);
        int city;
        try {
            city = Integer.parseInt(req.getParameter(CITY));
        } catch (RuntimeException e) {
            city = DB.EMPTY_FIELD;
        }

        GymController gController = new GymController();

        Gym gym = new Gym(id, name, logoPath, websiteURL, website, phone, address);

        gController.updateCity(city, id);
        gController.update(gym);

        if (logoPath == null) gym.setLogoPath("");
        String sCity = new CityController().getById(city);

        PrintWriter out = resp.getWriter();
        List<Object> dto = new ArrayList<>();
        dto.add(sCity);
        dto.add(gym);

        String json = stringify(dto);

        out.print(json);
    }
}
