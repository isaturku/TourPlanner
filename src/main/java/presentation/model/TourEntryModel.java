package presentation.model;

import service.IService;
import service.ServiceFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class TourEntryModel {

    private IService manager = ServiceFactory.GetManager();
    private StringProperty tourName;

    public TourEntryModel(){
        tourName = new SimpleStringProperty("");
    }

    public String getTourName() {
        return tourName.get();
    }

    public StringProperty getTourNameProperty() {
        return tourName;
    }

    //When add is clicked, the new Tour will be added to the DB
    public boolean createTour(TourEntryModel tourEntryModel) throws SQLException, IOException {
        manager.createTourItem(tourEntryModel);
        Logger log = LogManager.getLogger(TourEntryModel.class);
        log.info("Tour " + getTourName() + " is added (to the DB as well)");
        return true;
    }
}
