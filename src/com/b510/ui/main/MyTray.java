package com.b510.ui.main;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.b510.menu.AlarmFile;
import com.b510.menu.AlarmTools;

public class MyTray implements ActionListener, MouseListener {
	private Image icon;// 图标
	private TrayIcon trayIcon;
	private SystemTray systemTray;// 系统托盘

	private MyAlarm myAlarm; // 托盘所属主窗体
	private PopupMenu pop = new PopupMenu(); // 弹出菜单
	// 菜单选项
	/** 截图 */
	private MenuItem screenshot = new MenuItem("ScreenShot");
	/** 还原 */
	private MenuItem open = new MenuItem("Restore");
	/** 退出*/
	private MenuItem exit =new MenuItem("Exit");
	public MyTray(MyAlarm myAlarm) {
		this.myAlarm = myAlarm;
		// 得到托盘的图标
		icon = new ImageIcon(this.getClass().getClassLoader().getResource(
				"image/mytray.png")).getImage();

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();
			// 设置鼠标经过图标时，显示的内容
			trayIcon = new TrayIcon(icon, "open Alarm", pop);
			pop.add(screenshot);
			pop.add(open);
			pop.add(exit);
			// 添加系统托盘
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
				trayIcon.addMouseListener(this);
			}
		}
		displayInfoListener();
		trayIcon.addMouseListener(this);
		exit.addActionListener(this);
		open.addActionListener(this);
		screenshot.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exit){
			//退出系统
			AlarmFile.exit();
		}else if (e.getSource() == open) {
			// 单点击菜单中的"还原"选项时，还原窗口
			//displayInfo();
			//trayIcon.displayMessage("温馨提示", "hello,world", TrayIcon.MessageType.INFO);
			myAlarm.iconed = false;
			friendListSet(true);
			
		} else if (e.getSource() == screenshot) {
			// 但点击“截图”选项时，进行截图
			AlarmTools.screenshot();
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 但鼠标点击一次的时候，进行弹出窗口
		if (e.getClickCount() == 1 && e.getButton() != MouseEvent.BUTTON3) {
			if (!myAlarm.isVisible()) {
				friendListSet(true);
			} else {
				friendListSet(false);
			}
		}
		// 但鼠标点击两次的时候，进行弹出窗口
		// 如果窗口有显示，则隐藏窗口，否则显示窗口
		if (e.getClickCount() == 2 && e.getButton() != MouseEvent.BUTTON3) {
			if (!myAlarm.isVisible()) {
				friendListSet(true);
			} else {
				friendListSet(false);
			}
		}
	}

	/**
	 * 设置friendList的可见性
	 */
	private void friendListSet(boolean flag) {
		myAlarm.setVisible(true);
		myAlarm.setExtendedState(JFrame.NORMAL);
	}
	
	
	public void displayInfoListener() {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						while (true) {
							try {
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
							}
							if(myAlarm.getSecondOfResult()==30){
								 trayIcon.displayMessage("温馨提示","距闹钟设置时间还剩:\n00 时 00 分 " +myAlarm.getSecondOfResult() + " 秒",TrayIcon.MessageType.INFO);
								 myAlarm.setSecondOfResult(29);
							}
							if(myAlarm.getFlagOfBackground()){
								trayIcon.displayMessage("温馨提示","您已经更改了背景，点击托盘图标\n可以看到效果,右键托盘图标\n可以进行功能选择。",TrayIcon.MessageType.INFO);
								myAlarm.setFlagOfBackground(false);
							}
						}
					}
				}).start();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
