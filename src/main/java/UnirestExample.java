import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestExample {
    public static void main(String[]args){
        HttpResponse request= null;
        try {
            request =  Unirest
                    .get("https://reqres.in/api/users?page=2")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println("wenfiewg");
    }
}
