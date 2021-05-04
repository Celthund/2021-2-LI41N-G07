package pt.isel.ls.handlers.sports;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.Models.domainclasses.Sport;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.Models.SportsModel;
import pt.isel.ls.Request.Request;
import java.util.Optional;

public class GetSportByIdHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public RequestResult getSportById(String sid) throws ServerErrorException {
        Sport sport = model.getSportById(sid);
        if (sport != null) {
            return new RequestResult(
                200,
                sport,
                "Found sport with id = " + sid);
        }
        return new RequestResult(404, null, "Sport not found.");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws InvalidRequestException, ServerErrorException {
        if (request.getParameters().containsKey("sid")) {
            return Optional.of(getSportById(request.getParameters().get("sid")));
        }
        throw new InvalidRequestException();
    }
}
