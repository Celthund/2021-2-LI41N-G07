package pt.isel.ls.handlers.sports;

import java.util.Optional;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.models.SportsModel;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetSportByIdResult;

public class GetSportByIdHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public GetSportByIdResult getSportById(String sid) throws ServerErrorException {
        Sport sport = model.getSportById(sid);
        if (sport != null) {
            return new GetSportByIdResult(
                200,
                sport,
                "Found sport with id = " + sid);
        }
        return new GetSportByIdResult(404, null, "Sport not found.");
    }


    @Override
    public Optional<RequestResult<?>> execute(Request request) throws InvalidRequestException, ServerErrorException {
        if (request.getParameters().containsKey("sid")) {
            return Optional.of(getSportById(request.getParameters().get("sid")));
        }
        throw new InvalidRequestException();
    }
}
