/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b510.menu;

import mai.capture.DesktopCapture;

/**
 *
 * @author Hongten
 * 菜单-工具
 */
public class AlarmTools {

	/** 定义截图功能 */
	@SuppressWarnings("unused")
	private static DesktopCapture deskTopCapture;
	/**
	 * 截图
	 */
	public static void screenshot(){
		deskTopCapture=new DesktopCapture();
	}
}
