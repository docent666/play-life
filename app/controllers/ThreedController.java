package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ThreedController extends Controller {
    
    public static Result index() {
        return ok(views.html.threed.render());
    }
    
}
