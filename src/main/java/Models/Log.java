package Models;

import lombok.Data;

@Data
public class Log {
    private int id;
    private String date;
    private String time;
    private String comment;
    private int difficulty;
    private String totaltime;
    private int rating;

    public Log(int id, String date, String time, String comment, int difficulty, String totalTime, int rating){
        this.id = id;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totaltime = totalTime;
        this.rating = rating;
    }

}


