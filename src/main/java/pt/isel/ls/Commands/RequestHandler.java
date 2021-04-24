package pt.isel.ls.Commands;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Request.Request;

public interface RequestHandler {
    RequestResult execute(Request request) throws AppException;
}
