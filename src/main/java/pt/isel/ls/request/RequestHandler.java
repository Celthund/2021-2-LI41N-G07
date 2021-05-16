package pt.isel.ls.request;

import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;


public interface RequestHandler {
    Optional<RequestResult<?>> execute(Request request) throws AppException;
}
