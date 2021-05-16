package pt.isel.ls.views.sports.plaintext;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getSportPlainText;

public class CreateSportPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        Sport sport = ((CreateSportResult) requestResult).getData();

        if (sport == null) {
            return requestResult.getMessage();
        }
        return getSportPlainText(sport);
    }
}
