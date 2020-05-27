import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.BodyGroupController;
import model.Equipment;
import utils.JSON;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, SQLException {
        String string = null;
        String string1 = null;
        String string2 = new String();
        string2 = null;
        System.out.println(string == string1);
        System.out.println(string.equals(string1));
        System.out.println(string == string2);
        System.out.println(string.equals(string2));
    }
}
