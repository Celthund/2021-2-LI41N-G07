package pt.isel.ls.handlers.activities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByTopsResult;
import pt.isel.ls.utils.Database;
import pt.isel.ls.utils.TransactionManager;

public class GetActivitiesByTopsHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        if (queryString.containsKey("sid")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String sid = queryString.get("sid").getFirst();
                String orderBy = queryString.containsKey("orderBy") ? queryString.get("orderBy").getFirst() : null;
                String date = queryString.containsKey("date") ? queryString.get("date").getFirst() : null;
                String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;
                String distance = queryString.containsKey("distance") ? queryString.get("distance").getFirst() : null;
                String skip = queryString.containsKey("skip") ? queryString.get("skip").getFirst() : null;
                String top = queryString.containsKey("top") ? queryString.get("top").getFirst() : null;
                LinkedList<Activity> activities =
                    model.getActivitiesByTops(sid, orderBy, date, rid, distance, skip, top, conn);

                if (activities != null) {
                    return new GetActivitiesByTopsResult(200, activities,
                        String.format("Found %s activities", activities.size()));
                }
                return new GetActivitiesByTopsResult(404, null, "No activities found");

            }));
        }
        throw new InvalidRequestException("Missing sport id.");
    }
}
