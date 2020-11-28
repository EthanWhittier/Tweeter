package tweeter.server.service;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.RegisterServiceInterface;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.dao.S3Bucket;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;


public class RegisterService implements RegisterServiceInterface {

    @Override
    public RegisterResponse register(RegisterRequest request) throws TweeterRemoteException {
        RegisterResponse response;
        try {
            response = getUserDAO().register(request);
            getS3Bucket().storeProfilePic(request.getUsername(), request.getImageBytes());
        } catch (Exception e) {
            throw new TweeterRemoteException(e.getMessage(), e.getClass().getCanonicalName(), null);
        }

        return response;
    }

    public UserDAO getUserDAO() {
        return FactoryManager.getDAOFactory().makeUserDAO();
    }

    public S3Bucket getS3Bucket() {return new S3Bucket();}

}
