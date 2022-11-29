package kmct;
import java.io.*;
import java.util.*;
import org.json.*;

public class TokenManager {
    private static TokenManager instance = null;
    private String token = "";

    public static TokenManager getInstance(){
        if (instance==null){
            instance = new TokenManager();
        }
        return instance;
    }

    public String getToken(){
        return this.token;
    }

    public String createToken(String userKey, int problemID)throws IOException{
        String token = null;
        String response = Connection.getInstance().start(userKey, problemID);

        if (response.equals("400")){
            System.out.println("400: problem id, 또는 param이 잘못됨!");
        } else if (response.equals("401")) {
            System.out.println("401:: X-Auth-Token Header가 잘못됨");
        } else if (response.equals("403")) {
            System.out.println("403:: user_key가 잘못되었거나 10초 이내에 생성한 토큰이 존재");
            token = loadTokenFile();
        } else if (response.equals("500")) {
            System.out.println("500:: 서버 에러, 문의 필요");
        } else {
            saveTokenFile(response/* token */);
            token = response;
            response = "200";
        }
        this.token = token;
        return response;
    }
    private void saveTokenFile(String token) throws IOException {
        File tokenFile = new File("./res/token");
        FileOutputStream fo = new FileOutputStream(tokenFile);
        if(!tokenFile.exists()){
            tokenFile.createNewFile();
        }
        fo.write(token.toString().getBytes());
        fo.flush();
        fo.close();
    }

    private String loadTokenFile() throws IOException {
        String token = null;
        FileReader freader = new FileReader("./res/token");
        BufferedReader br = new BufferedReader(freader);
        token = br.readLine();
        br.close();
        return token;
    }

}
