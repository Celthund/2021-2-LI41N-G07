package pt.isel.ls.commands;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.request.Request;
import java.util.Optional;


public interface RequestHandler {
    Optional<RequestResult> execute(Request request) throws AppException;
}
