package pt.isel.ls.handlers.sports;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Models.domainclasses.Sport;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.Models.SportsModel;
import pt.isel.ls.Request.Request;
import java.util.Optional;

public class CreateSportHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public RequestResult createSport(String name, String description) throws AppException {
        Sport sport = model.createSport(name, description);
        if (sport != null) {
            return new RequestResult(200, sport, "Sport created with success with id = " + sport.sid + "");
        }
        return new RequestResult(500, null, "Failed to create sport.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        if (request.getQueryStrings().containsKey("name")
                && request.getQueryStrings().containsKey("description")
        ) {
            return Optional.of(createSport(
                    request.getQueryStrings().get("name").getFirst(),
                    request.getQueryStrings().get("description").getFirst()));
        }

        throw new InvalidRequestException();
    }
}
