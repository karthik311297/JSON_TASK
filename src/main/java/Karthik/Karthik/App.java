package Karthik.Karthik;

import java.io.IOException;
import java.math.BigInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * Hello world!
 *
 */
public class App 
{	static class Rec{
		private String clientContext;
		private long expirationmillis;
		private Integer status;
		private String uid;
		private String user;
		private String location;
		public Rec(){}
		public Rec(String a,long b,Integer c,String d,String e,String f){
			clientContext=a;
			expirationmillis=b;
			status=c;
			uid=d;
			user=e;
			location=f;
		}
		public long getExpirationmillis() {
			return expirationmillis;
		}

		public String getClientContext() {
			return clientContext;
		}

		public Integer getStatus() {
			return status;
		}

		public String getUid() {
			return uid;
		}

		public String getUser() {
			return user;
		}

		public String getLocation() {
			return location;
		}
		

		
	}
	static void parsedbfunc(JSONObject record){
		
		try {
			Connection con=DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/jso", "postgres", "vki31d97");
			if(con!=null){
				System.out.println("Connected to database");
				System.out.println("Executing statement");
				Rec ob=new Rec(record.getString("clientContext"),record.getNumber("expirationMillis").longValue(),record.getNumber("status").intValue(),record.getString("uId"),record.getJSONObject("user").toString(),record.getJSONObject("location").toString());
				System.out.println("--------------");
				System.out.println(record.getString("clientContext")+"\n"+record.getNumber("expirationMillis").longValue()+"\n"+record.getNumber("status").intValue()+"\n"+record.getString("uId")+"\n"+record.getJSONObject("user").toString()+"\n"+record.getJSONObject("location").toString()+"\n");
				System.out.println("--------------");
				PreparedStatement pt=con.prepareStatement("INSERT INTO \"registerUserSrvUrl\" values(?,?,?,?,?::JSON,?::JSON)");
				pt.setString(1, ob.getClientContext());
				pt.setLong(2, ob.getExpirationmillis());
				pt.setInt(3, ob.getStatus());
				pt.setString(4, ob.getUid());
				pt.setObject(5, ob.getUser());
				pt.setObject(6,ob.getLocation());
				int i=pt.executeUpdate();  
				System.out.println(i+" records inserted");
			}
			else{
				System.out.println("unable to connect");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static void myfunc(String nurl) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    try {
//	        HttpGet httpget = new HttpGet("http://httpbin.org/");
	        HttpGet httpget = new HttpGet(nurl);
	        System.out.println("Next task json Executing request " + httpget.getRequestLine());

	        // Create a custom response handler
	        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

	            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    //System.out.println(entity);

	                    return entity != null ? EntityUtils.toString(entity) : null;
	                } else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }

	        };
	        String responseBody = httpclient.execute(httpget, responseHandler);
	        System.out.println("----------------------------------------");
	        System.out.println(responseBody);
	        System.out.println("-----------------");
	        parsedbfunc(new JSONObject(responseBody));

		}finally{
	        httpclient.close();
		}
	}

	public final static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
//            HttpGet httpget = new HttpGet("http://httpbin.org/");
            HttpGet httpget = new HttpGet("https://vpp.itunes.apple.com/WebObjects/MZFinance.woa/wa/VPPServiceConfigSrv");
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        System.out.println(entity);
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            JSONObject obj=new JSONObject(responseBody);
            String xn=obj.getString("registerUserSrvUrl");
            String xn2=obj.getString("getVPPAssetsSrvUrl");
            System.out.println(xn);
            System.out.println(xn2);
            
            myfunc(xn+"?sToken=eyJleHBEYXRlIjoiMjAyMC0wOS0xNVQyMzoxNToxNi0wNzAwIiwidG9rZW4iOiI5c3RtRTcvbWFuckxJZExqcU1DeXpjaXM2S1BxZ0p3blVha1JMditVN0swdlF1RTQvWDIwdkNYeXd2U3pwZXpZQk05d3B0M0Z0bVYrSExXYldlcVRWdUhmaWxzL050ajZ1OTgzdktPckFjbkNBOHlvN0VDV09IQ1o3bm1kSDFMK09zVzdJeThUVlZ5MkNWS0JXZGVOZEE9PSIsIm9yZ05hbWUiOiJOb3ZlbGwifQ==&clientUserIdStr=100001");
         //   myfunc(xn2+"?sToken=eyJleHBEYXRlIjoiMjAyMC0wOS0xNVQyMzo1Njo0NC0wNzAwIiwidG9rZW4iOiI5c3RtRTcvbWFuckxJZExqcU1DeXpjaXM2S1BxZ0p3blVha1JMditVN0swODFzZGRkRnlLcFd5NGJicnFjV2pVTG04QkRSVDBFWlJYN254UEVMS05BWXk3WEViS0s3cjFqZFVyb01sL1ppcnlFWlpXS010bndiNzFnYkFXQ00xVzBJZ2Y0YzM1ZzA2V254YWJwalc2SVE9PSIsIm9yZ05hbWUiOiJOb3ZlbGwifQ==&associateSerialNumbers=serialNumber3&adamIdStr=281796108&pricingParam=STDQ");
            
        } finally {
            httpclient.close();
        }
        
    }

}
