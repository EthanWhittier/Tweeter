package tweeter.model.service.request;

import tweeter.model.domain.Follow;


public class FollowRequest {

    public FollowRequest() {}

   private Follow follow;

   public FollowRequest(Follow follow) {
       this.follow = follow;
   }

    public Follow getFollow() {
        return follow;
    }

}
