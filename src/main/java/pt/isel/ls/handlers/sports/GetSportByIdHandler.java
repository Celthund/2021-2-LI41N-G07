package pt.isel.ls.handlers.sports;

import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.mappers.SportsMapper;
import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.dabataseutils.Database;
import pt.isel.ls.dabataseutils.TransactionManager;

public class GetSportByIdHandler implements RequestHandler {

    SportsMapper model = new SportsMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("sid")) {
            DataSource dt = Database.getDataSource();
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
