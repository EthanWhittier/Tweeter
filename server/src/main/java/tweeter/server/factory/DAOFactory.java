package tweeter.server.factory;

import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.UserDAO;

public interface DAOFactory {


    public UserDAO makeUserDAO();

    public FollowDAO makeFollowDAO();

    public AuthTokenDAO makeAuthTokenDAO();


}
