package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.service.RegisterService;


public class RegisterHandler implements  RequestHandler<RegisterRequest, RegisterResponse> {


    /**
     * Lambda handler that gets a RegisterRequest, calls the service
     * which returns the response. Response contains logged in user and
     * authtoken
     *
     * @param request
     * @param context
     * @return
     */
    @Override
    public RegisterResponse handleRequest(RegisterRequest request, Context context) {
        RegisterService registerService = getRegisterService();
        return registerService.register(request);
    }

    public RegisterService getRegisterService() {
        return new RegisterService();
    }

}
