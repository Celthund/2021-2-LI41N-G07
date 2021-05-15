package pt.isel.ls.views.sports.plaintext;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.views.View;

public class GetSportByIdPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        Sport sport = ((GetSportByIdResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sport {sid: ")
                .append(sport.sid)
                .append(", name: ")
                .append(sport.name)
                .append(", description: ")
                .append(sport.description)
                .append("}\n");
        return stringBuilder.toString();
    }
}