package presentation.controller;

import javafx.scene.control.ListView;
import presentation.model.TourModel;
import service.ServiceFactory;
import service.IService;
import presentation.model.TourSearchModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourSearchController implements Initializable {

    @FXML
    private TextField searchInput;
    @FXML
    private Button pdfButton;
    @FXML
    private IService manager = ServiceFactory.GetManager();
    private TourSearchModel tourSearchModel;

    private static final ControllerFactory controllerFactory = new ControllerFactory();


    public TourSearchController(TourSearchModel tourSearchModel) {
        this.tourSearchModel = tourSearchModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Bind text value
        this.searchInput.textProperty().bindBidirectional(this.tourSearchModel.getSearchProperty());
    }

    //Save PDF button
    public void savePDF(ActionEvent actionEvent) throws Exception {
        TourListController tourListController = (TourListController) controllerFactory.create(TourListController.class);
        System.out.println(tourListController.getSelectedTour().getTourName());
        manager.savePDF(tourListController.getSelectedTour());
    }

    public void searchTours(ActionEvent actionEvent) {
    }
}
