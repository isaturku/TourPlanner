package persistence;

public class PersistenceFactory {
    private static IPersistence databaseLayer;

    //Singleton for the Database Layer
    public static IPersistence getDatabase(){
        if (databaseLayer == null) {
            databaseLayer = new PersistencePostGres();
        }
        return databaseLayer;
    }
}
