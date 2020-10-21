package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Follow;


public class FollowRequest {


   private Follow follow;

   public FollowRequest(Follow follow) {
       this.follow = follow;
   }

    public Follow getFollow() {
        return follow;
    }

}
