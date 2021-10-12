package com.jc.model;

public class CommonHeader {
    public String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "CommonHeader{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }
}
