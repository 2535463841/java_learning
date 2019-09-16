package org.zbw.robot;

import java.awt.AWTException;
import java.io.IOException;

import org.zbw.window.BaseWindow;

public class Demo {
	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
		BaseWindow baseWindow = new BaseWindow("Everything");
		baseWindow.saveImageAsPNG("image1.png");
	}
}

