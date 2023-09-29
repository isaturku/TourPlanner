package presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogModel {

    private StringProperty tourLog;
    private ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();
    private TourModel tourModel;

    public TourLogModel(){
        tourLog = new SimpleStringProperty("Log view");
    }

    public StringProperty getTourLogProperty() {
        return tourLog;
    }

    public ObservableList<TourLogCellModel> getTourLogs() {
        return tourLogs;
    }

    public String getTourModelName(){
        return this.tourModel.getTourName();
    }

    private void clearLogs(){
        tourLogs.clear();
    }

    public ObservableList<TourLogCellModel> getTours() {
        return tourLogs;
    }

    public void removeTour(TourLogCellModel product) {
        this.tourLogs.remove(product);
    }

    public void setTourModel(TourModel tourModelList) {
        //clear listview
        clearLogs();
        //save tourLogs from Listview to LogListView
        setLogModel(tourModelList);
    }

    //set ListView to LogListView
    private void setLogModel(TourModel tourModel) {
        tourModel.getTours().forEach(tourLog -> {
            tourLogs.add(tourLog);
        });
        this.tourModel = tourModel;
    }

    //save ListView to be the Same as the modified LogListView
    public void saveTourModel() {
        if(this.tourModel != null){
            this.tourModel.setTourLogs(getTours());
        }
    }
}
