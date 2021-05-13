package pt.isel.ls.views.users.json.sports;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.users.json.JsonGetter.*;

public class CreateSportJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Sport sport = ((CreateSportResult) requestResult).data;
        return (sport).toString();
    }
}
