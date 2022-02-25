package dev.dolphine;


import io.vertx.mutiny.ext.web.Route;
import io.vertx.mutiny.ext.web.Router;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/routes")
public class RouteResource {

    @Inject
    Router router;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String create(@QueryParam("endpoint") String endpoint) {
        Route route = createRoute(endpoint);
        return "created endpoint: " + route.getPath();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> all() {
        return router.getRoutes().stream().map(Route::getPath).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Route createRoute(String endpoint) {
        return router.get("/" + endpoint).handler(rc -> rc.response().endAndForget("Hello from route " + endpoint));
    }
}