package kmct;

import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;


public class Main {
    private static String authToken = "c737d498159cded390b8cbcc94657634"; //this could be changed
    private static int problemNum = 1;
    private static int time = 0;

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(Connection.getInstance().start(authToken,problemNum));
        



    }
    private static String start(String authToken, int problemNum) throws IOException{
        String response = TokenManager.getInstance().createToken(authToken,problemNum);
        System.out.println("token: " + response);
        return response;
    }


}