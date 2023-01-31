package main.java;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import main.java.Exceptions.UnexpectedTypeException;
import main.java.Exceptions.MissingTokenException;
import main.java.Exceptions.PermissionsScopeException;

public class ChallongeClient {
    private ChallongeApi api;

    public ChallongeClient(File authFile) throws IOException, MissingTokenException, ParseException, UnexpectedTypeException {
        TypeUtils.requireType(authFile, File.class,"authFile");
        this.api = new ChallongeApi(authFile);

        System.out.println(this.api.scope);
    }

    public ChallongeClient(String authFilePath) throws IOException, MissingTokenException, ParseException, UnexpectedTypeException {
        this(new File(authFilePath));
    }

    public void tournaments() throws IOException, InterruptedException, ParseException, PermissionsScopeException, MissingTokenException {
        this.api.scope.requirePermissionScope(Scope.TOURNAMENTS_READ);
        System.out.println(this.api.apiGet(ChallongeApi.toURI("tournaments")));
    }
}