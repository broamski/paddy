package com.nuszkowski.paddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse.Status;


public class HTTPRequest {
	static Logger log = Logger.getLogger("HTTPRequest");
	
	private CloseableHttpClient client;
	private HttpGet request;

	public HTTPRequest(String uri) {
		client = HttpClients.createDefault();
		request = new HttpGet(uri);
	}

	public void setProxy(String val) {
		String host_val = val.split(":")[0];
		int port_val = Integer.parseInt(val.split(":")[1]);
		log.debug("Using proxy server: " + val);
		HttpHost proxy = new HttpHost(host_val, port_val, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		request.setConfig(config);
	}

	static class ErrorObject {
		int code;
		String message;

		ErrorObject(int code_val, String message_val) {
			this.code = code_val;
			this.message = message_val;
		}
	}

	static class ErrorReturn {

		public ErrorObject error;

		ErrorReturn(ErrorObject e_val) {
			this.error = e_val;
		}
	}

	public String getRequest() {

		try {
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() != 200) {
				ErrorObject err = new ErrorObject(response.getStatusLine()
						.getStatusCode(), response.getStatusLine()
						.getReasonPhrase());
				ErrorReturn err_ret = new ErrorReturn(err);
				Gson gson = new Gson();
				return gson.toJson(err_ret);
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			return result.toString();

		} catch (ClientProtocolException e) {
			log.error(e);
			ErrorObject err = new ErrorObject(99, e.getMessage());
			ErrorReturn err_ret = new ErrorReturn(err);
			Gson gson = new Gson();
			return gson.toJson(err_ret);
		} catch (IOException e) {
			log.error(e);
			ErrorObject err = new ErrorObject(99, e.getMessage());
			ErrorReturn err_ret = new ErrorReturn(err);
			Gson gson = new Gson();
			return gson.toJson(err_ret);
		}

	}
}
