<img src="https://www.sipgatedesign.com/wp-content/uploads/wort-bildmarke_positiv_2x.jpg" alt="sipgate logo" title="sipgate" align="right" height="112" width="200"/>

# sipgate.io java send sms example

To demonstrate how to send an SMS, we queried the `/sessions/sms` endpoint of the sipgate REST API.

For further information regarding the sipgate REST API please visit https://api.sipgate.com/v2/doc

### Prerequisites

-   JDK 8
-   Maven

### How To Use

Navigate to the project's root directory.

Install dependencies manually or use your IDE's import functionality:

```bash
$ mvn dependency:resolve
```

In order to run the code you have to set the following variables in [SendSms.java](./src/main/java/de/sipgate/io/example/sendsms/SendSms.java):

```java
String username = "YOUR_SIPGATE_EMAIL";
String password = "YOUR_SIPGATE_PASSWORD";

String smsId = "YOUR_SIPGATE_SMS_EXTENSION";

String message = "YOUR_MESSAGE";
String recipient = "RECIPIENT_PHONE_NUMBER";
```

The `smsId` uniquely identifies the extension from which you wish to send your message. Further explanation is given in the section [Web SMS Extensions](#web-sms-extensions).

> **Optional:**
> In order to send a delayed message uncomment the following lines and set the desired date and time in the future (up to one month):
>
> ```java
> Calendar timestamp = Calendar.getInstance();
> timestamp.set(2001, 6, 24, 13, 37);
> long sendAt = timestamp.getTimeInMillis()/1000;
> ```
>
> **Note:** The numbering of the months starts from 0 for January to 11 for December. [Calendar documentation](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html)
>
> Additionally, in the `SMS` object uncomment the `sendAt` property.
>
> ```java
> SMS sms = new SMS(smsId, message, recipient, sendAt);
> ```
>
> **Note:** The `sendAt` property in the `SMS` object is a [Unix timestamp](https://www.unixtimestamp.com/).

Run the application:

Build JAR

```bash
$ mvn package
```

Run the application:

```bash
$ java -jar target/sipgateio-send-sms-java-1.0-SNAPSHOT-jar-with-dependencies.jar
```

##### How It Works

The sipgate REST API is available under the following base URL:

```java
private static final String baseUrl = "https://api.sipgate.com/v2";
```

The API expects request data in JSON format. Thus the `Content-Type` header needs to be set accordingly. You can achieve that by using the `header` method from the `Unirest` library.

```java
private static HttpResponse<String> sendNewSmsRequest(String username, String password, SMS smsObject)
	throws UnirestException {
		return Unirest.post(baseUrl + "/sessions/sms")
			.header("Accept", "application/json")
			.header("Content-Type", "application/json")
			.basicAuth(username, password)
			.body(smsObject)
			.asString();
	}
```

The request body contains the `SMS` object, which has four fields: `smsId`, `recipient`, `message` and an optional `sendAt` specified above.

```java
public SMS(String smsId, String message, String recipient, long sendAt) {
	this.smsId = smsId;
	this.message = message;
	this.recipient = recipient;
	this.sendAt = sendAt;
}
```

We use the java package 'Unirest' for request generation and execution. The `post` method takes as argument the request URL. Headers, authorization header and the request body, are generated from `header`, `basicAuth` and `body` methods respectively. The request URL consists of the base URL defined above and the endpoint `/sessions/sms`. The method `basicAuth` from the 'Unirest' package takes credentials and generates the required Basic Auth header (for more information on Basic Auth see our [code example](https://github.com/sipgate/sipgateio-basicauth-java)).

```java
Unirest.post(baseUrl + "/sessions/sms")
	.header("Accept", "application/json")
	.header("Content-Type", "application/json")
	.basicAuth(username, password)
	.body(smsObject)
	.asString();
```

#### Send SMS with custom sender number

By default 'sipgate' will be used as the sender. It is only possible to change the sender to a mobile phone number by verifying ownership of said number. In order to accomplish this, proceed as follows:

1. Log into your [sipgate account](https://app.sipgate.com/connections/sms)
2. Use the sidebar to navigate to the **Connections** (_Anschlüsse_) tab
3. Click **SMS** (if this option is not displayed you might need to book the **Web SMS** feature from the Feature Store)
4. Click the gear icon on the right side of the **Caller ID** box and enter the desired sender number.
5. Proceed to follow the instructions on the website to verify the number.

### Web SMS Extensions

A Web SMS extension consists of the letter 's' followed by a number (e.g. 's0'). The sipgate API uses the concept of Web SMS extensions to identify devices within your account that are enabled to send SMS. In this context the term 'device' does not necessarily refer to a hardware phone but rather a virtual connection.

You can find out what your extension is as follows:

1. Log into your [sipgate account](https://app.sipgate.com/connections/sms)
2. Use the sidebar to navigate to the **Connections** (_Anschlüsse_) tab
3. Click **SMS** (if this option is not displayed you might need to book the **Web SMS** feature from the Feature Store)
4. The URL of the page should have the form `https://app.sipgate.com/{...}/connections/sms/{smsId}` where `{smsId}` is your Web SMS extension.

### Common Issues

#### SMS sent successfully but no message received

Possible reasons are:

- incorrect or mistyped phone number
- recipient phone is not connected to network
- long message text - delivery can take a little longer

#### HTTP Errors

| reason                                                                                                                                              | errorcode |
| --------------------------------------------------------------------------------------------------------------------------------------------------- | :-------: |
| bad request (e.g. request body fields are empty or only contain spaces, timestamp is invalid etc.)                                                  |    400    |
| username and/or password are wrong                                                                                                                  |    401    |
| insufficient account balance                                                                                                                        |    402    |
| no permission to use specified SMS extension (e.g. SMS feature not booked, user password must be reset in [web app](https://app.sipgate.com/login)) |    403    |
| wrong REST API endpoint                                                                                                                             |    404    |
| wrong request method                                                                                                                                |    405    |
| wrong or missing `Content-Type` header with `application/json`                                                                                      |    415    |
| internal server error or unhandled bad request (e.g. `smsId` not set)                                                                               |    500    |

### Related

- [unirest documentation](http://unirest.io/java.html)
- [sipgate team FAQ (DE)](https://teamhelp.sipgate.de/hc/de)
- [sipgate basic FAQ (DE)](https://basicsupport.sipgate.de/hc/de)

### Contact Us

Please let us know how we can improve this example.
If you have a specific feature request or found a bug, please use **Issues** or fork this repository and send a **pull request** with your improvements.

### License

This project is licensed under **The Unlicense** (see [LICENSE file](./LICENSE)).

### External Libraries

This code uses the following external libraries

-   unirest:
    Licensed under the [MIT License](https://opensource.org/licenses/MIT)
    Website: http://unirest.io/java.html

---

[sipgate.io](https://www.sipgate.io) | [@sipgateio](https://twitter.com/sipgateio) | [API-doc](https://api.sipgate.com/v2/doc)
