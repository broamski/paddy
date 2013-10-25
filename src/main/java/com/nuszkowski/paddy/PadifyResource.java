package com.nuszkowski.paddy;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.nuszkowski.paddy.HTTPRequest;
import com.sun.jersey.api.json.JSONWithPadding;

@Path("padify")
public class PadifyResource {
	static Logger log = Logger.getLogger("PadifyResource");
	
	@GET
	@Produces("application/x-javascript")
	public JSONWithPadding padify(@QueryParam("url") String url,
			@QueryParam("lame_proxy") String lame_proxy,
			@DefaultValue("callback") @QueryParam("callback") String callback) {
		
		log.debug("Process attempt - URL: " + url + " - Callback: " + callback + " - Lame Proxy: " + lame_proxy);

		HTTPRequest hr = new HTTPRequest(url);
		if (lame_proxy != null)
			hr.setProxy(lame_proxy);
		
		String results = hr.getRequest();
		log.debug(results);
		return new JSONWithPadding(results, callback);

	}
}
