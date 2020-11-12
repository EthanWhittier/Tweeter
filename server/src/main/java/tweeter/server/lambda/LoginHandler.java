package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.server.service.LoginService;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {



    /**
     * Lambda Handler that gets a LoginRequest, calls the service
     * which returns the response. Response contains the logged
     * in user and an AuthToken.
     *
     * @param request
     * @param context
     * @return
     */
    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {
        LoginService loginService = getLoginService();
        return loginService.login(request);
    }

    public LoginService getLoginService() {
        return new LoginService();
    }
}
