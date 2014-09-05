package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.game.EDiceKit;
import com.taulukko.commons.util.lang.EString;
import com.taulukko.commons.util.web.taglibs.TagInputButton;
import com.taulukko.commons.util.web.taglibs.TagInputPassword;
import com.taulukko.commons.util.web.taglibs.TagLibBase;

public class TagNumKeyboard extends TagLibBase
{

	private String _name;

	private String _clearText = "Clear";

	private String _cancelText = "Cancel";

	private String _buttonID;

	private int _size;

	private String _onClick;

	private String _config;

	private boolean _randomLocation = true;

	private boolean _hasQuit = true;

	private boolean _configOff = false;

	public static void main(String... arg)
	{
		System.out.println("Teste de configuracoes, 1 diferente 1 igual");
		for (int cont = 0; cont < 100; cont++)
		{
			System.out.println(TagNumKeyboard.getConfig(cont));
			System.out.println(TagNumKeyboard.getConfig(cont));

		}

		System.out
				.println("Teste de validacoes, sempre tem que ser verdadeiro");
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "12123434"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "19253740"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "91527304"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1406", "91453068"));

		System.out.println("Teste de validacoes, sempre tem que ser falso");
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "12121212"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "19258740"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1234", "91527308"));
		System.out.println("Validando:"
				+ TagNumKeyboard.validatePassword("1406", "91453078"));

	}

	public String getOnClick()
	{
		return _onClick;
	}

	public void setOnClick(String click)
	{
		_onClick = click;
	}

	public TagNumKeyboard(String name, int size)
	{

		_name = name;
		_size = size;
		_config = getConfig((int)(System.currentTimeMillis()%Integer.MAX_VALUE));

	}
	
	public TagNumKeyboard(String name, int seed, int size)
	{

		_name = name;
		_size = size;
		_config = getConfig(seed);

	}

	public String printBeginTag()
	{
		return "";
	}

	public String printIntoTag()
	{
		String ret = "";
		TagInputPassword txtPassword = new TagInputPassword("txtPassword_"
				+ _name, "");
		txtPassword.setId("txtPassKV" + _name);
		TagInputButton cmdClear = new TagInputButton("cmdClear_" + _name,
				_clearText, 10);
		if (null != _buttonID)
		{
			cmdClear.setId(_buttonID);
		}
		cmdClear.setOnClick("onClearPass" + _name + "()");
		TagInputButton cmdQuit = new TagInputButton("cmdQuit_" + _name,
				_cancelText, 10);
		if (null != _buttonID)
		{
			cmdQuit.setId(_buttonID);
		}
		cmdQuit.setOnClick("onQuitPass" + _name + "()");
		txtPassword.setIsLocked(true);

		if (_configOff)
		{
			for (int cont = 0; cont < 10; cont++)
			{				
				TagInputButton button = new TagInputButton("cmdNumber_" + _name
						+ cont, String.valueOf(cont), 3);
				if (null != _buttonID)
				{
					button.setId(_buttonID);
				}
				button.setOnClick("onSendPass" + _name + "('" + cont + "')");
				ret += "\n" + button.printAllTag();
			}
		}
		else
		{
			// String cfg = "32-16-95-47-80";
			String configs[] = _config.split("-");

			for (int cont = 0; cont < configs.length; cont++)
			{
				EString number12 = new EString(configs[cont]);
				String number1 = number12.left(1).toString();
				String number2 = number12.right(1).toString();
				TagInputButton button = new TagInputButton("cmdNumber_" + _name
						+ cont, number1 + " ou " + number2, 10);
				if (null != _buttonID)
				{
					button.setId(_buttonID);
				}
				button.setOnClick("onSendPass" + _name + "('" + configs[cont]
						+ "')");
				ret += "\n" + button.printAllTag();
			}
		}
		ret += "\n<BR/><BR/>";
		ret += "\nSenha: " + txtPassword.printAllTag();
		ret += "\n<BR/><BR/>";
		ret += (_hasQuit) ? cmdQuit.printAllTag() : "";
		ret += cmdClear.printAllTag();

		if (_randomLocation)
		{
			ret += "\n<script>";

			// randomicamente coloca o teclado proximo do centro
			ret += "\n\nw = (screen.width-300)/2;";
			ret += "\nvariationw = Math.round(Math.random()*200 - 100);";
			ret += "\nvariationh = Math.round(Math.random()*30 - 15);";
			ret += "\nh = (screen.height-350)/2;";
			ret += "\ndiv = document.getElementById(\"" + _name + "\");";
			ret += "\ndiv.style.left=(w+variationw)+\"px\";";
			ret += "\ndiv.style.top=(h+variationh)+\"px\";";
			ret += "\n</script>";
		}
		return ret;
	}

	public String printEndTag()
	{
		return "";
	}

	@Override
	public String printBeginValidation()
	{
		return "";
	}

	@Override
	public String printEndValidation()
	{
		return "";
	}

	@Override
	public String printIntoValidation()
	{
		String ret = "\n<script>";
		ret += "\n\nfunction onQuitPass" + _name + "()";
		ret += "\n{";
		ret += "\ndiv = document.getElementById(\"" + _name + "\");";
		ret += "\ndiv.style.visibility = \"hidden\";";
		ret += "\n}";
		ret += "\n\n\n";
		ret += "\nfunction onClearPass" + _name + "(cfg)";
		ret += "\n{";
		ret += "\n\n\n";
		ret += "\nfield = document.getElementById(\"txtPassKV" + _name + "\")";
		ret += "\nfield.value = \"\";";
		ret += "\n}\n\n";
		ret += "\nfunction onSendPass" + _name + "(cfg)";
		ret += "\n{";
		ret += "\nfield = document.getElementById(\"txtPassKV" + _name + "\")";
		int maxLength = _size * 2;
		if (_configOff)
		{
			maxLength = _size ;
		}
		ret += "\nif(field.value.length >= " + maxLength + ")";
		ret += "\n{";
		ret += "\n     	alert('A senha precisa ter " + _size + " digitos!');";
		ret += "\n		return;";
		ret += "\n}";
		ret += "\n	field.value = field.value + cfg;";
		ret += "\n}";
		ret += "\n</script>";
		return ret;

	}

	public int getSize()
	{
		return _size;
	}

	public void setSize(int size)
	{
		_size = size;
	}

	public String getButtonID()
	{
		return _buttonID;
	}

	public void setButtonID(String buttonID)
	{
		_buttonID = buttonID;
	}

	public String getCancelText()
	{
		return _cancelText;
	}

	public void setCancelText(String cancelText)
	{
		_cancelText = cancelText;
	}

	public String getClearText()
	{
		return _clearText;
	}

	public void setClearText(String clearText)
	{
		_clearText = clearText;
	}

	public String getConfig()
	{
		return _config;
	}

	public void setConfig(String config)
	{
		_config = config;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public boolean getRandomLocation()
	{
		return _randomLocation;
	}

	public void setRandomLocation(boolean randomLocation)
	{
		_randomLocation = randomLocation;
	}

	public static boolean validatePassword(String password, String passwordCfg)
	{
		if (passwordCfg.length() != password.length() * 2)
		{
			return false;
		}

		for (int cont = 0; cont < password.length(); cont++)
		{
			if (password.charAt(cont) != passwordCfg.charAt(cont * 2)
					&& password.charAt(cont) != passwordCfg
							.charAt((cont * 2) + 1))
			{
				return false;
			}

		}

		return true;
	}

	public static String getConfig(int seed)
	{
		String ret = "";
		String pair = "";
		EDiceKit kit = new EDiceKit(1, 10, 0);
		boolean numbers[] = new boolean[10];
		seed = kit.rool(seed);
		while (ret.length() < 14)
		{
			// enquanto a config ainda nao foi completada
			while (numbers[seed - 1])
			{
				// enquanto n�o achar um numero n�o usado
				seed = kit.rool();
			}
			numbers[seed - 1] = true;
			pair += seed - 1;
			seed = kit.rool();
			if (pair.length() == 2)
			{
				// par completo
				if (ret.length() == 0)
				{
					// primeiro par
					ret += pair;
				}
				else
				{
					ret += "-" + pair;
				}
				pair = "";
			}
		}
		return ret;
	}

	public boolean getHasQuit()
	{
		return _hasQuit;
	}

	public void setHasQuit(boolean hasQuit)
	{
		_hasQuit = hasQuit;
	}

	public boolean getConfigOff()
	{
		return _configOff;
	}

	public void setConfigOff(boolean configOff)
	{
		_configOff = configOff;
	}
}
