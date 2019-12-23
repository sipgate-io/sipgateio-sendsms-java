package sipgateio.sendsms;

public class Sms {
	public String smsId;
	public String message;
	public String recipient;
	public long sendAt;

	public Sms(String smsId, String message, String recipient, long sendAt) {
		this.smsId = smsId;
		this.message = message;
		this.recipient = recipient;
		this.sendAt = sendAt;
	}

	public Sms(String smsId, String message, String recipient) {
		this.smsId = smsId;
		this.message = message;
		this.recipient = recipient;
	}
}
