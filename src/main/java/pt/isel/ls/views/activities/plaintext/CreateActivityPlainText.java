package pt.isel.ls.views.activities.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.CreateActivityResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.routes.plaintext.CreateRoutePlainText;
import pt.isel.ls.views.sports.plaintext.CreateSportPlainText;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getUserPlainText;

public class CreateActivityPlainText implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        Activity activity = ((CreateActivityResult) requestResult).data;
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Activity {aid: ")
                .append(activity.aid)
                .append(", date: ")
                .append(activity.date)
                .append(", duration: ")
                .append(activity.duration)
                .append(", ")
                .append(getUserPlainText( activity.user))
                .append(", ")
                .append(new CreateSportPlainText().getRepresentation((RequestResult) activity.sport))
                .append(", ");

        if (activity.route != null)
            stringbuilder.append(new CreateRoutePlainText().getRepresentation((RequestResult) activity.route));

        stringbuilder.append("}\n");

        return stringbuilder.toString();
    }
}