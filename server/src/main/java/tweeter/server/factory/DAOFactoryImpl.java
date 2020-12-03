package tweeter.server.factory;

import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.FeedDAO;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.StoryDAO;
import tweeter.server.dao.UserDAO;

public class DAOFactoryImpl implements DAOFactory {

    private static DAOFactoryImpl daoFactory;

    public static DAOFactory getDAOFactory() {
        if(daoFactory == null) {
            daoFactory = new DAOFactoryImpl();
            return daoFactory;
        } else {
            return daoFactory;
        }
    }

    @Override
    public UserDAO makeUserDAO() {
        return new UserDAO();
    }

    @Override
    public FollowDAO makeFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public AuthTokenDAO makeAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    @Override
    public StoryDAO makeStoryDAO() {return new StoryDAO();}

    @Override
    public FeedDAO makeFeedDAO() {return new FeedDAO();}

}
