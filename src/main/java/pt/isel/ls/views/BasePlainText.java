package pt.isel.ls.views;

import pt.isel.ls.results.RequestResult;

public class BasePlainText implements View {

    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        return requestResult.getMessage();
    }
}
