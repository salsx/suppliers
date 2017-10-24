package controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigFactory;
import models.RoleType;
import models.User;
import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import services.db.MongoDBService;
import services.user.UserService;
import views.html.index;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "Authorization";

    public static UserDAO userDAO=new UserDAOImpl(User.class, MongoDBService.getDatastore());



    public Result index() {
        /**
         * index()  :    does no ts compilation in advance. the ts files are download by the browser and compiled there to js.
         */
        return ok(index.render());
    }


    @BodyParser.Of(BodyParser.Json.class)
    public Result authenticate(){
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();
        if(username == null || password ==null) {
            return badRequest("Missing parameter [username] & [password]");
        } else {
            User user = userDAO.authenticate(username,password);
            if (user != null) {
                String token=null;
                try {
                    Algorithm algorithm = Algorithm.HMAC256(ConfigFactory.load().getString("play.crypto.secret"));
                    token = JWT.create()
                            .withIssuer("Producer OCD ")
                            .withSubject(user.getUsername())
                            .withIssuedAt(Date.from(Instant.now()))
                            .withExpiresAt(Date.from(Instant.now().plus(2,ChronoUnit.HOURS)))
                            .sign(algorithm);
                    user.setToken(token);
                } catch (UnsupportedEncodingException exception) {
                    return badRequest("UTF-8 encoding not supported");//UTF-8 encoding not supported
                } catch (JWTCreationException exception) {
                    return badRequest("Invalid Signing configuration / Couldn't convert Claims.");//Invalid Signing configuration / Couldn't convert Claims.
                }
                /*session("currentUser", user.getUsername());
                ObjectNode authTokenJson = Json.newObject();
                authTokenJson.put("_id", user.getId().toString());
                authTokenJson.put("username", user.getUsername());
                authTokenJson.put("firstName", user.getFirstName());
                authTokenJson.put("lastName", user.getLastName());
                authTokenJson.put("token", user.getToken());*/
                //response().setCookie(Http.Cookie.builder(AUTH_TOKEN, user.getToken()).withSecure(ctx().request().secure()).build());
                return ok(token);
            } else {
                return badRequest("No User available with such username.");
            }
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result register(){
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        if(json.findPath("username").textValue() == null || json.findPath("password").textValue()==null) {
            return badRequest("Missing required parameters");
        } else {
            if (userDAO.findByUsername(username) != null) {
                return badRequest("Username already taken.");
            } else {
                User user=new User(json.findPath("username").textValue(),json.findPath("password").textValue(),json.findPath("firstName").textValue(),json.findPath("lastName").textValue(),json.findPath("email").textValue(), RoleType.USER );
                userDAO.save(user);
                return created();
            }
        }
    }

    @Security.Authenticated(Secured.class)
    public Result getAll(){
        return ok(Json.toJson(userDAO.findAll()));
    }

    @Security.Authenticated(Secured.class)
    public User getCurrent(){
        return (User)Http.Context.current().args.get("user");
    }

    @Security.Authenticated(Secured.class)
    public Result get(String id){
        return ok(Json.toJson(userDAO.get(id)));
    }

    @Security.Authenticated(Secured.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(String id){
        JsonNode json = request().body().asJson();
        User userToUpdate=userDAO.get(id);
        if(!json.findPath("firstName").isMissingNode()) userToUpdate.setFirstName(json.findPath("firstName").textValue());
        if(!json.findPath("lastName").isMissingNode()) userToUpdate.setLastName(json.findPath("lastName").textValue());
        if(!json.findPath("email").isMissingNode()) userToUpdate.setEmail(json.findPath("email").textValue());
        userDAO.save(userToUpdate);
        return ok();
    }

    @Security.Authenticated(Secured.class)
    public Result delete(String username){
        Logger.debug("delete user with username:"+username);
        userDAO.delete(userDAO.findByUsername(username));
        return ok();
    }

    @Security.Authenticated(Secured.class)
    public Result logout() {
        User u=getCurrent();
        u.setToken(null);
        MongoDBService.getDatastore().save(u);
        session().clear();
        return redirect("/");
    }

    public CompletionStage<Result> authorize(){
        /*UserService userservice=new UserService();
        return userservice.authorize().thenApply(p -> red(p.asJson()));*/
        return null;

    }
}
