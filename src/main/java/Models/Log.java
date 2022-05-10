package Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class Log {
    private IntegerProperty id;
    private StringProperty date;
    private StringProperty time;
    private StringProperty comment;
    private IntegerProperty difficulty;
    private StringProperty totaltime;
    private IntegerProperty rating;

    public Log(int id, String date, String time, String comment, int difficulty, String totalTime, int rating){
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totaltime = new SimpleStringProperty(totalTime);
        this.rating = new SimpleIntegerProperty(rating);
    }

}


