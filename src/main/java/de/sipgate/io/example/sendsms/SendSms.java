package de.sipgate.io.example.sendsms;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SendSms {

	private static final String BASE_URL = "https://api.sipgate.com/v2";

	public static void main(String[] args) {
		Unirest.setObjectMapper(new SmsObjectMapper());

		String username = "YOUR_SIPGATE_EMAIL";
		String password = "YOUR_SIPGATE_PASSWORD";

		String smsId = "YOUR_SIPGATE_SMS_EXTENSION";

		String message = "YOUR_MESSAGE";
		String recipient = "RECIPIENT_PHONE_NUMBER";

		/*
		Only needed when sending a scheduled sms at specific date

		Calendar timestamp = Calendar.getInstance();
		timestamp.set(2001, 6, 24, 13, 37);
		long sendAt = timestamp.getTimeInMillis() / 1000;
		*/

		Sms sms = new Sms(smsId, message, recipient /* , sendAt */);

		try {
			HttpResponse<String> response = sendNewSmsRequest(username, password, sms);
			System.out.println("Status: " + response.getStatus());
			System.out.println("Body: " + response.getBody());
		} catch (UnirestException e) {
			System.err.println(e.getMessage());
		}
	}

	private static HttpResponse<String> sendNewSmsRequest(String username, String password, Sms smsObject)
			throws UnirestException {
		return Unirest.post(BASE_URL + "/sessions/sms")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.basicAuth(username, password)
				.body(smsObject)
				.asString();
	}
}
