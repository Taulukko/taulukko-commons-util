package com.taulukko.commons.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taulukko.commons.util.io.EFileBufferReader;

public abstract class ConfigBase<T extends ConfigBase<T>> implements Map<String, String> {

	private static Map<Class<?>, ConfigBase<?>> instances = new ConcurrentHashMap<>();

	private Map<String, String> extended = new HashMap<>();

	// //////////////////
	// SERVER//
	// //////////////////

	// Servidor em estado debug imprime mais logs e desliga coisas denecesírias
	// Default: false
	private boolean serverDebug = false;

	// Versao do sistema
	// Default: 0.4
	private String serverVersion = "0.4";

	// Data de Criaíao do sistema
	// Default: 01-08-2008
	private String serverCreated = "01-08-2008";

	// Tempo de espera em ms entre um envio de email e outro
	// Default: 60000 (1 min)
	private int emailSleepTime = 60000;

	// Enable Email, if true thread send emails
	// Default: true
	private boolean emailSendEnabled = true;

	// Show erros in browser. If enabled the browser show errors
	// Default: false
	private // Sugestion: Keep true in developer time
	boolean browserShowJSErrors = true;

	// ID do cluster do servidor
	private String clusterId = "01";

	// ////////
	// LOG//
	// ////////

	// forma de saída do log do root
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	private String rootPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log do root
	// Default: /home/taulukko/logs/root.log
	private String rootPath = "/home/taulukko/logs/root.log";

	// Nível de log do root
	// Default: warning
	private String rootLevel = "warning";

	// forma de saída do log stdOut
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	private String stdOutPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/stdout.log
	private String stdOutPath = "/home/taulukko/logs/stdout.log";

	// Nível de log do root
	// Default: warning
	private String stdOutLevel = "warning";

	// forma de saída do log access
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	private String accessPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log access
	// Default: /home/taulukko/logs/access.log
	private String accessPath = "/home/taulukko/logs/access.log";

	// Nível de log access
	// Default: warning
	private String accessLevel = "warning";

	// forma de saída do log sql
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	private String sqlPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/sql.log
	private String sqlPath = "/home/taulukko/logs/sql.log";

	// Nível de log
	// Default: warning
	private String sqlLevel = "warning";

	// ////////
	// LOG//
	// ////////
	// ////////
	// EMAIL DEFAULT//
	// ////////
	private String email = "example@taulukko.com.br";

	private String emailPassword = "examplePassword";
	private String emailSmtp = "smtp.example.com";

	private List<ConfigObserver> reloadObservers = new CopyOnWriteArrayList<>();

	private URI lastURI = null;

	private long lastSize = 0;

	// Máximos de emails na fila antes de aviso via SMS
	// Padrão: 10
	int emailMaxFIFO = 10;

	private boolean live = true;

	private Thread thread = null;

	private Reloadable<T> reloadable = null;

	protected String projectName;

	protected String realPath;

	ConfigBase(Reloadable<T> reloadable, Class<T> clazz) {
		this.reloadable = reloadable;
		ConfigBase.instances.put(clazz, this);
	}

	// MAP implementation

	@Override
	public int size() {
		return extended.size();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return extended.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return extended.containsValue(value);
	}

	@Override
	public String get(Object key) {
		return extended.get(key);
	}

	@Override
	public String put(String key, String value) {
		return extended.put(key, value);
	}

	@Override
	public String remove(Object key) {
		throw new RuntimeException("Config is read only");
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		extended.putAll(m);
	}

	@Override
	public void clear() {
		throw new RuntimeException("Config is read only");
	}

	@Override
	public Set<String> keySet() {
		return extended.keySet();
	}

	@Override
	public Collection<String> values() {
		return extended.values();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return extended.entrySet();
	}

	@Override
	public int hashCode() {
		return extended.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConfigBase)) {
			return false;
		}
		return extended.equals(obj);
	}

	// end map implementation

	@SuppressWarnings("unchecked")
	public static <T extends ConfigBase<T>> T getInstance(Class<T> clazz) {
		return (T) instances.get(clazz);
	}

	public static <T extends ConfigBase<T>> void startDefault(Class<T> clazz, ConfigBuilder<T> builder,
			String projectName, String realPath) throws Exception {

		T config = builder.createNewConfig();
		if (!realPath.endsWith("/") && !realPath.endsWith("\\")) {
			realPath += "/";
		}

		instances.put(clazz, config);

		config.projectName = projectName;

		ConfigBase.startByURI(clazz, new URI("file:///" + realPath + "config/" + projectName + ".properties"),
				projectName);

		ConfigBase.<T>reloadProperties(clazz);

	}

	public void stopDefault() {
		try {
			this.live = false;
			this.thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static <T extends ConfigBase<T>> void reloadProperties(final Class<T> clazz) {
		final ConfigBase<T> config = getInstance(clazz);
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

						EFileBufferReader reader = new EFileBufferReader(properties.getAbsolutePath());
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
							ConfigBase.<T>startByURI(clazz, config.lastURI, config.projectName);
						}

						for (ConfigObserver observer : config.reloadObservers) {
							observer.after();
						}

					} catch (Exception e) {
						e.printStackTrace();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
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

	public static <T extends ConfigBase<T>> void startByURI(Class<T> clazz, URI uri, String projectname)
			throws Exception {
		final ConfigBase<T> cfg = getInstance(clazz);
		cfg.startByURIInternal(uri, true, false, projectname);
	}

	public void startByURIInternal(URI uri, boolean tryAgain, boolean retryUsingJ2EE, String projectname)
			throws Exception {

		this.lastURI = uri;
		File file = new File(uri);

		if (!file.exists()) {

			System.out.println("[INFO] -  " + projectName + " - Fail load config file from " + uri.toString());

			if (tryAgain | retryUsingJ2EE) {
				if (retryUsingJ2EE) {
					this.startByURIInternal(new URI("file:///" + this.realPath.replace('\\', '/')
							+ "WEB-INF/classes/config/" + this.projectName + ".properties"), false, false, projectName);
				} else {
					this.startByURIInternal(new URI("file:///" + this.realPath.replace('\\', '/') + "config/"
							+ this.projectName + ".properties"), false, true, projectName);
				}
				return;
			} else {
				throw new Exception(uri.toString() + " not found");
			}
		}

		InputStream is = new FileInputStream(file.getAbsolutePath());
		Properties tempProp = new Properties();
		tempProp.load(is);
		is.close();

		Map<String, String> others = extended;

		for (Object key : tempProp.keySet()) {
			others.put(key.toString(), tempProp.getProperty(key.toString(), null));
		}

		String property = getFromMap("accessLevel", others, this.accessLevel);
		if (property != null) {
			this.accessLevel = String.valueOf(property);
		}

		property = getFromMap("clusterId", others, this.clusterId);
		if (property != null) {
			this.clusterId = property;
		}

		property = getFromMap("emailSleepTime", others, String.valueOf(this.emailSleepTime));
		if (property != null) {
			this.emailSleepTime = Integer.valueOf(property);
		}

		property = getFromMap("accessPath", others, this.accessPath);
		if (property != null) {
			this.accessPath = String.valueOf(property);
		}

		property = getFromMap("accessPattern", others, this.accessPattern);
		if (property != null) {
			this.accessPattern = String.valueOf(property);
		}

		property = getFromMap("rootLevel", others, this.rootLevel);
		if (property != null) {
			this.rootLevel = String.valueOf(property);
		}

		property = getFromMap("rootPath", others, this.rootPath);
		if (property != null) {
			this.rootPath = String.valueOf(property);
		}

		property = getFromMap("rootPattern", others, this.rootPattern);
		if (property != null) {
			this.rootPattern = String.valueOf(property);
		}

		property = getFromMap("serverCreated", others, this.serverCreated);
		;
		if (property != null) {
			this.serverCreated = String.valueOf(property);
		}

		property = getFromMap("serverDebug", others, String.valueOf(this.serverDebug));
		;
		if (property != null) {
			this.serverDebug = Boolean.valueOf(property);
		}

		property = getFromMap("serverVersion", others, this.serverVersion);
		;
		if (property != null) {
			this.serverVersion = String.valueOf(property);
		}

		property = getFromMap("stdOutLevel", others, this.stdOutLevel);
		;
		if (property != null) {
			this.stdOutLevel = String.valueOf(property);
		}

		property = getFromMap("stdOutPattern", others, this.stdOutPattern);
		;
		if (property != null) {
			this.stdOutPattern = String.valueOf(property);
		}

		property = getFromMap("stdOutPath", others, this.stdOutPath);
		;
		if (property != null) {
			this.stdOutPath = String.valueOf(property);
		}

		property = getFromMap("sqlLevel", others, this.sqlLevel);
		;
		if (property != null) {
			this.sqlLevel = String.valueOf(property);
		}

		property = getFromMap("sqlPattern", others, this.sqlPattern);
		;
		if (property != null) {
			this.sqlPattern = String.valueOf(property);
		}

		property = getFromMap("sqlPath", others, this.sqlPath);
		;
		if (property != null) {
			this.sqlPath = String.valueOf(property);
		}

		property = getFromMap("emailSendEnabled", others, String.valueOf(this.emailSendEnabled));
		;
		if (property != null) {
			this.emailSendEnabled = Boolean.valueOf(property);
		}

		property = getFromMap("browserShowJSErrors", others, String.valueOf(this.browserShowJSErrors));
		;
		if (property != null) {
			this.browserShowJSErrors = Boolean.valueOf(property);
		}

		property = getFromMap("email", others, this.email);
		;
		if (property != null) {
			this.email = property;
		}

		property = getFromMap("emailPassword", others, this.emailPassword);
		;
		if (property != null) {
			this.emailPassword = property;
		}

		property = getFromMap("emailSmtp", others, this.emailSmtp);
		;
		if (property != null) {
			this.emailSmtp = property;
		}

		property = getFromMap("emailMaxFIFO", others, String.valueOf(this.emailMaxFIFO));
		;
		if (property != null) {
			this.emailMaxFIFO = Integer.parseInt(property);
		}

		if (this.reloadable != null) {
			this.reloadable.reload(this, others);
		}

	}

	private <K, V> V getFromMap(K key, Map<K, V> map, V defaultValue) {
		if (!map.containsKey(key)) {
			return defaultValue;
		}

		return map.get(key);
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

	public Map<String, String> getExtended() {
		return extended;
	}

	public void setExtended(Map<String, String> extended) {
		this.extended = extended;
	}

	public boolean isServerDebug() {
		return serverDebug;
	}

	public void setServerDebug(boolean serverDebug) {
		this.serverDebug = serverDebug;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getServerCreated() {
		return serverCreated;
	}

	public void setServerCreated(String serverCreated) {
		this.serverCreated = serverCreated;
	}

	public int getEmailSleepTime() {
		return emailSleepTime;
	}

	public void setEmailSleepTime(int emailSleepTime) {
		this.emailSleepTime = emailSleepTime;
	}

	public boolean isEmailSendEnabled() {
		return emailSendEnabled;
	}

	public void setEmailSendEnabled(boolean emailSendEnabled) {
		this.emailSendEnabled = emailSendEnabled;
	}

	public boolean isBrowserShowJSErrors() {
		return browserShowJSErrors;
	}

	public void setBrowserShowJSErrors(boolean browserShowJSErrors) {
		this.browserShowJSErrors = browserShowJSErrors;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getRootPattern() {
		return rootPattern;
	}

	public void setRootPattern(String rootPattern) {
		this.rootPattern = rootPattern;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getRootLevel() {
		return rootLevel;
	}

	public void setRootLevel(String rootLevel) {
		this.rootLevel = rootLevel;
	}

	public String getStdOutPattern() {
		return stdOutPattern;
	}

	public void setStdOutPattern(String stdOutPattern) {
		this.stdOutPattern = stdOutPattern;
	}

	public String getStdOutPath() {
		return stdOutPath;
	}

	public void setStdOutPath(String stdOutPath) {
		this.stdOutPath = stdOutPath;
	}

	public String getStdOutLevel() {
		return stdOutLevel;
	}

	public void setStdOutLevel(String stdOutLevel) {
		this.stdOutLevel = stdOutLevel;
	}

	public String getAccessPattern() {
		return accessPattern;
	}

	public void setAccessPattern(String accessPattern) {
		this.accessPattern = accessPattern;
	}

	public String getAccessPath() {
		return accessPath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getSqlPattern() {
		return sqlPattern;
	}

	public void setSqlPattern(String sqlPattern) {
		this.sqlPattern = sqlPattern;
	}

	public String getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}

	public String getSqlLevel() {
		return sqlLevel;
	}

	public void setSqlLevel(String sqlLevel) {
		this.sqlLevel = sqlLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailSmtp() {
		return emailSmtp;
	}

	public void setEmailSmtp(String emailSmtp) {
		this.emailSmtp = emailSmtp;
	}

	public int getEmailMaxFIFO() {
		return emailMaxFIFO;
	}

	public void setEmailMaxFIFO(int emailMaxFIFO) {
		this.emailMaxFIFO = emailMaxFIFO;
	}

}