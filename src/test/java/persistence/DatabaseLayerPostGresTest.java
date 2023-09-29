package persistence;

import org.junit.jupiter.api.BeforeEach;
import persistence.model.Tour;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseLayerPostGresTest {

    IPersistence db = new PersistencePostGres();

    @BeforeEach
    void setup() throws SQLException, FileNotFoundException {
        db.createConnection();
    }

    @Test
    void testCreateConnection() throws SQLException, FileNotFoundException {
        assertEquals(db.createConnection(), true);
    }

    @Test
    void testAddTour() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        assertNotEquals(0, db.getIdFromName("thisTourDoesNotExist"));
        assertEquals(0, db.getIdFromName("thisTourDoesNotExist2"));
        TourModel temp = new TourModel();
        temp.setTourName("thisTourDoesNotExist");
        db.removeTour(temp);
    }

    @Test
    void testRemoveTour() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        assertNotEquals(0, db.getIdFromName("thisTourDoesNotExist"));
        assertEquals(0, db.getIdFromName("thisTourDoesNotExist2"));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
        assertEquals(0, db.getIdFromName("thisTourDoesNotExist"));
    }

    @Test
    void testUpdateTourDetailsDesc() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("asdf", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getDescription).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsFrom() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("Berlin", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getStartLocation).orElse(null));

        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsTo() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("Vienna", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getEndLocation).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsTransport() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("as", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getTransportType).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsDistance() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("2134", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getDistance).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsEstTime() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("01:00", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getEstimatedTime).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsInfo() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals("asf", db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getRouteInfo).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testUpdateTourDetailsRating() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "thisTourDoesNotExist", 4);
        assertEquals(4, db.getAllTours().stream().filter(t->t.getId() == db.getMaxId()).findFirst().map(Tour::getRatings).orElse(null));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testSaveTourLogsDate() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals("01/01/1970",db.getAllTourLogs("thisTourDoesNotExist").get(0).getDate());
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testSaveTourLogsDifficulty() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setDifficulty("hard");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals("hard",db.getAllTourLogs("thisTourDoesNotExist").get(0).getDifficulty());
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testSaveTourLogsComment() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setComment("asdvf");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals("asdvf",db.getAllTourLogs("thisTourDoesNotExist").get(0).getComment());
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testSaveTourLogsRating() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setRating("dsfn");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals("dsfn",db.getAllTourLogs("thisTourDoesNotExist").get(0).getRating());
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testSaveTourLogsExists() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals(true,db.tourLogExists("01/01/1970"));
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
    @Test
    void testRemoveLogForTours() throws SQLException, FileNotFoundException {
        db.addTour("thisTourDoesNotExist");
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        log.setDate("02/01/1970");
        db.saveTourLogs(log,"thisTourDoesNotExist");
        assertEquals(2,db.getAllTourLogs("thisTourDoesNotExist").size());
        db.removeLogsForTour(db.getIdFromName("thisTourDoesNotExist"));
        assertEquals(0,db.getAllTourLogs("thisTourDoesNotExist").size());
        TourModel tm = new TourModel();
        tm.setTourName("thisTourDoesNotExist");
        db.removeTour(tm);
    }
}