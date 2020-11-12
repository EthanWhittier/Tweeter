package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;
import tweeter.server.service.LogoutService;

public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {


    /**
     * Lambda handler that gets a LogoutRequest, calls the service
     * which returns a response to log a user out.
     *
     * @param request
     * @param context
     * @return
     */
    @Override
    public LogoutResponse handleRequest(LogoutRequest request, Context context) {
        LogoutService logoutService = new LogoutService();
        return logoutService.logout(request);
    }
    
}
