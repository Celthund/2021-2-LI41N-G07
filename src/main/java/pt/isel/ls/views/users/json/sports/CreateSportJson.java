package pt.isel.ls.views.users.json.sports;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;

public class CreateSportJson extends AbstractSportJson {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Sport sport = ((CreateSportResult) requestResult).data;
        return getSportJson(sport).toString();
    }
}
