package com.taulukko.commons.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.lang.EDate;

public abstract class ConfigBase {

	private static ConfigBase instance = null;

	// //////////////////
	// SERVER//
	// //////////////////

	// Servidor em estado debug imprime mais logs e desliga coisas denecesírias
	// Default: false
	public boolean serverDebug = false;

	// Versao do sistema
	// Default: 0.4
	public String serverVersion = "0.4";

	// Data de Criaíao do sistema
	// Default: 01-08-2008
	public String serverCreated = "01-08-2008";

	// Tempo de espera em ms entre um envio de email e outro
	// Default: 60000 (1 min)
	public int emailSleepTime = 60000;

	// Enable Email, if true thread send emails
	// Default: true
	public boolean emailSendEnabled = true;

	// Show erros in browser. If enabled the browser show errors
	// Default: false
	// Sugestion: Keep true in developer time
	public boolean browserShowJSErrors = true;

	// ID do cluster do servidor
	public String clusterId = "01";

	// taulukkoApiUrl
	public String taulukkoApiUrl = "http://localhost:8081/version/1.0/";

	// ////////
	// LOG//
	// ////////

	// forma de saída do log do root
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	public String rootPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log do root
	// Default: /home/taulukko/logs/root.log
	public String rootPath = "/home/taulukko/logs/root.log";

	// Nível de log do root
	// Default: warning
	public String rootLevel = "warning";

	// forma de saída do log stdOut
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	public String stdOutPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/stdout.log
	public String stdOutPath = "/home/taulukko/logs/stdout.log";

	// Nível de log do root
	// Default: warning
	public String stdOutLevel = "warning";

	// forma de saída do log access
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	public String accessPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log access
	// Default: /home/taulukko/logs/access.log
	public String accessPath = "/home/taulukko/logs/access.log";

	// Nível de log access
	// Default: warning
	public String accessLevel = "warning";

	// forma de saída do log sql
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	public String sqlPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/sql.log
	public String sqlPath = "/home/taulukko/logs/sql.log";

	// Nível de log
	// Default: warning
	public String sqlLevel = "warning";

	// ////////
	// LOG//
	// ////////
	// ////////
	// EMAIL DEFAULT//
	// ////////
	public String email = "taulukko@taulukko.com.br";
	public String emailPassword = "smyghof7";
	public String emailSmtp = "smtp.gmail.com";

	private List<ConfigObserver> reloadObservers = new CopyOnWriteArrayList<ConfigObserver>();

	private URI lastURI = null;

	private long lastSize = 0;

	// Máximos de emails na fila antes de aviso via SMS
	// Padrão: 10
	public int emailMaxFIFO = 10;

	private boolean live = true;

	private Thread thread = null;

	private static Reloadable reloadable = null;

	private static String projectName;

	private static String realPath;

	private static boolean j2ee = false;

	public ConfigBase(Reloadable reloadable, boolean j2ee) {
		ConfigBase.reloadable = reloadable;
		ConfigBase.j2ee = j2ee;
		ConfigBase.instance = this;

	}

	@SuppressWarnings("unchecked")
	public static <T extends ConfigBase> T getInstance() {
		return (T) instance;
	}

	public static void startDefault(ConfigBase configBase, String projectName,
			String realPath) {
		ConfigBase.instance = configBase;
		ConfigBase.projectName = projectName;
		ConfigBase.realPath = realPath;
		try {
			if (j2ee) {
				ConfigBase.startByURI(new URI("file:///"
						+ realPath
						+ String.format("WEB-INF/classes/config/%s.properties",
								projectName)));
			} else {
				ConfigBase.startByURI(new URI("file:///" + realPath
						+ String.format("config/%s.properties", projectName)));
			}
			reloadProperties();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopDefault() {
		try {
			getInstance().live = false;
			getInstance().thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void reloadProperties() {
		getInstance().thread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (getInstance().live) {
					try {
						Thread.sleep(5000);

						if (getInstance().lastURI == null) {
							continue;
						}
						File properties = new File(getInstance().lastURI);

						EFileBufferReader reader = new EFileBufferReader(
								properties.getAbsolutePath());
						String content = reader.toString();
						int size = content.length();
						reader.close();

						if (!properties.exists()
								|| getInstance().lastSize == size) {
							continue;
						}

						getInstance().lastSize = size;

						info("Reloading config [" + ConfigBase.projectName
								+ "]...");

						for (ConfigObserver observer : getInstance().reloadObservers) {
							observer.before();
						}

						synchronized (getInstance().lastURI) {
							startByURI(getInstance().lastURI);
						}

						for (ConfigObserver observer : getInstance().reloadObservers) {
							observer.after();
						}

						info("Config [" + ConfigBase.projectName + "] reloaded");

					} catch (Exception e) {
						e.printStackTrace();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}, "Config Service");
		getInstance().thread.setPriority(Thread.MIN_PRIORITY);
		getInstance().thread.start();
	}

	private static void info(String value

	) {
		System.out.println(value);
	}

	static void startByURI(URI uri) throws Exception {
		startByURI(uri, true);
	}

	private static void startByURI(URI uri, boolean tryAgain) throws Exception {

		getInstance().lastURI = uri;
		File file = new File(uri);

		if (!file.exists()) {

			if (tryAgain) {
				if (j2ee) {
					startByURI(new URI("file:///" + realPath.replace('\\', '/')
							+ "/WEB-INF/classes/config/" + projectName
							+ ".properties"), false);
				} else {
					startByURI(new URI("file:///" + realPath.replace('\\', '/')
							+ "/config/" + projectName + ".properties"), false);
				}
				return;
			} else {
				throw new Exception(uri.toString() + " not found");
			}
		}

		Properties properties = new Properties();

		InputStream is = new FileInputStream(file.getAbsolutePath());
		properties.load(is);
		is.close();

		String property = properties.getProperty("accessLevel", null);
		if (property != null) {
			getInstance().accessLevel = String.valueOf(property);
		}

		property = properties.getProperty("clusterId", null);
		if (property != null) {
			getInstance().clusterId = property;
		}

		property = properties.getProperty("emailSleepTime", null);
		if (property != null) {
			getInstance().emailSleepTime = Integer.valueOf(property);
		}

		property = properties.getProperty("accessPath", null);
		if (property != null) {
			getInstance().accessPath = String.valueOf(property);
		}

		property = properties.getProperty("accessPattern", null);
		if (property != null) {
			getInstance().accessPattern = String.valueOf(property);
		}

		property = properties.getProperty("rootLevel", null);
		if (property != null) {
			getInstance().rootLevel = String.valueOf(property);
		}

		property = properties.getProperty("rootPath", null);
		if (property != null) {
			getInstance().rootPath = String.valueOf(property);
		}

		property = properties.getProperty("rootPattern", null);
		if (property != null) {
			getInstance().rootPattern = String.valueOf(property);
		}

		property = properties.getProperty("serverCreated", null);
		if (property != null) {
			getInstance().serverCreated = String.valueOf(property);
		}

		property = properties.getProperty("serverDebug", null);
		if (property != null) {
			getInstance().serverDebug = Boolean.valueOf(property);
		}

		property = properties.getProperty("serverVersion", null);
		if (property != null) {
			getInstance().serverVersion = String.valueOf(property);
		}

		property = properties.getProperty("stdOutLevel", null);
		if (property != null) {
			getInstance().stdOutLevel = String.valueOf(property);
		}

		property = properties.getProperty("stdOutPattern", null);
		if (property != null) {
			getInstance().stdOutPattern = String.valueOf(property);
		}

		property = properties.getProperty("stdOutPath", null);
		if (property != null) {
			getInstance().stdOutPath = String.valueOf(property);
		}

		property = properties.getProperty("sqlLevel", null);
		if (property != null) {
			getInstance().sqlLevel = String.valueOf(property);
		}

		property = properties.getProperty("sqlPattern", null);
		if (property != null) {
			getInstance().sqlPattern = String.valueOf(property);
		}

		property = properties.getProperty("sqlPath", null);
		if (property != null) {
			getInstance().sqlPath = String.valueOf(property);
		}

		property = properties.getProperty("emailSendEnabled", null);
		if (property != null) {
			getInstance().emailSendEnabled = Boolean.valueOf(property);
		}

		property = properties.getProperty("browserShowJSErrors", null);
		if (property != null) {
			getInstance().browserShowJSErrors = Boolean.valueOf(property);
		}

		property = properties.getProperty("email", null);
		if (property != null) {
			getInstance().email = property;
		}

		property = properties.getProperty("emailPassword", null);
		if (property != null) {
			getInstance().emailPassword = property;
		}

		property = properties.getProperty("emailSmtp", null);
		if (property != null) {
			getInstance().emailSmtp = property;
		}

		property = properties.getProperty("emailMaxFIFO", null);
		if (property != null) {
			getInstance().emailMaxFIFO = Integer.parseInt(property);
		}

		property = properties.getProperty("taulukkoApiUrl", null);
		if (property != null) {
			getInstance().taulukkoApiUrl = property;
		}

		if (reloadable != null) {
			reloadable.reload(getInstance(), properties);
		}

	}

	public static void save(String propertie, String value) throws Exception {
		File file = new File(getInstance().lastURI);
		if (!file.exists()) {
			throw new Exception("File not exist");
		}

		EFile efile = new EFile(file);
		EDate edate = new EDate();

		efile.copyTo(file.getAbsolutePath() + ".bkp" + edate.getCodeDate());

		Properties properties = new Properties();

		InputStream is = new FileInputStream(file.getAbsolutePath());
		properties.load(is);
		properties.setProperty(propertie, value);
		properties.store(new FileOutputStream(file), "AUTOMATIC");

	}

	public static void addObserver(String type, ConfigObserver observer) {
		if (observer != null && type.toLowerCase().equals("reload")) {
			getInstance().reloadObservers.add(observer);
		}
	}

	public static boolean removeObserver(String type, ConfigObserver observer) {
		if (observer != null && type.toLowerCase().equals("reload")) {
			return getInstance().reloadObservers.remove(observer);
		}
		return false;
	}

	public static void clearObserver(String type) {
		if (type.toLowerCase().equals("reload")) {
			getInstance().reloadObservers.clear();
		}
	}

	public static interface ConfigObserver {
		public void before();

		public void after();
	}
}