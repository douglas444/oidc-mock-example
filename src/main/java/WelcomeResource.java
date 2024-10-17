import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Authenticated
@Path("/welcome")
public class WelcomeResource {

    @Location("welcome.html")
    Template template;

    @Inject
    JsonWebToken idToken;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response welcome() {
        return Response.ok(this.template.data("name", this.idToken.getClaim("preferred_username")).render()).build();
    }

}
