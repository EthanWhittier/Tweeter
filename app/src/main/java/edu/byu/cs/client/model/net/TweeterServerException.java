package edu.byu.cs.client.model.net;

import java.util.List;

import tweeter.model.net.TweeterRemoteException;

public class TweeterServerException extends TweeterRemoteException {

    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}