package org.zbw.window;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;



public class BaseWindow {
	int x;
	int y;
	int width;
	int height;
	double scalling;
	HWND hwnd;
	Robot robot;
	
	public BaseWindow(String name) throws AWTException {
		this.hwnd = User32.INSTANCE.FindWindow(null, name);
		if (hwnd == null) {
			System.err.println(String.format("window \"%s\" not found.", name));
			return;
		}
		this.setForeground();
		RECT rect = new RECT();
		User32.INSTANCE.GetWindowRect(this.hwnd, rect);
		this.x = rect.left;
		this.y = rect.top;
		this.width = rect.right - rect.left;
		this.height = rect.bottom - rect.top;

		this.robot = new Robot();
		this.initScalling();
	}
	
	private void initScalling() {
		GraphicsDevice graphDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		DisplayMode disMode = graphDevice.getDisplayMode();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.scalling = (double) disMode.getWidth() / dimension.getWidth();
	}
	
	public double getscalling() {
		return this.scalling;
	}
	double getScalling() {
		GraphicsDevice graphDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		DisplayMode disMode = graphDevice.getDisplayMode();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		return (double) disMode.getWidth() / dimension.getWidth();
	}
	
	public void moveMouseTo(int x, int y) { 
		this.robot.mouseMove(x, y);
	}
	public void delay(int second) {
		this.robot.delay(second * 1000);
	}
	
	private void setForeground() {
		User32.INSTANCE.SetForegroundWindow(this.hwnd);
		User32.INSTANCE.ShowWindow(this.hwnd, WinUser.SW_RESTORE);
	}
	
	public void saveImageAsPNG(String filePath) throws IOException {
		this.setForeground();
		this.delay(3);
		Rectangle rectangle = new Rectangle(
			Double.valueOf(this.x / this.scalling).intValue(),
			Double.valueOf(this.y / this.scalling).intValue(),
			Double.valueOf(this.width / this.scalling).intValue(),
			Double.valueOf(this.height / this.scalling).intValue()
		);
		
		BufferedImage image = this.robot.createScreenCapture(rectangle);
		ImageIO.write(image, "PNG", new File(filePath));
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
