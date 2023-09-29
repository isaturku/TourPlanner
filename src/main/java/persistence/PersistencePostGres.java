package persistence;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistence.model.Tour;
import persistence.model.TourLog;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;


public class PersistencePostGres implements IPersistence {

    private Connection con;
    private SessionFactory sessionFactory;
    Logger log = LogManager.getLogger(PersistencePostGres.class);

    public PersistencePostGres() {
    }

    @Override
    //Create connection to DB
    public boolean createConnection() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        return sessionFactory.isOpen();
    }

    @Override
    //Get last inserted Tours id
    public int getMaxId() {
        int id = 0;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Tour> res = session.createQuery("select t from Tour t where t.id = (select max(tt.id) from Tour tt)", Tour.class).list();
        id = res.get(0).getId();
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    //Get last inserted Tour Logs id
    public int getMaxIdLog() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<TourLog> res = session.createQuery("select tl from TourLog tl where tl.id = (select max(t.id) from TourLog t)", TourLog.class).list();
        int id = res.get(0).getId();
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    //Get ID of the Tour with x name
    public int getIdFromName(String name) {
        int id;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Tour> res = session.createQuery("select t from Tour t where t.name = ?1", Tour.class).setParameter(1, name).stream().findFirst();
        id = res.map(Tour::getId).orElse(0);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public Tour getTourForName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Tour> res = session.createQuery("select t from Tour t where t.name = ?1", Tour.class).setParameter(1, name).stream().findFirst();
        session.getTransaction().commit();
        session.close();
        return res.orElse(null);
    }

    public Tour getTourForId(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Tour> res = session.createQuery("select t from Tour t where t.id = ?1", Tour.class).setParameter(1, id).stream().findFirst();
        session.getTransaction().commit();
        session.close();
        return res.orElse(null);
    }


    @Override
    //Remove Tour
    public void removeTour(TourModel tourModel) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete Tour t where t.name = :name").setParameter("name", tourModel.getTourName()).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    //Update Tour Details after Save button is clicked
    public void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update Tour t SET description = :desc, startLocation = :start, endLocation = :end, transportType = :tt, distance = :dist, estimatedTime = :eta, routeInfo = :ri, ratings = :r  WHERE name = :name").setParameter("desc", tourDesc).setParameter("start", tourFrom).setParameter("end", tourTo).setParameter("tt", tourTransport).setParameter("dist", tourDistance).setParameter("eta", tourEstTime).setParameter("ri", tourInfo).setParameter("r", tourRating).setParameter("name", tourName).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    //Update Tour Logs after Save button on Log view is clicked
    public void updateTourLog(TourLogCellModel item, String tourModelName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update TourLog t SET comment = :comment, difficulty = :difficulty, totalTime = :tt, rating = :r WHERE date = :d").setParameter("comment", item.getComment()).setParameter("difficulty", item.getDifficulty()).setParameter("tt", item.getTotalTime()).setParameter("r", item.getRating()).setParameter("d", item.getDate()).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    //Remove Tour Log (the one who is removed from the ListView)
    public void removeTourLog(TourLogCellModel tourLogCellModel) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(TourLog.fromTourLogCellModel(tourLogCellModel));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeLogsForTour(int tourId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tour tour = getTourForId(tourId);
        tour.getLogs().forEach(session::remove);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    //Get all Tour Logs for each Tour
    public List<TourLog> getAllTourLogs(String tourName) {
        return getTourForName(tourName).getLogs();
    }

    @Override
    //Get all Tours
    public List<Tour> getAllTours() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Tour> res = session.createQuery("SELECT t from Tour t", Tour.class).list();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    @Override
    //Get all Tour Names
    public List<String> getAllToursNames() {
        return getAllTours().stream().map(Tour::getName).toList();
    }

    @Override
    //Save all TourLogs
    public void saveTourLogs(TourLogCellModel item, String tourModelName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TourLog tourLog = TourLog.fromTourLogCellModel(item);
        tourLog.setTour(getTourForName(tourModelName));
        session.persist(tourLog);
        session.getTransaction().commit();
        session.close();
    }

    //Check if Tour Log exists
    public boolean tourLogExists(String date) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<TourLog> res = session.createQuery("SELECT t from TourLog t where t.date = :date", TourLog.class).setParameter("date",date).list();
        session.getTransaction().commit();
        session.close();
        return res.size() > 0;
    }

    @Override
    //Add Tour
    public void addTour(String tour) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tour tourObject = new Tour(tour);
        tourObject.setName(tour);
        session.persist(tourObject);
        session.getTransaction().commit();
        session.close();
    }
}
