// =====================================================
// Project: commons-auth
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.clientauth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * InitAccessTokenRestClient
 */
@RegisterRestClient
@Path("clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface InitAccessTokenRestClient {

	@POST
	@Path("client/accesstoken")
	Response authenticateClient(OAuthClientCredentials clientSecrets);

}
