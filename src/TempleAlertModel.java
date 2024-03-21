import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class TempleAlertModel {
    /**
     * Current state of the game.
     */
    private currentState currentState;

    private MainMenuModel menuModel;
    private TempleAlertView templeAlertView;
    private currentState gameState;

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.reddit.com/r/Temple/new.json?limit=1"))
                .header("User-Agent", "YourApp/0.1 by YourRedditUsername")
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }

    public enum currentState {
        GameOn, GameOver
    }

    public TempleAlertModel(MainMenuModel menu, int money) {
        this.menuModel = menu;
        this.gameState = currentState.WaitingForBet;
        this.templeAlertView = new TempleAlertView(this); // This line sets up the view, make sure RandnumView is ready for this.
    }

    public void startGame(int bet) {

    }

    private void endGame() {
        // Optionally reset for a new game or update UI to allow for a new game to start
        gameState = currentState.GameOver;
        templeAlertView.showGameOverMessage();
        templeAlertView.updateGameView(); // This should update the game's UI based on the new game state

    }

    public currentState getGameState() {
        return gameState;
    }

    public void exit() {
        templeAlertView.dispose();
        menuModel.setVisible();
    }
}
