package com.myproject.parking.lib.data;
/**
 * Historical Changes:
 * 0.0.1 - 2012-11-28, Hariyanto
 * - prepare for mockup 
 */

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionData {
	private static Logger LOG = LoggerFactory.getLogger(VersionData.class);
	
	private String version;
	private String appsName;
	private String buildDate;
	
	public VersionData() {
		appsName = "Parking Engine Apps";
		version = "0.0.1";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
		
		String buildNumber = "";
		Date appsBuild = new Date();
		try {
			Enumeration<URL> resources = getClass().getClassLoader()
				.getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
				Manifest manifest = new Manifest(resources.nextElement().openStream());
				Attributes attrib = manifest.getMainAttributes();
				String vendorId = attrib.getValue("Implementation-Vendor-Id");
				if ("com.emobile".equals(vendorId)) {
					buildNumber = attrib.getValue("Implementation-Build");
					String timestamp = attrib.getValue("Implementation-Timestamp");
					if (timestamp != null && !timestamp.equals("")) {
						long t = Long.parseLong(timestamp);
						appsBuild = new Date(t);
					}
					break;
				}
			}
		} catch (IOException E) {
			// handle
		}
		if (!"".equals(buildNumber))
			version = version + " rev." + buildNumber;
		this.buildDate = sdf.format(appsBuild);
	}
	
	public void printInfo() throws Exception {
		LOG.info("{} v.{}, Built on {}", new String[] { appsName, version, buildDate });
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getAppsName() {
		return appsName;
	}
	
	public String getBuildDate() {
		return buildDate;
	}


}
