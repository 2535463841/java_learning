package org.zbw;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.cli.ParseException;


public class Main {
	final static HashMap<String, HashMap<String, String>> classMap = new HashMap<String, HashMap<String, String>>();
	
	public static void registerClassHelper(String action, String className, String helpInfo) {
		HashMap<String, String> classInfo = new HashMap<String, String>();
		classInfo.put("class", className);
		classInfo.put("helpInfo", helpInfo);
		classMap.put(action, classInfo);
	}
	
	public static void registerHashMap() {
		registerClassHelper("RandomWordsCreater", "org.zbw.utils.RandomWordsCreater", "创建随机单词");
		registerClassHelper("AutoClick", "org.zbw.robot.AutoClick", "自动点击窗口按钮，并且保存窗口截图");
	}
	
	public static void printClassHelpInfo() {
		System.out.println("Usage: action [options]");
		System.out.println("the value of action :");
		for (String action : classMap.keySet()) {
			System.out.println(String.format("    %10s %s", action, classMap.get(action).get("helpInfo")));
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
			IOException, ParseException {

		registerHashMap();

		if (args.length == 0 || !classMap.containsKey(args[0])) {
			printClassHelpInfo();
			System.exit(1);
		}
		
		Class<?> classzz = Class.forName(classMap.get(args[0]).get("class") );
		Method method = classzz.getMethod("main", String [].class);
		ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.remove(0);

		String[] newArgs = new String[argsList.size()];
		argsList.toArray(newArgs);
		method.invoke(String.class, (Object) newArgs);
	}
}
