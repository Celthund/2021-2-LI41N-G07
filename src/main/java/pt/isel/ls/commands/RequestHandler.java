package pt.isel.ls.commands;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Request.Request;
import java.util.Optional;


public interface RequestHandler {
    Optional<RequestResult> execute(Request request) throws AppException;
}
