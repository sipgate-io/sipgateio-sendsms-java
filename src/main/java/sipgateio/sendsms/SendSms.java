package sipgateio.sendsms;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdimascio.dotenv.Dotenv;

public class SendSms {

	private static final String BASE_URL = "https://api.sipgate.com/v2";

	public static void main(String[] args) {
		Unirest.setObjectMapper(new SmsObjectMapper());

		Dotenv dotenv = Dotenv.load();

		String tokenId = dotenv.get("TOKEN_ID");
		String token = dotenv.get("TOKEN");

		String smsId = dotenv.get("SMS_ID");

		String message = "YOUR_MESSAGE";
		String recipient = dotenv.get("RECIPIENT");

		/*
		Only needed when sending a scheduled sms at specific date

		Calendar timestamp = Calendar.getInstance();
		timestamp.set(2001, 6, 24, 13, 37);
		long sendAt = timestamp.getTimeInMillis() / 1000;
		*/

		Sms sms = new Sms(smsId, message, recipient /* , sendAt */);

		try {
			HttpResponse<String> response = sendNewSmsRequest(tokenId, token, sms);
			System.out.println("Status: " + response.getStatus());
			System.out.println("Body: " + response.getBody());
		} catch (UnirestException e) {
			System.err.println(e.getMessage());
		}
	}

	private static HttpResponse<String> sendNewSmsRequest(String tokenId, String token, Sms smsObject)
			throws UnirestException {
		return Unirest.post(BASE_URL + "/sessions/sms")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.basicAuth(tokenId, token)
				.body(smsObject)
				.asString();
	}
}
