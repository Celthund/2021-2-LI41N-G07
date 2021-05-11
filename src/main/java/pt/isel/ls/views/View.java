package pt.isel.ls.views;

import pt.isel.ls.results.RequestResult;

public interface View {

    String getRepresentation(RequestResult requestResult);

}
