package tweeter.server.factory;

import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.FeedDAO;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.StoryDAO;
import tweeter.server.dao.UserDAO;

public interface DAOFactory {


    public UserDAO makeUserDAO();

    public FollowDAO makeFollowDAO();

    public AuthTokenDAO makeAuthTokenDAO();

    public StoryDAO makeStoryDAO();

    public FeedDAO makeFeedDAO();


}
