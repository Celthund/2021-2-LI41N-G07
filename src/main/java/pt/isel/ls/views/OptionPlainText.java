package pt.isel.ls.views;

import pt.isel.ls.results.OptionResult;
import pt.isel.ls.results.RequestResult;

public class OptionPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        return ((OptionResult) requestResult).message;
    }
}
