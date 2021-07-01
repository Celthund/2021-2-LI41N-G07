package pt.isel.ls.results.sports;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;

public class GetAllSportsResult extends RequestResult<LinkedList<Sport>> {

    public GetAllSportsResult(int status, LinkedList<Sport> data, String message) {
        super(status, data, message);
    }
}
