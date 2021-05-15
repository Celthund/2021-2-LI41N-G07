package pt.isel.ls.views.sports.plaintext;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getSportPlainText;

public class GetSportByIdPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        Sport sport = ((GetSportByIdResult) requestResult).getData();

        return getSportPlainText(sport);
    }
}
