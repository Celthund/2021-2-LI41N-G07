package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Sport;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Exceptions.ServerErrorException;
import pt.isel.ls.Models.SportsModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.LinkedList;

public class SportsView implements RequestHandler {

    SportsModel model = new SportsModel();

    public RequestResult getSportById(String sid) throws ServerErrorException {
        Sport sport = model.getSportById(sid);
        if (sport != null){
            return new RequestResult(
                    200,
                    sport,
                    "Found sport with id = " + sid);
        }
        return new RequestResult(404, null, "Sport not found.");
    }

    public RequestResult getAllSports() throws ServerErrorException {
        LinkedList<Sport> sports = model.getAllSports();
        if(sports.size() > 0){
            return new RequestResult(200, sports, sports.size() + " sports found.");
        }
        return new RequestResult(404, null, "No sports found.");
    }

    public RequestResult createSport(String name, String description) throws ServerErrorException {
        Sport sport = model.createSport(name, description);
        if (sport != null){
            return new RequestResult(200, sport, "Sport created with success with id = " + sport.sid + "");
        }
        return new RequestResult(500, null, "Failed to create sport.");
    }

    // The execute method receives a request, checks the arguments in it so it can get the query correspondent to the Method, Path and/or parameters in the Request class
    @Override
    public RequestResult execute(Request request) throws InvalidRequestException, ServerErrorException {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllSports();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("sid"))
            return getSportById(request.getParameters().get("sid"));

        if (request.getMethod() == Method.POST &&
                request.getQueryString().containsKey("name") &&
                request.getQueryString().containsKey("description")
        )
            return createSport(
                    request.getQueryString().get("name").getFirst(),
                    request.getQueryString().get("description").getFirst());

        throw new InvalidRequestException();
    }
}
