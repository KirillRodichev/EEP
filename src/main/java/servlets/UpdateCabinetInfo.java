package servlets;

import constants.DB;
import controllers.CityController;
import lombok.SneakyThrows;
import model.Gym;
import model.entity.CityEntity;
import model.entity.GymEntity;
import services.CityService;
import services.GymService;

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

import static constants.DispatchAttrs.GYM;
import static constants.ErrorMsg.MERGE_EXEC_ERR;
import static constants.ErrorMsg.REQ_PARAMS_ERR;
import static constants.Parameters.*;
import static utils.Dispatch.sendErrMsg;
import static utils.FileU.getImgName;
import static utils.JSON.stringify;

@MultipartConfig
@WebServlet("/updateCabinetInfo")
public class UpdateCabinetInfo extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id, cityID;
        String name, imgName, websiteURL, website, phone, address;
        try {
            id = Integer.parseInt(req.getParameter(GYM_ID));
            name = req.getParameter(GYM_NAME);
            imgName = getImgName(req.getPart(GYM_LOGO_PATH), GYM);
            websiteURL = req.getParameter(GYM_WEBSITE_URL);
            website = req.getParameter(GYM_WEBSITE);
            phone = req.getParameter(GYM_PHONE);
            address = req.getParameter(GYM_ADDRESS);
        } catch (RuntimeException e) {
            sendErrMsg(resp, REQ_PARAMS_ERR);
            throw new RuntimeException(e);
        }
        try {
            cityID = Integer.parseInt(req.getParameter(CITY));
        } catch (RuntimeException e) {
            cityID = DB.EMPTY_FIELD;
        }

        GymService gymService = new GymService();
        CityService cityService = new CityService();

        GymEntity prevGym = gymService.get(id);
        CityEntity city = cityID == DB.EMPTY_FIELD
                ? cityService.getByGymID(id)
                : cityService.get(cityID);

        Gym gym = new Gym(id, name, imgName, websiteURL, website, phone, address);
        GymEntity curEntity = new GymEntity(id, name, imgName, websiteURL, website, phone, address, null, city, null);

        try {
            gymService.merge(prevGym, curEntity);
        } catch (RuntimeException e) {
            sendErrMsg(resp, MERGE_EXEC_ERR);
            throw new RuntimeException(e);
        }

        if (imgName == null) gym.setLogoPath("");
        String sCity = new CityController().getById(cityID);

        PrintWriter out = resp.getWriter();
        List<Object> dto = new ArrayList<>();
        dto.add(sCity);
        dto.add(gym);

        String json = stringify(dto);

        out.print(json);
    }
}
