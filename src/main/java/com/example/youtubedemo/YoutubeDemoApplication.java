package com.example.youtubedemo;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class YoutubeDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(YoutubeDemoApplication.class, args);
	}

	@Bean
	public JsonFactory defaultJsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}

	@Bean
	public HttpTransport httpTransport() throws GeneralSecurityException, IOException {
		return GoogleNetHttpTransport.newTrustedTransport();
	}
	@Bean
	public OAuth2Credentials credentials() {
		var clientId = "..";
		var clientSecret = "..";
		var refreshToken = "..";
		return UserCredentials.newBuilder()
						.setClientId(clientId)
						.setClientSecret(clientSecret)
						.setRefreshToken(refreshToken)
						.build();
	}

	@Bean
	public YouTube youTubeService(
			HttpTransport httpTransport,
			OAuth2Credentials credentials,
			JsonFactory jsonFactory
	) {
		var adaptor = new HttpCredentialsAdapter(credentials);
		return new YouTube.Builder(httpTransport, jsonFactory, adaptor)
				.setApplicationName("youtube-upload-test")
				.build();
	}
}
