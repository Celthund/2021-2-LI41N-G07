package pt.isel.ls.results.sports;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class GetSportByIdResult extends RequestResult<Sport> {

    public GetSportByIdResult(int status, Sport data, String message) {
        super(status, data, message);
    }
}
