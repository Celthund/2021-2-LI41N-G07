package pt.isel.ls.results.sports;

import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class CreateSportResult extends RequestResult<Sport> {

    public CreateSportResult(int status, Sport data, String message) {
        super(status, data, message);
    }
}
