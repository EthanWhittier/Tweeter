package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse extends Response {

    public LogoutResponse(boolean success) {
        super(success);

    }



    @Override
    public boolean equals(Object o) {
        if(!(o instanceof LogoutResponse)) {
            return false;
        }
        if(isSuccess()) {
            if(!((LogoutResponse) o).isSuccess()) {
                return false;
            }
        }
        return true;
    }
}
