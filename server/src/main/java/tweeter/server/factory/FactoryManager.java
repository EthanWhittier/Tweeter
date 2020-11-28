package tweeter.server.factory;

public class FactoryManager {

    public static DAOFactory getDAOFactory() {
        return DAOFactoryImpl.getDAOFactory();
    }


}
