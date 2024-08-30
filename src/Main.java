import config.Db;
import models.Partner;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Db db = Db.getInstance("EcoMove","postgres","soumia");
        Connection connection = db.getConnection();
//        Partner.createTable();
        Partner.createEnums();
    }
}

