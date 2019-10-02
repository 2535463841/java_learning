package org.zbw.robot;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.zbw.lib.Utils;
import org.zbw.window.BaseWindow;


class ClickPoint {
	int x;
	int y;
	String captureFile;
	
	public ClickPoint(String clickPoint) {
		String[] clickPointInfo = clickPoint.split(":");
		this.x = Integer.valueOf(clickPointInfo[0].strip());
		this.y = Integer.valueOf(clickPointInfo[1].strip());
		this.captureFile = clickPointInfo.length >= 3 ? clickPointInfo[2].strip() : null;
	}
}


public class AutoClick {
	public static void main(String[] args) throws AWTException, ParseException {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("h", "help", false, "print help message");
		options.addOption("c", "config-file", true, "the path of config file, default is ./autoclick.properties");
		
		CommandLine commandLine = parser.parse(options, args);
		if (commandLine.hasOption("h")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("Usage of RandomWordsCreater", options);
			System.exit(1);
		}
		String configFile = commandLine.getOptionValue("c", "autoclick.properties");
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configFile));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		String windowNames = properties.getProperty("window.names");
		HashMap<String, ArrayList<ClickPoint>> windowClickMap = new HashMap<String, ArrayList<ClickPoint>>();
		
		for (String windowName : windowNames.split(",")) {
			String windowClickPoints = properties.getProperty(String.format("window.%s.clickpoints", windowName.replace(" ", "_"))).strip();
			ArrayList<ClickPoint> clickPoints = new ArrayList<ClickPoint>();
			if (windowClickPoints != null && windowClickPoints != "") {
				for (String clickPointString : windowClickPoints.split(",")) {
					clickPoints.add(new ClickPoint(clickPointString.strip()));
				}
			}
			windowClickMap.put(windowName, clickPoints);
		}
		
		for (String windowName : windowClickMap.keySet()) {
			System.out.println("handle window: " + windowName);
			BaseWindow baseWindow = new BaseWindow(windowName);
			for (ClickPoint clickPoint : windowClickMap.get(windowName)) {
				System.out.println(String.format("click point: %s, %s", clickPoint.x, clickPoint.y));
				baseWindow.mouseClick(clickPoint.x, clickPoint.y);
				if (clickPoint.captureFile != null && clickPoint.captureFile != "" ) {
					try {
						baseWindow.saveImageAsPNG(
							String.format("%s_%s_%s.png", Utils.getDate(), windowName.replace(" ", "_"), clickPoint.captureFile)
						);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			baseWindow.hide();
			
		}
	}
}
