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
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class GetSportByIdHandler implements RequestHandler {

    SportsModel model = new SportsModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws InvalidRequestException, ServerErrorException {
        if (request.getParameters().containsKey("sid")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String sid = request.getParameters().get("sid");
                Sport sport = model.getSportById(sid, conn);
                if (sport != null) {
                    return new GetSportByIdResult(
                        200,
                        sport,
                        "Found sport with id = " + sid);
                }
                return new GetSportByIdResult(404, null, "Sport not found.");
            }));
        }
        throw new InvalidRequestException("Missing sport id.");
    }
}
