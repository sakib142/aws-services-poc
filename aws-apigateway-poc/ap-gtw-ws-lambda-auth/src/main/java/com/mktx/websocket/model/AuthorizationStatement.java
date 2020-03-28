package com.mktx.websocket.model;

public class AuthorizationStatement {

    public String Action;
    public AuthorizationEffect Effect;
    public String Resource;

    public AuthorizationStatement() {
    }

    public AuthorizationStatement(String action, AuthorizationEffect effect, String resource) {
        this.Action = action;
        this.Effect = effect;
        this.Resource = resource;
    }

}
