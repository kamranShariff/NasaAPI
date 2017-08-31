package ca.nasa.neo;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used for rest calls.
 * @author kamran
 *
 */

public class AstroidsCalculationsImpl implements AstroidsCalculations{

	/**
	 * This method accepts input reference if which 
	 * is used as input for API call  which will return a JSON object .
	 *It returns a String value. 
	 *
	 */
	public ResponseEntity<String> restCall(String url){
		try {
			this.avoidSslCertificates();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity entity = new HttpEntity("", headers);
		
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	public String getDetailsNeo(String reference_id) throws Exception {
		// TODO Auto-generated method stub
		
		
		ResponseEntity<String> result =  restCall("https://api.nasa.gov/neo/rest/v1/neo/"+reference_id+"?api_key=DEMO_KEY");
		
		return result.getBody().toString();
		
	}
	
	public  String earthObjects() throws Exception {
		
		
		ResponseEntity<String> result =  restCall("https://api.nasa.gov/neo/rest/v1/neo/browse?api_key=DEMO_KEY");
		
		return result.getBody().toString();
	}
	/**
	 * This method is used to bypass SSL certificates.
	 * @throws Exception
	 */
	public void avoidSslCertificates() throws Exception{
		TrustManager[] trustAllCerts = new TrustManager[] {
			       new X509TrustManager() {
			          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return null;
			          }

			          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

			          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

			       }
			    };

			    SSLContext sc = SSLContext.getInstance("SSL");
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			    // Create all-trusting host name verifier
			    HostnameVerifier allHostsValid = new HostnameVerifier() {
			        public boolean verify(String hostname, SSLSession session) {
			          return true;
			        }
			    };
			    // Install the all-trusting host verifier
			    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			    System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
	}
	
}
