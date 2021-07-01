package pt.isel.ls.handlers.sports;

import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.SportsModel;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.utils.Database;
import pt.isel.ls.utils.TransactionManager;

public class CreateSportHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getQueryStrings().containsKey("name") && request.getQueryStrings().containsKey("description")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String name = request.getQueryStrings().get("name").getFirst();
                String description = request.getQueryStrings().get("description").getFirst();
                Sport sport = model.createSport(name, description, conn);
                if (sport != null) {
                    CreateSportResult resp = new CreateSportResult(303, sport,
                        "Sport created with success with id = " + sport.sid + "");
                    resp.addHeader("Location", "/sports/" + sport.sid);
                    return resp;
                }
                return new CreateSportResult(500, null, "Failed to create sport.");
            }));
        }
        throw new InvalidRequestException("Missing sport name or description.");
    }
}
