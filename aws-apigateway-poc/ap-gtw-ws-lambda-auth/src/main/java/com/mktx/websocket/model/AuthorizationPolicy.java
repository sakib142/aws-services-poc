package com.mktx.websocket.model;

import java.util.LinkedList;
import java.util.List;

public class AuthorizationPolicy {

    public String Version;
    public List<AuthorizationStatement> Statement = new LinkedList<>();

    public AuthorizationPolicy() {
    }

    public AuthorizationPolicy(String version) {
        this.Version = version;
    }

    public void addStatement(AuthorizationStatement aStatement) {
        this.Statement.add(aStatement);
    }
}
