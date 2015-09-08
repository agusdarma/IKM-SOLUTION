package com.myproject.parking.trx.data;
/**
 * Historical Changes:
 * 1.0.0 - 2012-12-06, 16:00
 * - first release with version number
 *
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
	private Date buildDate;
	private SimpleDateFormat sdf;
	
	public VersionData() {
		appsName = "Parking ";
		version = "1.0.0";
		sdf = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
		
		String buildNumber = "";
		try {
			Enumeration<URL> resources = getClass().getClassLoader()
				.getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
				Manifest manifest = new Manifest(resources.nextElement().openStream());
				Attributes attrib = manifest.getMainAttributes();
				String vendorId = attrib.getValue("Implementation-Vendor-Id");
				if ("com.myproject".equals(vendorId)) {
					buildNumber = attrib.getValue("Implementation-Build");
					String timestamp = attrib.getValue("Implementation-Timestamp");
					if (timestamp != null && !timestamp.equals("")) {
						long t = Long.parseLong(timestamp);
						buildDate = new Date(t);
					}
					break;
				}
			}
		} catch (IOException E) {
			// handle
		}
		if (!"".equals(buildNumber))
			version = version + " rev." + buildNumber;
		if (buildDate == null)
			buildDate = new Date();
	}
	
	public void printInfo() throws Exception {
		LOG.info("{} v.{}, Built on {}", new String[] { appsName, version, sdf.format(buildDate) });
	}
	
	public String getVersion() {
		return version;
	}
	
	public Date getBuildDate() {
		return buildDate;
	}

	public String getAppsName() {
		return appsName;
	}

}
