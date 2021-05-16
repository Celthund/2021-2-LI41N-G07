package pt.isel.ls.views.sports.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getSportJson;

public class GetSportByIdJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        Sport sport = ((GetSportByIdResult) requestResult).getData();

        if (sport == null)
            return emptyDataSetJson(requestResult.getMessage(),
                    requestResult.getStatus()).toString();

        return getSportJson(sport).toString();
    }
}
