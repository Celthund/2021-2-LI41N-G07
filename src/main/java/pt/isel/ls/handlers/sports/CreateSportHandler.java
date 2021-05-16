package pt.isel.ls.handlers.sports;

import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.SportsModel;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;

public class CreateSportHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    public CreateSportResult createSport(String name, String description) throws AppException {
        Sport sport = model.createSport(name, description);
        if (sport != null) {
            return new CreateSportResult(200, sport, "Sport created with success with id = " + sport.sid + "");
        }
        return new CreateSportResult(500, null, "Failed to create sport.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
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
