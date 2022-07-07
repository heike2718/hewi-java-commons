// =====================================================
// Project: commons-auth
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.clientauth.impl;

import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientDefinitionException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_auth.clientauth.IClientAccessTokenService;
import de.egladil.web.commons_auth.clientauth.InitAccessTokenRestClient;
import de.egladil.web.commons_auth.clientauth.MessagePayload;
import de.egladil.web.commons_auth.clientauth.OAuthClientCredentials;
import de.egladil.web.commons_auth.clientauth.ResponsePayload;
import de.egladil.web.commons_auth.config.AuthproviderConfiguration;
import de.egladil.web.commons_auth.exception.AuthRuntimeException;
import de.egladil.web.commons_auth.exception.ClientAuthException;

/**
 * ClientAccessTokenService
 */
@RequestScoped
public class ClientAccessTokenService implements IClientAccessTokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientAccessTokenService.class);

	@Inject
	@RestClient
	InitAccessTokenRestClient initAccessTokenRestClient;

	@Override
	public String orderAccessToken(final AuthproviderConfiguration authproviderConfiguration) {

		String nonce = UUID.randomUUID().toString();
		OAuthClientCredentials credentials = OAuthClientCredentials.create(authproviderConfiguration.getClientId(),
			authproviderConfiguration.getClientSecret(), nonce);

		Response authResponse = null;

		try {

			authResponse = initAccessTokenRestClient.authenticateClient(credentials);

			ResponsePayload responsePayload = authResponse.readEntity(ResponsePayload.class);

			evaluateResponse(nonce, responsePayload);

			@SuppressWarnings("unchecked")
			Map<String, String> dataMap = (Map<String, String>) responsePayload.getData();
			String accessToken = dataMap.get("accessToken");

			return accessToken;
		} catch (IllegalStateException | RestClientDefinitionException | WebApplicationException e) {

			String msg = "Unerwarteter Fehler beim Anfordern eines client-accessTokens: " + e.getMessage();
			LOGGER.error(msg, e);
			throw new AuthRuntimeException(msg, e);
		} catch (ClientAuthException e) {

			// wurde schon geloggt
			return null;
		} finally {

			if (authResponse != null) {

				authResponse.close();
			}
		}
	}

	private void evaluateResponse(final String nonce, final ResponsePayload responsePayload) throws ClientAuthException {

		MessagePayload messagePayload = responsePayload.getMessage();

		if (messagePayload.isOk()) {

			@SuppressWarnings("unchecked")
			Map<String, String> dataMap = (Map<String, String>) responsePayload.getData();
			String responseNonce = dataMap.get("nonce");

			if (!nonce.equals(responseNonce)) {

				String msg = "Possible BOT-Attack: zurückgesendetes nonce stimmt nicht";

				LOGGER.warn(msg);
				throw new ClientAuthException();
			}
		} else {

			LOGGER.error("Authentisierung des Clients hat nicht geklappt: {} - {}", messagePayload.getLevel(),
				messagePayload.getMessage());
			throw new ClientAuthException();
		}
	}
}
