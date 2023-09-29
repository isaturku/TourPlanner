package presentation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.model.Tour;

import java.io.File;
import java.util.Iterator;


public class TourModel {
    @FXML
    private StringProperty tourName = new SimpleStringProperty("");
    @FXML
    private StringProperty tourDesc = new SimpleStringProperty("");
    @FXML
    private StringProperty tourFrom = new SimpleStringProperty("");
    @FXML
    private StringProperty tourTo = new SimpleStringProperty("");
    @FXML
    private StringProperty tourTransport = new SimpleStringProperty("");
    @FXML
    private StringProperty tourDistance = new SimpleStringProperty("");
    @FXML
    private StringProperty tourEstTime = new SimpleStringProperty("");
    @FXML
    private StringProperty tourInfo = new SimpleStringProperty("");
    @FXML
    private IntegerProperty tourRating = new SimpleIntegerProperty(0);

    private ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();

    public static TourModel From(TourEntryModel source) {
        var newInstance = new TourModel();
        newInstance.tourName.set(source.getTourName());
        return newInstance;
    }

    public static TourModel fromEntity(Tour t){
        TourModel tm = new TourModel();
        tm.setTourName(t.getName());
        tm.setTourDesc(t.getDescription());
        tm.setTourDistance(t.getDistance());
        tm.setTourFrom(t.getStartLocation());
        tm.setTourInfo(t.getRouteInfo());
        tm.setTourEstTime(t.getEstimatedTime());
        tm.setTourRating(t.getRatings());
        tm.setTourTransport(t.getTransportType());
        return tm;
    }

    public String getTourTransport() {
        return tourTransport.get();
    }

    public String getTourDistance() {
        return tourDistance.get();
    }

    public String getTourEstTime() {
        return tourEstTime.get();
    }

    public void setTourTransport(String tourTransport) {
        this.tourTransport.set(tourTransport);
    }

    public void setTourDistance(String tourDistance) {
        this.tourDistance.set(tourDistance);
    }

    public void setTourEstTime(String tourEstTime) {
        this.tourEstTime.set(tourEstTime);
    }

    public void setTourInfo(String tourInfo) {
        this.tourInfo.set(tourInfo);
    }

    public String getTourInfo() {
        return tourInfo.get();
    }

    public String getTourName() {
        return tourName.get();
    }

    public void setTourName(String name){
        this.tourName.set(name);
    }

    public StringProperty getTourNameProperty() {
        return tourName;
    }

    public String getTourDesc() {
        return tourDesc.get();
    }

    public void setTourDesc(String tourDesc) {
        this.tourDesc.set(tourDesc);
    }

    public String getTourFrom() {
        return tourFrom.get();
    }

    public String getTourTo() {
        return tourTo.get();
    }

    public void setTourFrom(String tourFrom) {
        this.tourFrom.set(tourFrom);
    }

    public void setTourTo(String tourTo) {
        this.tourTo.set(tourTo);
    }

    protected void clearLogs(){
        this.tourLogs.clear();
    }

    public ObservableList<TourLogCellModel> getTours() {
        return tourLogs;
    }

    public int getTourRating() {
        return this.tourRating.get();
    }

    public void setTourRating(int tourRating) {
        this.tourRating.set(tourRating);
    }

    public void setTourLogs(ObservableList<TourLogCellModel> tourLogs){
        clearLogs();

        Iterator<TourLogCellModel> it = tourLogs.iterator();
        while (it.hasNext()) {
            TourLogCellModel value = it.next();
            this.tourLogs.add(value);
        }
    }

    //Delete Img for this Tour
    public void deleteImg(String img){
        Logger log = LogManager.getLogger(TourModel.class);
        File myObj = new File(img);
        if (!myObj.exists() || myObj.delete()) {
            log.info(myObj.getName() + " is deleted");
        } else {
            log.error("Failed to delete the file.");
        }
    }
}
