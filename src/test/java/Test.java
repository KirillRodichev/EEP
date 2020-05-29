import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.BodyGroupController;
import model.Equipment;
import model.entity.CityEntity;
import model.entity.GymEntity;
import services.CityService;
import utils.JSON;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, SQLException {
       /* CityService cs = new CityService();

        CityEntity city = cs.getCity(2);

        List<CityEntity> allCities = cs.getAllCities();

        System.out.println(city.getName());
        for (CityEntity c : allCities) {
            System.out.println(c.getName());
        }*/

       String s = getObj(new GymEntity());
        System.out.println(s);

        //https://stackoverflow.com/questions/12192592/java-sql-sqlexception-ora-01000-maximum-open-cursors-exceeded
    }

    public static <T> String getObj(T obj) {
        return obj.getClass().getSimpleName();
    }
}