package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.server.service.LoginService;


public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {


    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {
        context.getLogger().log(request.getUsername());
        LoginService loginService = getLoginService();
        try {
            return loginService.login(request);
        } catch (TweeterRemoteException e) {
            return new LoginResponse(e.getMessage());
        }
    }

    public LoginService getLoginService() {
        return new LoginService();
    }



}
