package presentation.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourModelTest {

    TourModel tourModel = new TourModel();

    @Test
    void testTourLogAddAndClear(){
        ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();
        TourLogCellModel temp = new TourLogCellModel();
        tourLogs.add(temp);
        tourModel.setTourLogs(tourLogs);
        tourModel.clearLogs();
        ObservableList<TourLogCellModel> empty = FXCollections.observableArrayList();
        assertEquals(empty, tourModel.getTours());
    }
}