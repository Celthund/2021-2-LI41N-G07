package pt.isel.ls.results.sports;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class GetAllSportsResult implements RequestResult {
    public final int status;
    public final LinkedList<Sport> data;
    public final String message;

    public GetAllSportsResult(int status, LinkedList<Sport> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
