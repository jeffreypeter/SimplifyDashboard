package simplify.client.bpd;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import bpm.rest.client.BPMClient;
import bpm.rest.client.BPMClientException;
import bpm.rest.client.BPMClientImpl;
import bpm.rest.client.GenericClient;
import bpm.rest.client.authentication.AuthenticationTokenHandler;
import bpm.rest.client.authentication.AuthenticationTokenHandlerException;
import bpm.rest.client.authentication.was.WASAuthenticationTokenHandler;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.representation.StringRepresentation;
import org.restlet.util.Series;

import simplify.utils.CustomLogger;

public class BPDApiExtended extends BPMClientImpl{
	private GenericClient client;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	public BPDApiExtended(String hostname, int port,AuthenticationTokenHandler handler) throws BPMClientException {
		super(hostname, port, handler);
		client = new GenericClient(hostname, "/rest/bpm/wle/v1", port, handler,
				false, 300000, 8000) ;
		/*{
			public String buildURL(String relativePath, Map<String, Object> arguments) {
				String output="";
				try {
		            System.out.println(Arrays.toString(getClass().getSuperclass().getMethods()));
		            java.lang.reflect.Method m = GenericClient.class.getDeclaredMethod("buildURL");
		            m.setAccessible(true);
		            output= (String) m.invoke(this, (Object[])null);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				return output;
			}
			public JSONObject executeRESTCall(String relativePath,
					Map<String, Object> arguments, Method method,
					boolean putContentInBody) throws BPMClientException,
					AuthenticationTokenHandlerException {

				// Prepare the request object
				String url;
				Request request;
				if (putContentInBody) {
					url = buildURL(relativePath, null);
					String requestBody = encodeArguments(arguments, true);
					logger.info("HTTP call: " + url,className);
					logger.info("Request body: " + requestBody,className);
					StringRepresentation sp = new StringRepresentation(requestBody);
					sp.setMediaType(MediaType.APPLICATION_WWW_FORM);
					request = new Request(method, url, sp);
				} else {
					url = buildURL(relativePath, arguments);
					logger.info("HTTP call: " + url,className);
					request = new Request(method, url);
					// The following is to avoid 411 error on certain servers that
					// require a content-length to be specified
					request.setEntity("\n", MediaType.TEXT_PLAIN);
				}

				if (handler.foundAuthenticationToken()) {
					// Add authentication token to request
					handler.addAuthenticationToken(request);
				} else if (!handler.isUsingUserIdentityInContainer()) {
					// Add userid and password to request only if not using user
					// identity in the container
					addAuthenticationChallengeResponse(request);
				}

				// Indicates the client preferences and let the server handle the best
				// representation with content negotiation.
				request.getClientInfo().getAcceptedMediaTypes()
						.add(new Preference<MediaType>(MediaType.APPLICATION_JSON));

				Client httpClient = null;
				try {
					Protocol protocol = (useSSL) ? Protocol.HTTPS : Protocol.HTTP;
					Context ctx = new Context();
					Series<Parameter> pa = ctx.getParameters();
					pa.add(new Parameter("readTimeout", Integer.toString(readTimeOut)));
					pa.add(new Parameter("socketConnectTimeoutMs", Integer
							.toString(connectionTimeOut)));
					httpClient = new Client(ctx, protocol);
					// Ask the HTTP client connector to handle the call
					Response response = httpClient.handle(request);
					return processResponse(response);
				} catch (BPMClientException lcException) {
					if ((lcException.getStatusCode() == 401 || lcException
							.getStatusCode() == 403)
							&& handler.foundAuthenticationToken() && !reauthenticating) {
						handler.reset();
						reauthenticating = true;
						try {
							logger.info("Reauthenticating...",className);
							return executeRESTCall(relativePath, arguments, method,
									putContentInBody);
						} finally {
							reauthenticating = false;
							logger.info("Reauthenticating attempt completed...",className);
						}
					}
					throw (lcException);
				} finally {
					try {
						if (httpClient != null)
							httpClient.stop();
					} catch (Exception e) {
						// throw new BPMClientException(e.getMessage());
					}
				}
			}
		};		*/
	}
	public JSONObject triggerTimer(int instanceId, String timerTokenId)throws BPMClientException, AuthenticationTokenHandlerException
	{
		    Map<String, Object> args = new HashMap<String, Object>();
		    args.put("action", "fireTimer");
		    args.put("timerTokenId", timerTokenId);
		    args.put("parts", "none");
		    return this.client.executeRESTCall("/process/" + instanceId, args, Method.POST, false);
	}
	public JSONObject getBPDInstanceDetails(int instanceId,String parts) throws BPMClientException, AuthenticationTokenHandlerException
	{
		logger.info("IN Extended BPDApi", className);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("parts",parts);
		JSONObject output = this.client.executeRESTCall("/process/" + instanceId, args, Method.GET, false);
		logger.info("Output:: "+output, className);
		return output;
	}
	
	
	
	public static void main(String[] args) {
		try {
			AuthenticationTokenHandler handler = new WASAuthenticationTokenHandler("tw_portaladmin", "hdfc1234");
			BPDApiExtended client = new  BPDApiExtended("172.25.54.177",9080,handler);
//			client.moveToken(75146, 4, "bpdid:5f893b5b5f228d76:394e90fd:144688e22e4:-7ff1");
/*//			JSONObject jsonObject = new JSONObject();
			
			
			 * Medclar 
			jsonObject.put("status","Pending");
			jsonObject.put("proposalNo","1100000005421");
			jsonObject.put("medClarNo","311");			
			jsonObject.put("medCenterName","SNG Diagnostics & Medical");
			jsonObject.put("userBranch","Mumbai - BKC");
			jsonObject.put("medCenterCode","61981599");
			client.runBPD("25.24f7093e-d424-4a15-8122-6f70805672e0", "2066.ca7cb737-dc36-4fe6-b2ba-4f1c23d01d18", jsonObject);
//			Payment
			jsonObject.put("taskCode","CHEQUEDIS");
			jsonObject.put("status","Sent For Approval");
			jsonObject.put("userId","42389");
			jsonObject.put("receiptNo","0000037473");
			jsonObject.put("zone","REG_AP, PUNE & NORTH KERALA");
			jsonObject.put("userBranch","0772");
			*/
			JSONObject jsonObject = new JSONObject("{ 	\"status\": \"Sent for Approval\", 	\"taskCode\": \"FUNDTRA\", 	\"userId\": \"111703\", 	\"receiptNo\": \"0000798057\", 	\"zone\": \"REG_MUMBAI - EAST & CENTRAL\", 	\"userBranch\": \"0545\" }");
			client.runBPD("25.ca89c438-b5ed-4862-9e4a-a31edc9d2490", "2066.971f5fc3-1c37-4823-ba28-cf68679d4b8c", jsonObject);
//			System.out.println(client.runService("CMS@GetWorkList", jsonObject));
			System.out.println("init done..");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
