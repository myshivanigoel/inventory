package in.util;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getBaseUrl(HttpServletRequest request)
	{
		String base="";
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
//		base = url.substring(7, url.length() - uri.length() + ctx.length()) + "/";
		base = url.substring(7, url.length() - uri.length() + ctx.length());

		
		return base;
	}

}
