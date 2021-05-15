package pt.isel.ls.views.sports.plaintext;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getSportPlainText;

public class GetAllSportsPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).getData();
        StringBuilder stringBuilder = new StringBuilder();
        for (Sport sport : sports) {
            stringBuilder.append(getSportPlainText(sport));
        }
        return stringBuilder.toString();
    }
}
