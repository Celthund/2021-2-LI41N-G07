package pt.isel.ls.views.sports.plaintext;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

public class GetAllSportsPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        for (Sport sport : sports) {
            stringBuilder.append("Sport {sid: ")
                    .append(sport.sid)
                    .append(", name: ")
                    .append(sport.name)
                    .append(", description: ")
                    .append(sport.description)
                    .append("}\n");
        }
        return stringBuilder.toString();
    }
}
