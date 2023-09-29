package persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import presentation.model.TourLogCellModel;

@Entity
@Table(name = "tours_logs")
@Data
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;
    String date;
    String comment;
    String difficulty;
    @Column(name = "total_time")
    String totalTime;
    String rating;

    public static  TourLog fromTourLogCellModel(TourLogCellModel tourLogCellModel){
        TourLog tourLog = new TourLog();
        tourLog.setDate(tourLogCellModel.getDate());
        tourLog.setRating(tourLogCellModel.getRating());
        tourLog.setComment(tourLogCellModel.getComment());
        tourLog.setDifficulty(tourLogCellModel.getDifficulty());
        tourLog.setTotalTime(tourLogCellModel.getTotalTime());
        return tourLog;
    }
}
