package Models;

import lombok.Data;

@Data
public class Log {
    public String date;
    public String time;
    public String comment;
    public int difficulty;
    public String totaltime;
    public int rating;

    public Log(String date, String time, String comment, int difficulty, String totalTime, int rating){
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totaltime = totalTime;
        this.rating = rating;
    }

}


