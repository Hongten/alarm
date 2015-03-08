/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b510.ui.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.b510.menu.AlarmBackground;
import com.b510.menu.AlarmFile;
import com.b510.menu.AlarmHelp;
import com.b510.menu.AlarmTools;

/**
 * 
 * @author Hongten
 */
public class MyAlarm extends JFrame implements ActionListener {

	/**
	 *+1
	 */
	private javax.swing.JButton add1;
	/**
	 * +30
	 */
	private javax.swing.JButton add30;
	/**
	 * +5
	 */
	private javax.swing.JButton add5;
	/**
	 * 时
	 */
	private javax.swing.JComboBox dorp_down_hours;
	/**
	 * 分
	 */
	private javax.swing.JComboBox dorp_down_minute;
	/**
	 * 秒
	 */
	private javax.swing.JComboBox drop_down_second;
	/**
	 * 试听
	 */
	private javax.swing.JButton listening_test;
	/**
	 * 试听-停止
	 */
	private javax.swing.JButton listening_test_stop;
	/**
	 * 主Panel
	 */
	private javax.swing.JPanel mainPanel;
	/**
	 * 菜单bar
	 */
	private javax.swing.JMenuBar menuBar;
	/**
	 * -1
	 */
	private javax.swing.JButton minus1;
	/**
	 * -30
	 */
	private javax.swing.JButton minus30;
	/**
	 * -5
	 */
	private javax.swing.JButton minus5;
	/**
	 * 当前时间
	 */
	private javax.swing.JLabel now;
	/**
	 * 铃声
	 */
	private javax.swing.JComboBox ring_setup;
	/**
	 * 截屏
	 */
	private javax.swing.JMenuItem screenshot;
	/**
	 * 更改背景图片
	 */
	private javax.swing.JMenuItem changeBackground;
	/**
	 * 停止
	 */
	private javax.swing.JButton stop;
	/**
	 * 工具
	 */
	private javax.swing.JMenu tools;
	/**
	 * 帮助
	 */
	private javax.swing.JMenu help;
	/**
	 * 关于
	 */
	private javax.swing.JMenuItem about;
	/**
	 * 退出
	 */
	private javax.swing.JMenuItem exit;
	/**
	 * 上传铃声
	 */
	private javax.swing.JMenuItem uploadRing;
	/**
	 * wenjian
	 */
	private javax.swing.JMenu file;
	/**
	 * 结果，即剩余时间
	 */
	private JLabel result;
	/**
	 * 分割线
	 */
	private javax.swing.JSeparator line;
	/**
	 * 变量-->小时
	 */
	private String h;
	/**
	 * 变量-->分钟
	 */
	private String m;
	/**
	 * 变量-->秒
	 */
	private String s;
	/**
	 * 线程的一个标志
	 */
	private boolean running = true;
	/**
	 * 定义图盘图盘标志
	 */
	public boolean iconed = false;
	/**
	 * 背景图片选择标志
	 */
	private int background = 0;
	/**
	 * 获取result的秒数
	 */
	private int secondOfResult;
	/**
	 * 更改背景图片的标志
	 */
	private boolean flagOfBackground = false;
	/**
	 * MyAlarm的X坐标
	 */
	public static int pointX = 0;
	/**
	 * MyAlarm的Y坐标
	 */
	public static int pointY = 0;
	/** 定义托盘 */
	MyTray myTray;
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -6601825053136983041L;

	public MyAlarm(String title) {
		this.setTitle(title);
		init();
	}

	/**
	 * 初始化背景图片
	 */
	public void initMainPanel() {
		mainPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				ImageIcon icon = new ImageIcon(AlarmBackground
						.getBackground(background));
				g.drawImage(icon.getImage(), 0, 0, 545, 463, null);
			}
		};
	}

	/**
	 * 主界面初始化
	 */
	public void init() {
		initMainPanel();
		now = new javax.swing.JLabel();
		stop = new javax.swing.JButton();
		add1 = new javax.swing.JButton();
		add5 = new javax.swing.JButton();
		add30 = new javax.swing.JButton();
		minus1 = new javax.swing.JButton();
		minus5 = new javax.swing.JButton();
		minus30 = new javax.swing.JButton();
		listening_test = new javax.swing.JButton();
		listening_test_stop = new javax.swing.JButton();
		dorp_down_hours = new javax.swing.JComboBox();
		dorp_down_minute = new javax.swing.JComboBox();
		drop_down_second = new javax.swing.JComboBox();
		ring_setup = new javax.swing.JComboBox();
		menuBar = new javax.swing.JMenuBar();
		file = new javax.swing.JMenu();
		uploadRing = new javax.swing.JMenuItem();
		exit = new javax.swing.JMenuItem();
		tools = new javax.swing.JMenu();
		screenshot = new javax.swing.JMenuItem();
		changeBackground = new javax.swing.JMenuItem();
		help = new javax.swing.JMenu();
		about = new javax.swing.JMenuItem();
		line = new javax.swing.JSeparator();
		result = new javax.swing.JLabel();

		mainPanel.setName("mainPanel"); // NOI18N

		result.setForeground(Color.RED);
		result.setName("result");
		result.setVisible(false);

		now.setFont(now.getFont().deriveFont(
				now.getFont().getStyle() | java.awt.Font.BOLD,
				now.getFont().getSize() + 70));
		now.setName("now"); // NOI18N
		// 时间监听器，得到系统时间和设置好时间后，得到剩余时间
		timeListener();

		stop.setText("stop"); // NOI18N
		stop.setName("stop"); // NOI18N
		// 初始化的时候是不可见的
		stop.setVisible(false);
		stop.addActionListener(this);

		add1.setText("+1"); // NOI18N
		add1.setName("add1"); // NOI18N
		add1.addActionListener(this);

		add5.setText("+5"); // NOI18N
		add5.setName("add5"); // NOI18N
		add5.addActionListener(this);

		add30.setText("+30"); // NOI18N
		add30.setName("add30"); // NOI18N
		add30.addActionListener(this);

		minus1.setText("-1"); // NOI18N
		minus1.setName("minus1"); // NOI18N
		minus1.addActionListener(this);

		minus5.setText("-5"); // NOI18N
		minus5.setName("minus5"); // NOI18N
		minus5.addActionListener(this);

		minus30.setText("-30"); // NOI18N
		minus30.setName("minus30"); // NOI18N
		minus30.addActionListener(this);

		listening_test.setText("试听"); // NOI18N
		listening_test.setName("listening_test"); // NOI18N
		listening_test.addActionListener(this);

		listening_test_stop.setText("停止"); // NOI18N
		listening_test_stop.setName("listening_test_stop"); // NOI18N
		listening_test_stop.addActionListener(this);

		dorp_down_hours.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "关闭", "00", "01", "02", "03", "04", "05", "06",
						"07", "08", "09", "10", "11", "12", "13", "14", "15",
						"16", "17", "18", "19", "20", "21", "22", "23" }));
		dorp_down_hours.setName("dorp_down_hours"); // NOI18N
		dorp_down_hours.addActionListener(this);

		dorp_down_minute.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "关闭", "00", "01", "02", "03", "04", "05", "06",
						"07", "08", "09", "10", "11", "12", "13", "14", "15",
						"16", "17", "18", "19", "20", "21", "22", "23", "24",
						"25", "26", "27", "28", "29", "30", "31", "32", "33",
						"34", "35", "36", "37", "38", "39", "40", "41", "42",
						"43", "44", "45", "46", "47", "48", "49", "50", "51",
						"52", "53", "54", "55", "56", "57", "58", "59" }));
		dorp_down_minute.setName("dorp_down_minute"); // NOI18N
		dorp_down_minute.addActionListener(this);

		drop_down_second.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "关闭", "00", "01", "02", "03", "04", "05", "06",
						"07", "08", "09", "10", "11", "12", "13", "14", "15",
						"16", "17", "18", "19", "20", "21", "22", "23", "24",
						"25", "26", "27", "28", "29", "30", "31", "32", "33",
						"34", "35", "36", "37", "38", "39", "40", "41", "42",
						"43", "44", "45", "46", "47", "48", "49", "50", "51",
						"52", "53", "54", "55", "56", "57", "58", "59" }));
		drop_down_second.setName("drop_down_second"); // NOI18N
		drop_down_second.addActionListener(this);

		ring_setup.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"铃声一", "铃声二", "铃声三", "铃声四", "铃声五", "铃声六", "铃声七" }));
		ring_setup.setName("ring_setup"); // NOI18N
		// 主要的布局
		omponentLayout();

		menuBar.setName("menuBar"); // NOI18N

		file.setText("文件"); // NOI18N
		file.setName("file"); // NOI18N

		uploadRing.setText("上传铃声"); // NOI18N
		uploadRing.setName("uploadRing"); // NOI18N
		uploadRing.addActionListener(this);
		file.add(uploadRing);

		file.add(line);

		exit.setText("退出");
		exit.setName("exit"); // NOI18N
		exit.addActionListener(this);
		file.add(exit);

		menuBar.add(file);

		tools.setText("工具"); // NOI18N
		tools.setName("tools"); // NOI18N

		screenshot.setText("截图工具"); // NOI18N
		screenshot.setName("screenshot"); // NOI18N
		screenshot.addActionListener(this);
		tools.add(screenshot);

		changeBackground.setText("更改背景");
		changeBackground.setName("changeBackground");
		changeBackground.addActionListener(this);
		tools.add(changeBackground);

		menuBar.add(tools);

		help.setText("帮助"); // NOI18N
		help.setName("help"); // NOI18N

		about.setText("关于软件");
		about.setName("about"); // NOI18N
		about.addActionListener(this);
		help.add(about);

		menuBar.add(help);

		this.add(mainPanel);
		setJMenuBar(menuBar);

		this.setVisible(true);
		this.setSize(550, 516);
		// this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		// this.setLocation(470, 250);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 当点击"-"最小化按钮时，系统会最小化到托盘
		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				iconed = true;
				setVisible(false);
			}

			public void windowClosing(WindowEvent e) {
				// 当点击"X"关闭窗口按钮时，会询问用户是否要最小化到托盘
				// 是，表示最小化到托盘，否，表示退出
				int option = JOptionPane.showConfirmDialog(MyAlarm.this,
						"是否最小化到托盘?", "提示:", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					iconed = true;
					MyAlarm.this.setVisible(false);
				} else {
					AlarmFile.exit();
				}
			}
		});
		// 初始化自定义托盘
		myTray = new MyTray(MyAlarm.this);

	}

	/**
	 * 组件的布局，不要轻易动啊
	 */
	private void omponentLayout() {
		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap(170, Short.MAX_VALUE)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				result)
																		.addContainerGap())
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																mainPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				mainPanelLayout
																						.createSequentialGroup()
																						.addComponent(
																								now,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								483,
																								Short.MAX_VALUE)
																						.addContainerGap())
																		.addGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				mainPanelLayout
																						.createSequentialGroup()
																						.addGroup(
																								mainPanelLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addGroup(
																												mainPanelLayout
																														.createSequentialGroup()
																														.addComponent(
																																dorp_down_hours,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																74,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																dorp_down_minute,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																65,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																drop_down_second,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																62,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																stop))
																										.addGroup(
																												mainPanelLayout
																														.createSequentialGroup()
																														.addComponent(
																																add1)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																add5)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																add30)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																minus1)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																minus5)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																minus30))
																										.addGroup(
																												mainPanelLayout
																														.createSequentialGroup()
																														.addComponent(
																																ring_setup,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																listening_test)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																listening_test_stop)))
																						.addGap(
																								73,
																								73,
																								73))))));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								mainPanelLayout
										.createSequentialGroup()
										.addGap(120, 120, 120)
										.addComponent(result)
										.addGap(24, 24, 24)
										.addComponent(
												now,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(36, 36, 36)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																dorp_down_hours,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																dorp_down_minute,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																drop_down_second,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(stop))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(add1)
														.addComponent(add5)
														.addComponent(add30)
														.addComponent(minus1)
														.addComponent(minus5)
														.addComponent(minus30))
										.addGap(13, 13, 13)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																ring_setup,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																listening_test)
														.addComponent(
																listening_test_stop))
										.addGap(58, 58, 58)));
	}

	/**
	 * 时间监听器，得到系统时间和设置好时间后，得到剩余时间
	 */
	public void timeListener() {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						while (true) {
							try {
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
							}
							initMainPanel();
							now.setText(now());// 得到系统时间
							result.setText(surplus_time());// 得到剩余时间
						}
					}
				}).start();
	}

	/**
	 * 播放声音的监听器
	 */
	public void myListener() {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						while (true) {
							try {
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
							}
							executeSound();// 播放声音
						}
					}
				}).start();
	}

	/**
	 * 获取返回结果
	 * 
	 * @return result值
	 */
	public String surplus_time() {
		String r = null;
		// 获取系统时，分，秒
		int h = getHour();
		int m = getMunite();
		int s = getSecond();
		// 获取设置的响铃时间
		int dh = 0;
		int dm = 0;
		int ds = 0;
		if (dorp_down_hours.getSelectedIndex() != 0) {
			dh = dorp_down_hours.getSelectedIndex() - 1;
		}
		if (dorp_down_minute.getSelectedIndex() != 0) {
			dm = dorp_down_minute.getSelectedIndex() - 1;
		}
		if (drop_down_second.getSelectedIndex() != 0) {
			ds = drop_down_second.getSelectedIndex() - 1;
		}
		int hour = dh - h;
		int min = dm - m;
		int sec = ds - s;
		if (hour == 0) {
			if (min == 0) {
				if (sec == 0) {
					r = "时间到了哦！";
				}
				if (sec < 0) {
					hour += 23;
					min += 59;
					sec += 59;
				}
			}
			if (min < 0) {
				hour += 23;
				if (sec < 0) {
					min -= 1;
					sec += 59;
				}
				min += 60;
			}
			if (min >= 0) {
				if (sec < 0 || sec == 0) {
					min -= 1;
					sec += 59;
				}
				if (sec > 0) {
					// sec=sec;
				}
			}
		}
		if (hour < 0) {
			if (min <= 0) {
				if (sec <= 0) {
					hour -= 1;
					min += 59;
					sec += 59;
				}
			}
			if (min > 0) {
				if (sec <= 0) {
					min -= 1;
					sec += 59;
				}
			}
			hour += 24;
		}
		if (hour > 0) {
			if (min == 0) {
				if (sec <= 0) {
					hour -= 1;
					min += 59;
					sec += 59;
				}
			}
			if (min < 0) {
				if (sec < 0) {
					min -= 1;
					sec += 59;
				}
				min += 60;
				hour -= 1;
			}
			if (min > 0) {
				if (sec < 0 || sec == 0) {
					min -= 1;
					sec += 59;
				}
			}
		}

		if (sec == 30 && min == 0 && hour == 0) {
			setSecondOfResult(sec);
		}
		r = new String("剩:" + hour + "时" + min + "分" + sec + "秒");
		if (hour == 0 && min == 0 && sec < 0) {
			r = "时间到了哦！";
		}
		// result.setText(r);
		return r;
	}

	public int getSecondOfResult() {
		return secondOfResult;
	}

	public void setSecondOfResult(int sec) {
		this.secondOfResult = sec;
	}

	public boolean getFlagOfBackground() {
		return flagOfBackground;
	}

	public void setFlagOfBackground(boolean flag) {
		this.flagOfBackground = flag;
	}

	/**
	 * 时间到了的时候就播放声音
	 */
	public void executeSound() {
		// 获取系统时，分，秒
		int h = getHour();
		int m = getMunite();
		int s = getSecond();
		// 获取设置的响铃时间
		int dh = 0;
		int dm = 0;
		int ds = 0;
		if (dorp_down_hours.getSelectedIndex() != 0) {
			dh = dorp_down_hours.getSelectedIndex() - 1;
		}
		if (dorp_down_minute.getSelectedIndex() != 0) {
			dm = dorp_down_minute.getSelectedIndex() - 1;
		}
		if (drop_down_second.getSelectedIndex() != 0) {
			ds = drop_down_second.getSelectedIndex() - 1;
		}
		int hour = dh - h;
		int min = dm - m;
		int sec = ds - s;
		if (hour == 0 && min == 0 && sec == 0) {
			// 主窗体设置为可见
			setVisible(true);
			// 设置窗口前端显示
			setExtendedState(JFrame.NORMAL);
			setAlwaysOnTop(true);
			// 播放声音
			new Thread(new AlarmSound(ring_setup.getSelectedIndex())).start();
			// 震动
			shack();
		}
	}

	/**
	 * 得到系统时间当前时间，并返回
	 * 
	 * @return 返回系统当前时间
	 */
	public String now() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		// 小于10的时候，在前面加0
		String h = hour < 10 ? "0" : "";
		String m = min < 10 ? "0" : "";
		String s = sec < 10 ? "0" : "";
		String current = new String(h + hour + ":" + m + min + ":" + s + sec);
		return current;
	}

	/**
	 * 布局下面的按钮和下拉选项的监听器
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * 获取dorp_down_hours，dorp_down_minute，drop_down_second的值
		 */
		if (e.getSource() == dorp_down_hours) {
			valueJudgment();
		}
		if (e.getSource() == dorp_down_minute) {
			valueJudgment();
		}
		if (e.getSource() == drop_down_second) {
			valueJudgment();
		}
		if (e.getSource() == stop) {
			stopActionPerformed();
		}
		if (e.getSource() == add1) {
			add1Action();
			myListener();
		}
		if (e.getSource() == add5) {
			add5Action();
			myListener();
		}
		if (e.getSource() == add30) {
			add30Action();
			myListener();
		}
		if (e.getSource() == minus1) {
			minus1Action();
			myListener();
		}
		if (e.getSource() == minus5) {
			minus5Action();
			myListener();
		}
		if (e.getSource() == minus30) {
			minus30Action();
			myListener();
		}
		AlarmSound alarmSound = new AlarmSound(ring_setup.getSelectedIndex());
		Thread alarmThread = new Thread(alarmSound);
		if (e.getSource() == listening_test) {
			// 试听
			if (!alarmThread.isAlive()) {
				alarmThread.start();
			}
			if (running) {
				alarmThread.resume();
			}
			listening_test.setEnabled(false);
		}
		if (e.getSource() == listening_test_stop) {
			// 停止试听
			listening_test.setEnabled(true);
			if (running) {
				alarmThread.suspend();
			}
			running = !running;
		}
		if (e.getSource() == uploadRing) {
			// 上传文件
		}
		if (e.getSource() == exit) {
			// 退出程序
			AlarmFile.exit();
		}
		if (e.getSource() == screenshot) {
			// 主窗体隐藏
			iconed = true;
			MyAlarm.this.setVisible(false);
			// 开始截图
			AlarmTools.screenshot();
		}
		if (e.getSource() == changeBackground) {
			changeBG();
		}
		if (e.getSource() == about) {
			pointX = getMyAlarmX();
			pointY = getMyAlarmY();
			new AlarmHelp("关于程序");
		}
	}

	/**
	 * 更改背景图片</br> 当点击工具-- >更改背景，这里就可以处理更改背景图片
	 */
	private void changeBG() {
		setFlagOfBackground(true);
		if (background < AlarmBackground.getBackgroundListSize()) {
			iconed = true;
			setVisible(false);
			if (background != AlarmBackground.getBackgroundListSize() - 1) {
				++background;
			} else {
				background = 0;
			}
			initMainPanel();
		}
	}

	/**
	 * 点击-30按钮的时候执行的动作
	 */
	private void minus30Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() - 30 < 0) {
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() + 30);// 60-30
			if (dorp_down_hours.getSelectedIndex() == 0) {
				dorp_down_hours.setSelectedIndex(24);// 设置为23
			} else {
				// 小时数-1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() - 1);
			}
		} else {
			// 分钟数-30
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() - 30);
		}
		valueJudgment();
	}

	/**
	 * 点击-5按钮的时候执行的动作
	 */
	private void minus5Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() - 5 < 0) {
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() + 55);// 60-5
			if (dorp_down_hours.getSelectedIndex() == 0) {
				dorp_down_hours.setSelectedIndex(24);// 设置为23
			} else {
				// 小时数-1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() - 1);
			}
		} else {
			// 分钟数-5
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() - 5);
		}
		valueJudgment();
	}

	/**
	 * 点击-1按钮的时候执行的动作
	 */
	private void minus1Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() - 1 == 0) {
			dorp_down_minute.setSelectedIndex(60);// 设置为59
			if (dorp_down_hours.getSelectedIndex() == 0) {
				dorp_down_hours.setSelectedIndex(24);// 设置为23
			} else {
				// 小时数-1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() - 1);
			}
		} else {
			// 分钟数-1
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() - 1);
		}
		valueJudgment();
	}

	/**
	 * 判断dorp_down_hours，dorp_down_minute，drop_down_second当前是否为0，即："关闭"
	 */
	private void isHMSZero() {
		// 如果小时数还没有设置，那么就设置为当前小时数
		if (dorp_down_hours.getSelectedIndex() == 0) {
			dorp_down_hours.setSelectedIndex(getHour() + 1);
		}
		// 如果分钟数还没有设置，那么就设置为当前分钟数
		if (dorp_down_minute.getSelectedIndex() == 0) {
			dorp_down_minute.setSelectedIndex(getMunite() + 1);
		}
		// 如果秒钟还没有设置，那么就设置为当前秒钟
		if (drop_down_second.getSelectedIndex() == 0) {
			drop_down_second.setSelectedIndex(getSecond());
		}
	}

	/**
	 * 点击+30按钮的时候执行的动作
	 */
	private void add30Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() + 30 > 60) {
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() - 30);// +30-60
			if (dorp_down_hours.getSelectedIndex() > 23) {
				dorp_down_hours.setSelectedIndex(1);// 设置为00
			} else {
				// 小时数+1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() + 1);
			}
		} else {
			// 分钟数+30
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() + 30);
		}
		valueJudgment();
	}

	/**
	 * 点击+5按钮的时候执行的动作
	 */
	private void add5Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() + 5 > 60) {
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() - 55);// +5-60
			if (dorp_down_hours.getSelectedIndex() > 23) {
				dorp_down_hours.setSelectedIndex(1);// 设置为00
			} else {
				// 小时数+1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() + 1);
			}
		} else {
			// 分钟数+5
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() + 5);
		}
		valueJudgment();
	}

	/**
	 * 点击+1按钮的时候执行的动作
	 */
	private void add1Action() {
		isHMSZero();
		if (dorp_down_minute.getSelectedIndex() + 1 > 60) {
			dorp_down_minute.setSelectedIndex(1);// 设置为00
			if (dorp_down_hours.getSelectedIndex() > 23) {
				dorp_down_hours.setSelectedIndex(1);// 设置为00
			} else {
				// 小时数+1
				dorp_down_hours.setSelectedIndex(dorp_down_hours
						.getSelectedIndex() + 1);
			}
		} else {
			// 分钟数+1
			dorp_down_minute.setSelectedIndex(dorp_down_minute
					.getSelectedIndex() + 1);
		}
		valueJudgment();
	}

	/**
	 * 给h,m,s三个变量赋值，并判断他们的值
	 */
	private void valueJudgment() {
		h = dorp_down_hours.getSelectedItem().toString();
		m = dorp_down_minute.getSelectedItem().toString();
		s = drop_down_second.getSelectedItem().toString();
		hsmCheck();
	}

	/**
	 * 检查时，分，秒的值,如果都不是"关闭"，那么</br>
	 * 
	 * <li>stop按钮要显示出来</li><br/>
	 * <li>result要显示出来剩余时间</li><br/>
	 * <li>ring_setup要设置为不可用</li> <li>listening_test按钮为不可用</li> <li>
	 * listening_test_stop按钮为不可用</li>
	 */
	private void hsmCheck() {
		if (h != "关闭" && m != "关闭" && s != "关闭") {
			stop.setVisible(true);
			result.setVisible(true);
			ring_setup.setEnabled(false);
			listening_test.setEnabled(false);
			listening_test_stop.setEnabled(false);
		}
	}

	/**
	 * stop按钮的动作： <li>
	 * 把dorp_down_hours，dorp_down_minute，drop_down_second的值设置为"关闭"</li> <li>
	 * 隐藏result</li> <li>ring_setup设置为可用</li> <li>listening_test按钮为可用</li> <li>
	 * listening_test_stop按钮为可用</li> <li>stop按钮设置为不可见</li><li>停止声音播放</li>
	 */
	private void stopActionPerformed() {
		dorp_down_hours.setSelectedIndex(0);
		dorp_down_minute.setSelectedIndex(0);
		drop_down_second.setSelectedIndex(0);
		result.setVisible(false);
		ring_setup.setEnabled(true);
		listening_test.setEnabled(true);
		listening_test_stop.setEnabled(true);
		// 这里要停止声音
		stop.setVisible(false);
	}

	/**
	 * 获取当前小时数
	 * 
	 * @return 返回当前小时数
	 */
	private int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟数
	 * 
	 * @return 返回当前分钟数
	 */
	private int getMunite() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 获取当前秒钟数
	 * 
	 * @return 返回当前秒钟数
	 */
	private int getSecond() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	/**
	 * 实现震动功能
	 */
	public void shack() {
		Point p = MyAlarm.this.getLocation();
		int x = (int) p.getX();
		int y = (int) p.getY();
		for (int i = 0; i < 5000; i++) {
			MyAlarm.this.setLocation(x - 5, y);
			MyAlarm.this.setLocation(x, y + 5);
			MyAlarm.this.setLocation(x + 5, y);
			MyAlarm.this.setLocation(x, y - 5);
		}
		MyAlarm.this.setLocation(x, y);
	}

	/**
	 * 获取MyAlarm的X坐标
	 * 
	 * @return 返回MyAlarm的X坐标
	 */
	public int getMyAlarmX() {
		return (int) getLocation().getX();
	}

	/**
	 * 获取MyAlarm的Y坐标
	 * 
	 * @return 返回MyAlarm的Y坐标
	 */
	public int getMyAlarmY() {
		return (int) MyAlarm.this.getLocation().getY();
	}

	/**
	 * 闹铃类
	 * 
	 * @author Hongten
	 * 
	 * @time 2012-3-2 2012
	 */
	class AlarmSound implements Runnable {
		private String temAlarm;
		private String alarm0Path = "src/image/sound/alarm0.wav";//
		private String alarm1Path = "src/image/sound/alarm1.wav";// 
		private String alarm2Path = "src/image/sound/alarm2.wav";// 
		private String alarm3Path = "src/image/sound/alarm3.wav";// 
		private String alarm4Path = "src/image/sound/alarm4.wav";// 
		private String alarm5Path = "src/image/sound/alarm5.wav";// 
		private String alarm6Path = "src/image/sound/alarm6.wav";// 
		private String alarm7Path = "src/image/sound/alarm7.wav";// 
		private String alarm8Path = "src/image/sound/alarm8.wav";// 

		public AlarmSound(int a) {
			switch (a) {
			case 0:
				temAlarm = alarm0Path;
				break;
			case 1:
				temAlarm = alarm1Path;
				break;
			case 2:
				temAlarm = alarm2Path;
				break;
			case 3:
				temAlarm = alarm3Path;
				break;
			case 4:
				temAlarm = alarm4Path;
				break;
			case 5:
				temAlarm = alarm5Path;
				break;
			case 6:
				temAlarm = alarm6Path;
				break;
			case 7:
				temAlarm = alarm7Path;
				break;
			case 8:
				temAlarm = alarm8Path;
				break;
			}
		}

		// 读取声音文件，并且播放出来
		public void run() {
			File soundFile = new File(temAlarm);
			AudioInputStream audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}
			AudioFormat format = audioInputStream.getFormat();
			SourceDataLine auline = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			try {
				auline = (SourceDataLine) AudioSystem.getLine(info);
				auline.open(format);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			auline.start();
			int nBytesRead = 0;
			byte[] abData = new byte[512];
			try {
				while (nBytesRead != -1) {
					nBytesRead = audioInputStream
							.read(abData, 0, abData.length);
					if (nBytesRead >= 0)
						auline.write(abData, 0, nBytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} finally {
				auline.drain();
				auline.close();
			}

		}
	}
}
