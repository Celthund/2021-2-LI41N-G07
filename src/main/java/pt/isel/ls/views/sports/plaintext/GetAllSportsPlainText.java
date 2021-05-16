package pt.isel.ls.views.sports.plaintext;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getSportPlainText;

public class GetAllSportsPlainText implements View {

    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).getData();

        if (sports == null) {
            return requestResult.getMessage();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Sport sport : sports) {
            stringBuilder.append(getSportPlainText(sport));
        }
        return stringBuilder.toString();
    }
}
