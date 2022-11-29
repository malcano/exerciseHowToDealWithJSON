package kmct;
import java.net.HttpURLConnection;
import java.io.*;
import java.net.*;
import org.json.*;

/*
*
* API CONNECTION
*
* */

public class Connection {
    private static Connection instance = null;

    public static Connection getInstance(){
        if(instance==null){
            instance = new Connection();
        }
        return instance;
    }


    /*
     * POST /start
     * X-Auth-Token: {X-Auth-Token}
     * Content-Type: application/json
     * */
    public String start (String authToken, int problem){
        HttpURLConnection conn = null;//http url connection
        JSONObject responseJson = null; // json 응답
        String response = null;

        try{
            //URL 설정
            URL url = new URL(kmct.GlobalData.Host_URL + kmct.GlobalData.Post_Start);

            //URL 오픈
            conn = (HttpURLConnection) url.openConnection();

            //Request 형식 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Auth-Token", authToken);
            conn.setRequestProperty("Content-Type","application/json");

            //Request에 Json data 준비
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter (new OutputStreamWriter(conn.getOutputStream()));
            JSONObject problemID = new org.json.JSONObject();
            problemID.put("problem",problem);
            bw.write(problemID.toString());
            bw.flush();
            bw.close();

            int responseCode = conn.getResponseCode();

            if(responseCode == 200){//성공
                BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine())!=null){
                    sb.append(line);
                }
                responseJson =  new JSONObject (sb.toString());
                response = responseJson.getString("auth_key");
            }else{
                response = String.valueOf(responseCode);
            }

        }catch(JSONException e){
            e.printStackTrace();
            System.out.println("Json Exception occured");
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }


/*
GET /waiting_line
Authorization: {auth_key}
Content-Type: application/json
*
* */
    public JSONObject waitingLine(){
        HttpURLConnection conn = null;
        JSONObject getJson = null;

        try {
            URL url = new java.net.URL(kmct.GlobalData.Host_URL + kmct.GlobalData.Get_Waiting_Line);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization",TokenManager.getInstance().getToken());

            int getResponse = conn.getResponseCode();
            if(getResponse==200){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = br.readLine())!= null){
                    sb.append(line);
                }
                getJson = new org.json.JSONObject(sb.toString());
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

        return getJson;

    }

/*GET /game_result
Authorization: {auth_key}
Content-Type: application/json*/



/*
*GET /user_info
Authorization: {auth_key}
Content-Type: application/json
* */


/*PUT /match
Authorization: {auth_key}
Content-Type: application/json
*/

/*
* PUT /change_grade
Authorization: {auth_key}
Content-Type: application/json
* */

/*GET /score
Authorization: {auth_key}
Content-Type: application/json*/

}