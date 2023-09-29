package service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.IPersistence;
import persistence.PersistenceFactory;
import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ServiceImp implements IService {

    //DB Layer
    private IPersistence dataLayer;
    //MapQuest
    private MapQuestManager map = new MapQuestManager();
    Logger log = LogManager.getLogger(ServiceImp.class);

    public ServiceImp() {
        dataLayer = PersistenceFactory.getDatabase();
    }

    @Override
    public void createTourItem(TourEntryModel tourItem) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.addTour(tourItem.getTourName());
    }

    @Override
    public void deleteTourItem(TourModel tourModel) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.removeTour(tourModel);
    }

    @Override
    public void deleteTourLogItem(TourLogCellModel tourLogCellModel) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.removeTourLog(tourLogCellModel);
    }

    @Override
    public boolean getMap(String tourName, String start, String finish) throws IOException {
        try {
            File outputFile = new File("src/main/resources/TourImages/" + tourName + ".jpg");
            BufferedImage img = MapQuestManager.requestRouteImage(start, finish);
            if (img != null) {
                log.info("Img is saved!");
                ImageIO.write(img, "jpg", outputFile);
                return true;
            } else {
                log.error("Could not save img for this Tour!");
                return false;
            }
        } catch (Exception e) {
            log.error("Could not save img for this Tour!");
            return false;
        }
    }

    @Override
    public String getRouteDistance(String start, String end) {
        return map.getRouteDistance(start, end);
    }

    @Override
    public void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.updateTourDetails(tourDesc, tourFrom, tourTo, tourTransport, tourDistance, tourEstTime, tourInfo, tourName, tourRating);
    }

    @Override
    public ObservableList<TourModel> getAllTour() throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        return FXCollections.observableArrayList(dataLayer.getAllTours().stream().map(TourModel::fromEntity).toList());
    }

    @Override
    public List<String> getAllTourNames() throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        return dataLayer.getAllToursNames();
    }

    @Override
    public void saveTourLogs(TourLogCellModel tourLogs, String tourModelName) {
        try {
            this.dataLayer.createConnection();
            this.dataLayer.saveTourLogs(tourLogs, tourModelName);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<TourLogCellModel> getAllTourLogs(String tourName) {
        try {
            this.dataLayer.createConnection();
            return FXCollections.observableArrayList(this.dataLayer.getAllTourLogs(tourName).stream().map(TourLogCellModel::fromEntity).toList()) ;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void savePDF(TourModel selectedTour) {
        Document doc = new Document(PageSize.A4);
        try {
            final PdfWriter instance = PdfWriter.getInstance(doc, new FileOutputStream(selectedTour.getTourName() + ".pdf"));
            doc.open();
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(18);

            Paragraph title = new Paragraph(selectedTour.getTourName(), fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(title);

            Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(14);

            Paragraph subtitle = new Paragraph(selectedTour.getTourDesc(), fontSubtitle);
            subtitle.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            doc.add(subtitle);

            Font fontInfo = FontFactory.getFont(FontFactory.HELVETICA);
            fontTitle.setSize(12);

            Paragraph fromText = new Paragraph("From: " + selectedTour.getTourFrom(), fontInfo);
            doc.add(fromText);
            Paragraph toText = new Paragraph("To: " + selectedTour.getTourTo(), fontInfo);
            doc.add(toText);
            Paragraph transportText = new Paragraph("Transport: " + selectedTour.getTourTransport(), fontInfo);
            doc.add(transportText);
            Paragraph distanceText = new Paragraph("Distance: " + selectedTour.getTourDistance(), fontInfo);
            doc.add(distanceText);
            Paragraph estText = new Paragraph("Estimated Time: " + selectedTour.getTourEstTime(), fontInfo);
            doc.add(estText);
            Paragraph infoText = new Paragraph("Info: " + selectedTour.getTourInfo(), fontInfo);
            doc.add(infoText);
            Paragraph ratingText = new Paragraph("Rating: " + selectedTour.getTourRating(), fontInfo);
            doc.add(ratingText);
            Paragraph logTitle = new Paragraph("Logs", fontTitle);
            logTitle.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(logTitle);
            Table table = new Table(5);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 0));
            table.setPadding(5);
            table.setSpacing(5);
            Cell dateHeader = new Cell("Date");
            dateHeader.setHeader(true);
            table.addCell(dateHeader);
            Cell commentHeader = new Cell("Comment");
            commentHeader.setHeader(true);
            table.addCell(commentHeader);
            Cell difficultyHeader = new Cell("Difficulty");
            difficultyHeader.setHeader(true);
            table.addCell(difficultyHeader);
            Cell timeHeader = new Cell("Total Time");
            timeHeader.setHeader(true);
            table.addCell(timeHeader);
            Cell ratingHeader = new Cell("Rating");
            ratingHeader.setHeader(true);
            table.addCell(ratingHeader);
            table.endHeaders();
            for (TourLogCellModel log : selectedTour.getTours()) {
                Cell dateCell = new Cell(log.getDate());
                table.addCell(dateCell);
                Cell commentCell = new Cell(log.getComment());
                table.addCell(commentCell);
                Cell difficultyCell = new Cell(log.getDifficulty());
                table.addCell(difficultyCell);
                Cell timeCell = new Cell(log.getTotalTime());
                table.addCell(timeCell);
                Cell ratingCell = new Cell(log.getRating());
                table.addCell(ratingCell);
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

