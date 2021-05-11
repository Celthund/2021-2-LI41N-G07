package pt.isel.ls.results.sports;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class CreateSportResult implements RequestResult {
    public final int status;
    public final Sport data;
    public final String message;

    public CreateSportResult(int status, Sport data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
