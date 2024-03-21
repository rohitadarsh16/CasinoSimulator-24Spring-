import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class TempleAlertModel {

    private MainMenuModel menuModel;
    private TempleAlertView templeAlertView;

    public TempleAlertModel(MainMenuModel menu) {
        this.menuModel = menu;
        this.templeAlertView = new TempleAlertView(this); // This line sets up the view for temple alerts
    }

    public HttpResponse<String> startGame() throws Exception{
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.reddit.com/r/Temple/new.json?limit=1"))
                .header("User-Agent", "YourApp/0.1 by YourRedditUsername")
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        System.out.println(httpResponse.body());
        return httpResponse;
    }

    public void exit() {
        templeAlertView.dispose();
        menuModel.setVisible();
    }
}
