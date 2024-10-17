import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.oidc.server.OidcWiremockTestResource;
import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlForm;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(OidcWiremockTestResource.class)
public class WelcomeResourceTest {

    @Test
    public void testCodeFlow() throws Exception {
        try (final WebClient webClient = createWebClient()) {
            // the test REST endpoint listens on '/code-flow'
            HtmlPage page = webClient.getPage("http://localhost:8081/welcome");

            HtmlForm form = page.getFormByName("form");
            // user 'alice' has the 'user' role
            form.getInputByName("username").type("alice");
            form.getInputByName("password").type("alice");

            page = form.getInputByValue("login").click();

            assertEquals("alice", page.getBody().asNormalizedText());
        }
    }

    private WebClient createWebClient() {
        WebClient webClient = new WebClient();
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        return webClient;
    }
}