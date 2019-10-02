package org.zbw.lib;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
	final static String DEFAULT_DATE_FORMAT = "YYYY-MM-dd_HHmmss";
	
	public static String getDate() {
		return Utils.getDate(DEFAULT_DATE_FORMAT);
	}

	public static String getDate(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}
}
