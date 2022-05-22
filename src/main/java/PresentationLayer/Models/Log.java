package PresentationLayer.Models;

import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class Log {
    private int id;
    private LocalDate date;
    private LocalTime time;
    private String comment;
    private int difficulty;
    private String totaltime;
    private Integer rating;

    public Log(int id, LocalDate date, LocalTime time, String comment, int difficulty, String totalTime, int rating){
        this.id = id;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totaltime = totalTime;
        this.rating = rating;
    }

}


