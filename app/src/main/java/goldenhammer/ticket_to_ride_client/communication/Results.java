package goldenhammer.ticket_to_ride_client.communication;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class Results{
    private String body;
    private int responseCode;

    public Results(String body, int responseCode) {
        this.body = body;
        this.responseCode = responseCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}