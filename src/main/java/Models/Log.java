package Models;

import javafx.beans.property.*;
import lombok.Data;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class Log {
    private IntegerProperty id;
    private LocalDate date;
    private LocalTime time;
    private StringProperty comment;
    private IntegerProperty difficulty;
    private StringProperty totaltime;
    private IntegerProperty rating;

    public Log(int id, LocalDate date, LocalTime time, String comment, int difficulty, String totalTime, int rating){
        this.id = new SimpleIntegerProperty(id);
        this.date = date;
        this.time = time;
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totaltime = new SimpleStringProperty(totalTime);
        this.rating = new SimpleIntegerProperty(rating);
    }

}


