package com.taulukko.commons.util.window.util;

import java.awt.Toolkit;
import java.awt.Window;

import com.taulukko.commons.util.game.EDiceKit;

public class Effects
{

	public static void centeralized(Window window)
	{
		window.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
						- (window.getWidth() / 2), (Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2)
						- (window.getHeight() / 2));
	}

	public static void randomize(Window window)
	{

		int height = Toolkit.getDefaultToolkit().getScreenSize().height
				- window.getHeight();

		int width = Toolkit.getDefaultToolkit().getScreenSize().width
				- window.getWidth();

		//randomize
		height = EDiceKit.rool(1, height, 0);
		width = EDiceKit.rool(1, width, 0);

		window.setLocation(width, height);
	}
}
