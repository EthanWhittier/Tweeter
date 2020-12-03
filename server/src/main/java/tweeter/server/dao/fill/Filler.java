package tweeter.server.dao.fill;

import java.util.ArrayList;
import java.util.List;

import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;

public class Filler {



    private static int NUM_USERS = 10000;

    private static String FOLLOW_TARGET = "eiwhitt";


    public static void fillDatabase() {
        // Get instance of DAOs by way of the Abstract Factory Pattern
        UserDAO userDAO = FactoryManager.getDAOFactory().makeUserDAO();
        FollowDAO followDAO = FactoryManager.getDAOFactory().makeFollowDAO();

        List<String> followers = new ArrayList<>();
        List<UserDTO> users = new ArrayList<>();

        // Iterate over the number of users you will create
        for (int i = 1; i <= NUM_USERS; i++) {

            String firstName = "Guy " + i;
            String lastName = "Last " + i;
            String alias = "guy" + i;

            // Note that in this example, a UserDTO only has a name and an alias.
            // The url for the profile image can be derived from the alias in this example
            UserDTO user = new UserDTO();
            user.setAlias(alias);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            users.add(user);

            // Note that in this example, to represent a follows relationship, only the aliases
            // of the two users are needed
            followers.add(alias);
        }

        // Call the DAOs for the database logic
        if (users.size() > 0) {
            userDAO.addUserBatch(users);
        }
        if (followers.size() > 0) {
            followDAO.addFollowersBatch(followers, FOLLOW_TARGET);
        }
    }









}
