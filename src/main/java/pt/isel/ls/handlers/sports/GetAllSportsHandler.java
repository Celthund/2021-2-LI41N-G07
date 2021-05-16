package pt.isel.ls.handlers.sports;

import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.SportsModel;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;

public class GetAllSportsHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public GetAllSportsResult getAllSports(String skip, String top) throws AppException {
        LinkedList<Sport> sports = model.getAllSports(skip, top);
        if (sports.size() > 0) {
            return new GetAllSportsResult(200, sports, sports.size() + " sports found.");
        }
        return new GetAllSportsResult(404, null, "No sports found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {

        //Implementation of paging options
        if (request.getQueryStrings().containsKey("skip") && request.getQueryStrings().containsKey("top")) {
            return Optional.of(getAllSports(request.getQueryStrings().get("skip").getFirst(),
                request.getQueryStrings().get("top").getFirst()));
        }

        return Optional.of(getAllSports(null, null));
    }
}
