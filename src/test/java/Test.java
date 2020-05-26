import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Equipment;
import utils.JSON;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        List<Integer> l = JSON.jsonArrToList(null);
    }
}
