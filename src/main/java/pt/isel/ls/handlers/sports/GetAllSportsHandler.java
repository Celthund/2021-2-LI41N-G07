package pt.isel.ls.handlers.sports;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.SportsModel;
import pt.isel.ls.request.Request;
import java.util.LinkedList;
import java.util.Optional;

public class GetAllSportsHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public RequestResult getAllSports() throws AppException {
        LinkedList<Sport> sports = model.getAllSports();
        if (sports.size() > 0) {
            return new RequestResult(200, sports, sports.size() + " sports found.");
        }
        return new RequestResult(404, null, "No sports found.");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        return Optional.of(getAllSports());
    }
}
