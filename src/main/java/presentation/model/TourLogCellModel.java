package presentation.model;

import persistence.model.TourLog;
import service.ServiceFactory;
import service.IService;
import javafx.beans.property.*;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class TourLogCellModel {

    @FXML
    private StringProperty date = new SimpleStringProperty("dummy");
    @FXML
    private StringProperty comment = new SimpleStringProperty("dummy");
    @FXML
    private StringProperty difficulty = new SimpleStringProperty("dummy");
    @FXML
    private StringProperty totalTime = new SimpleStringProperty("dummy");
    @FXML
    private StringProperty rating = new SimpleStringProperty("dummy");
    public IService manager = ServiceFactory.GetManager();

    private String name;

    private BooleanProperty editMode = new SimpleBooleanProperty(false);
    private BooleanProperty workingMode = new SimpleBooleanProperty(true);

    public static TourLogCellModel fromEntity(TourLog tourLog){
        TourLogCellModel tourLogCellModel = new TourLogCellModel();
        tourLogCellModel.setDate(tourLog.getDate());
        tourLogCellModel.setDifficulty(tourLog.getDifficulty());
        tourLogCellModel.setTotalTime(tourLog.getTotalTime());
        tourLogCellModel.setComment(tourLog.getComment());
        tourLogCellModel.setRating(tourLog.getRating());
        return  tourLogCellModel;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getTotalTime() {
        return totalTime.get();
    }

    public StringProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime.set(totalTime);
    }

    public String getRating() {
        return rating.get();
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    //Delete Tour Log Item from the Listview
    public void deleteTourLog(TourLogCellModel tourLogCellModel) throws SQLException, FileNotFoundException {
        manager.deleteTourLogItem(tourLogCellModel);
    }

    public boolean getEditMode() {
        return editMode.get();
    }

    public void setEditMode(boolean value) {
        editMode.set(value);
    }

    public void setWorkMode(boolean value) {
        workingMode.set(value);
    }

    public BooleanProperty getWorkingModeProperty() {
        return workingMode;
    }

    public BooleanProperty getEditModeProperty() {
        return editMode;
    }

    public String getName() {
        return name;
    }
}
