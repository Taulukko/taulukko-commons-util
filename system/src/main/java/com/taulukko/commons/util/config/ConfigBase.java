package com.taulukko.commons.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.lang.EDate;

public abstract class ConfigBase {

	private static Map<Class<? extends ConfigBase>, ConfigBase> instances = new ConcurrentHashMap<>();

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

	private Reloadable reloadable = null;

	protected String projectName;

	protected String realPath;

	public <T extends ConfigBase> ConfigBase(Reloadable reloadable,
			Class<T> clazz) {
		this.reloadable = reloadable;
		ConfigBase.instances.put(clazz, this);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ConfigBase> T getInstance(Class<T> clazz) {
		return (T) instances.get(clazz);
	}

	public static <T extends ConfigBase> void startDefault(Class<T> clazz,
			ConfigBuilder<T> builder, String projectName, String realPath)
			throws Exception {

		T config = builder.createNewConfig();
		if (!realPath.endsWith("/") && !realPath.endsWith("\\")) {
			realPath += "/";
		}

		ConfigBase.instances.put(clazz, config);
		config.projectName = projectName;
		config.realPath = realPath;

		ConfigBase.startByURI(
				clazz,
				new URI("file:///" + realPath
						+ String.format("config/%s.properties", projectName)));

		ConfigBase.<T> reloadProperties(clazz);

	}

	public void stopDefault() {
		try {
			this.live = false;
			this.thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static <T extends ConfigBase> void reloadProperties(
			final Class<T> clazz) {
		final ConfigBase config = getInstance(clazz);
		config.thread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (config.live) {
					try {
						Thread.sleep(5000);
						if (!config.live) {
							return;
						}

						if (config.lastURI == null) {
							continue;
						}
						File properties = new File(config.lastURI);

						EFileBufferReader reader = new EFileBufferReader(
								properties.getAbsolutePath());
						String content = reader.toString();
						int size = content.length();
						reader.close();

						if (!properties.exists() || config.lastSize == size) {
							continue;
						}

						config.lastSize = size;

						info("Reloading config [" + config.projectName + "]...");

						for (ConfigObserver observer : config.reloadObservers) {
							observer.before();
						}

						synchronized (config.lastURI) {
							ConfigBase.<T> startByURI(clazz, config.lastURI);
						}

						for (ConfigObserver observer : config.reloadObservers) {
							observer.after();
						}

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
		config.thread.setPriority(Thread.MIN_PRIORITY);
		config.thread.start();
	}

	private static void info(String value

	) {
		System.out.println(value);
	}

	static <T extends ConfigBase> void startByURI(Class<T> clazz, URI uri)
			throws Exception {
		ConfigBase config = getInstance(clazz);
		config.startByURI(uri, true, false);
	}

	private void startByURI(URI uri, boolean tryAgain, boolean retryUsingJ2EE)
			throws Exception {

		this.lastURI = uri;
		File file = new File(uri);

		if (!file.exists()) {

			if (tryAgain | retryUsingJ2EE) {
				if (retryUsingJ2EE) {
					this.startByURI(
							new URI("file:///"
									+ this.realPath.replace('\\', '/')
									+ "WEB-INF/classes/config/"
									+ this.projectName + ".properties"), false,
							false);
				} else {
					this.startByURI(
							new URI("file:///"
									+ this.realPath.replace('\\', '/')
									+ "config/" + this.projectName
									+ ".properties"), false, true);
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
			this.accessLevel = String.valueOf(property);
		}

		property = properties.getProperty("clusterId", null);
		if (property != null) {
			this.clusterId = property;
		}

		property = properties.getProperty("emailSleepTime", null);
		if (property != null) {
			this.emailSleepTime = Integer.valueOf(property);
		}

		property = properties.getProperty("accessPath", null);
		if (property != null) {
			this.accessPath = String.valueOf(property);
		}

		property = properties.getProperty("accessPattern", null);
		if (property != null) {
			this.accessPattern = String.valueOf(property);
		}

		property = properties.getProperty("rootLevel", null);
		if (property != null) {
			this.rootLevel = String.valueOf(property);
		}

		property = properties.getProperty("rootPath", null);
		if (property != null) {
			this.rootPath = String.valueOf(property);
		}

		property = properties.getProperty("rootPattern", null);
		if (property != null) {
			this.rootPattern = String.valueOf(property);
		}

		property = properties.getProperty("serverCreated", null);
		if (property != null) {
			this.serverCreated = String.valueOf(property);
		}

		property = properties.getProperty("serverDebug", null);
		if (property != null) {
			this.serverDebug = Boolean.valueOf(property);
		}

		property = properties.getProperty("serverVersion", null);
		if (property != null) {
			this.serverVersion = String.valueOf(property);
		}

		property = properties.getProperty("stdOutLevel", null);
		if (property != null) {
			this.stdOutLevel = String.valueOf(property);
		}

		property = properties.getProperty("stdOutPattern", null);
		if (property != null) {
			this.stdOutPattern = String.valueOf(property);
		}

		property = properties.getProperty("stdOutPath", null);
		if (property != null) {
			this.stdOutPath = String.valueOf(property);
		}

		property = properties.getProperty("sqlLevel", null);
		if (property != null) {
			this.sqlLevel = String.valueOf(property);
		}

		property = properties.getProperty("sqlPattern", null);
		if (property != null) {
			this.sqlPattern = String.valueOf(property);
		}

		property = properties.getProperty("sqlPath", null);
		if (property != null) {
			this.sqlPath = String.valueOf(property);
		}

		property = properties.getProperty("emailSendEnabled", null);
		if (property != null) {
			this.emailSendEnabled = Boolean.valueOf(property);
		}

		property = properties.getProperty("browserShowJSErrors", null);
		if (property != null) {
			this.browserShowJSErrors = Boolean.valueOf(property);
		}

		property = properties.getProperty("email", null);
		if (property != null) {
			this.email = property;
		}

		property = properties.getProperty("emailPassword", null);
		if (property != null) {
			this.emailPassword = property;
		}

		property = properties.getProperty("emailSmtp", null);
		if (property != null) {
			this.emailSmtp = property;
		}

		property = properties.getProperty("emailMaxFIFO", null);
		if (property != null) {
			this.emailMaxFIFO = Integer.parseInt(property);
		}

		if (this.reloadable != null) {
			this.reloadable.reload(this, properties);
		}

	}

	public void save(String propertie, String value) throws Exception {

		File file = new File(this.lastURI);
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

	public void addObserver(String type, ConfigObserver observer) {

		if (observer != null && type.toLowerCase().equals("reload")) {
			this.reloadObservers.add(observer);
		}
	}

	public boolean removeObserver(String type, ConfigObserver observer) {
		if (observer != null && type.toLowerCase().equals("reload")) {
			return this.reloadObservers.remove(observer);
		}
		return false;
	}

	public void clearObserver(String type) {
		if (type.toLowerCase().equals("reload")) {
			this.reloadObservers.clear();
		}
	}

	public static interface ConfigObserver {
		public void before();

		public void after();
	}
}