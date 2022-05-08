package DataAccessLayer;

import lombok.Data;

@Data
public class DbConfig {
    private String database = "tourplanner";
    private String user = "postgres";
    private String password = "Globuli48";
}
