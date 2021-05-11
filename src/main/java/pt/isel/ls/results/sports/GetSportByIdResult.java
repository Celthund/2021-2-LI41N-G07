package pt.isel.ls.results.sports;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class GetSportByIdResult implements RequestResult {
    public final int status;
    public final Sport data;
    public final String message;

    public GetSportByIdResult(int status, Sport data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
