import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import java.util.ArrayList;
import java.util.List;


public class ListarTopicos {
    public static void main(String[] args) {

        // Declara um userAgent para o cabeçalho da requisição
        UserAgent userAgent = new UserAgent("web", "br.com.zup.desafio2", "v1.0.0", "otavio_teixeira");

        // Declara sua credencial de autenticação
        Credentials credentials = Credentials.script("otavio_teixeira", "teste01",
                "1jaK7a5_xy_Z-g", "KXw5d-sBsHAQR_nJ8K30uZtBW1o");

        // Cria uma conexão http com a API do reddit
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);

        // Autentica e realiza uma requisição GET ao servidor RedditClient
        RedditClient redditClient  = OAuthHelper.automatic(adapter, credentials);

        DefaultPaginator<Submission> frontPage = redditClient.frontPage()
                .sorting(SubredditSort.NEW)
                .timePeriod(TimePeriod.DAY)
                .limit(20)
                .build();

        List<String> topics = new ArrayList();

        Listing<Submission> submissions = frontPage.next();
        for (Submission s : submissions) {
           topics.add(s.getTitle());
        }

        topics.sort((a, b) -> a.compareToIgnoreCase(b));

        for (String topic : topics){
            System.out.println(topic);
        }
    }
}