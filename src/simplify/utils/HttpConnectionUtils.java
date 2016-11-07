package simplify.utils;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpConnectionUtils {
	
	private PropertyReader reader = new PropertyReader("enviprops");
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();

	private static void trustAllHttpsCertificates() throws Exception {

		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc =javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(
		sc.getSocketFactory());

	}
	public boolean getHTTPsServerStatus(String envi) {
		logger.info("IN getServerStatus", className);
		boolean flag=false;
		String urlStr = reader.getValue(envi+"_console");
		logger.info("urlStr ::"+urlStr, className);
		try {			
			trustAllHttpsCertificates();
			 HostnameVerifier hv = new HostnameVerifier()
			    {
			        public boolean verify(String urlHostName, SSLSession session)
			        {
			            return true;
			        }
			    };
			    URL url = new URL(urlStr);
			    HttpsURLConnection.setDefaultHostnameVerifier(hv);
			    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			    con.setReadTimeout(3000);
			    con.setConnectTimeout(3000);
			    con.connect();
			    int status = con.getResponseCode();
			    if(status == 200) {
			    	flag = true;
			    }
		} catch (Exception e) {
			logger.error("In Exception:: "+e.getLocalizedMessage(), className);
			e.printStackTrace();
		}
		logger.info("Status:: "+flag, className);
		return flag;
	}
	public boolean getHTTPServerStatus(String envi)  {
		logger.info("IN getHTTPServerStatus", className);
		boolean flag = false;
		HttpURLConnection con = null;
		String proxyFlag =reader.getValue(envi+"_console_proxy");
		String urlStr = reader.getValue(envi+"_console_url");
		logger.info("urlStr ::"+urlStr+" proxyFlag::"+proxyFlag, className);
		try {
			SocketAddress addr = new InetSocketAddress("10.60.0.210", 8080);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			URL url = new URL(urlStr);			
			if("true".equalsIgnoreCase(proxyFlag)) {
				con = (HttpURLConnection) url.openConnection(proxy);
			} else {
				con = (HttpURLConnection) url.openConnection();
			}
			con.setReadTimeout(3000);
			con.setConnectTimeout(3000);
			con.connect();
			int status = con.getResponseCode();
			if(status == 200) {
		    	flag = true;
		    }
		} catch(Exception e) {
			logger.error("In Exception:: "+e.getLocalizedMessage(), className);
			e.printStackTrace();
		}
		return flag;
	}
	
	public static void main(String[] args) {
		HttpConnectionUtils utils = new HttpConnectionUtils();		
		System.out.println("Status :"+utils.getHTTPServerStatus("dev"));
	}
	

}
class miTM implements javax.net.ssl.TrustManager,javax.net.ssl.X509TrustManager
{
	public java.security.cert.X509Certificate[] getAcceptedIssuers()
	{
		return null;
	}
	public boolean isServerTrusted(java.security.cert.X509Certificate[] certs)
	{
		return true;
	}	
	public boolean isClientTrusted( java.security.cert.X509Certificate[] certs)
	{
	return true;
	}
	
	public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
	{
		return;
	}
	
	public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
	{
		return;
	}
}
