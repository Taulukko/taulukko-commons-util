package com.taulukko.commons.util.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.lang.EDate;

 abstract class ConfigBase<T extends ConfigBase> implements Map<String, Object> {

	private static Map<Class<? extends ConfigBase>, ConfigBase> instances = new ConcurrentHashMap<>();
	
	
	Map<String, Object> extended = new HashMap<>();

	// //////////////////
	// SERVER//
	// //////////////////

	// Servidor em estado debug imprime mais logs e desliga coisas denecesírias
	// Default: false
	 boolean serverDebug = false;

	// Versao do sistema
	// Default: 0.4
	 String serverVersion = "0.4";

	// Data de Criaíao do sistema
	// Default: 01-08-2008
	 String serverCreated = "01-08-2008";

	// Tempo de espera em ms entre um envio de email e outro
	// Default: 60000 (1 min)
	 int emailSleepTime = 60000;

	// Enable Email, if true thread send emails
	// Default: true
	 boolean emailSendEnabled = true;

	// Show erros in browser. If enabled the browser show errors
	// Default: false
	// Sugestion: Keep true in developer time
	 boolean browserShowJSErrors = true;

	// ID do cluster do servidor
	 String clusterId = "01";

	// ////////
	// LOG//
	// ////////

	// forma de saída do log do root
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	 String rootPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log do root
	// Default: /home/taulukko/logs/root.log
	 String rootPath = "/home/taulukko/logs/root.log";

	// Nível de log do root
	// Default: warning
	 String rootLevel = "warning";

	// forma de saída do log stdOut
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	 String stdOutPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/stdout.log
	 String stdOutPath = "/home/taulukko/logs/stdout.log";

	// Nível de log do root
	// Default: warning
	 String stdOutLevel = "warning";

	// forma de saída do log access
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	 String accessPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log access
	// Default: /home/taulukko/logs/access.log
	 String accessPath = "/home/taulukko/logs/access.log";

	// Nível de log access
	// Default: warning
	 String accessLevel = "warning";

	// forma de saída do log sql
	// Default: [%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n
	// Sugestão para Debug (mais lento): [%d{yyyy-MMM-dd hh:mm}]%-5p[%t](%F:%L)
	// - %m%n
	 String sqlPattern = "[%d{yyyy-MMM-dd hh:mm}]%-5p[%t]- %m%n";

	// Caminho do log
	// Default: /home/taulukko/logs/sql.log
	 String sqlPath = "/home/taulukko/logs/sql.log";

	// Nível de log
	// Default: warning
	 String sqlLevel = "warning";

	// ////////
	// LOG//
	// ////////
	// ////////
	// EMAIL DEFAULT//
	// ////////
	 String email = "taulukko@taulukko.com.br";
	 String emailPassword = "smyghof7";
	 String emailSmtp = "smtp.gmail.com";

	private List<ConfigObserver> reloadObservers = new CopyOnWriteArrayList<ConfigObserver>();

	private URI lastURI = null;

	private long lastSize = 0;

	// Máximos de emails na fila antes de aviso via SMS
	// Padrão: 10
	 int emailMaxFIFO = 10;

	private boolean live = true;

	private Thread thread = null;

	private Reloadable reloadable = null;

	protected String projectName;

	protected String realPath;

	
	  ConfigBase(Reloadable reloadable, Class<T > clazz) {
		this.reloadable = reloadable;
		ConfigBase.instances.put(clazz, this);
	}
	
	// MAP implementation

	@Override
	 int size() {
		return extended.size();
	}

	@Override
	 boolean isEmpty() {
		return false;
	}

	@Override
	 boolean containsKey(Object key) {
		return extended.containsKey(key);
	}

	@Override
	 boolean containsValue(Object value) {
		return extended.containsValue(value);
	}

	@Override
	 Object get(Object key) {
		return extended.get(key);
	}

	@Override
	 Object put(String key, Object value) {
		return extended.put(key, value);
	}

	@Override
	 Object remove(Object key) {
		throw new RuntimeException("Config is read only");
	}

	@Override
	 void putAll(Map<? extends String, ? extends Object> m) {
		extended.putAll(m);
	}

	@Override
	 void clear() {
		throw new RuntimeException("Config is read only");
	}

	@Override
	 Set<String> keySet() {
		return extended.keySet();
	}

	@Override
	 Collection<Object> values() {
		return extended.values();
	}

	@Override
	 Set<Entry<String, Object>> entrySet() {
		return extended.entrySet();
	}

	@Override
	 int hashCode() {
		return extended.hashCode();
	}

	@Override
	 boolean equals(Object obj) {
		if (!(obj instanceof ConfigBase)) {
			return false;
		}
		return extended.equals(obj);
	}

	// end map implementation 
 
	 static <T extends ConfigBase> T getInstance(Class<T> clazz) {
		return (T) instances.get(clazz);
	}

	 static <T extends ConfigBase> void startDefault(Class<T> clazz, ConfigBuilder<T> builder, String projectName,
			String realPath) throws Exception {

		T config = builder.createNewConfig();
		if (!realPath.endsWith("/") && !realPath.endsWith("\\")) {
			realPath += "/";
		}

		ConfigBase.instances.put(clazz, config);
		config.projectName = projectName;
		config.realPath = realPath;

		ConfigBase.startByURI(clazz,
				new URI("file:///" + realPath + String.format("config/%s.properties", projectName)), projectName);

		ConfigBase.<T>reloadProperties(clazz);

	}

	 void stopDefault() {
		try {
			this.live = false;
			this.thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static <T extends ConfigBase> void reloadProperties(final Class<T> clazz) {
		final ConfigBase config = getInstance(clazz);
		config.thread = new Thread(new Runnable() {
			@Override
			 void run() {

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

	static void startByURI(Class<T> clazz, URI uri, String projectname) throws Exception {
		T cfg = getInstance(clazz);
		cfg.startByURIInternal(uri, true, false, projectname);
	}

	void startByURIInternal(URI uri, boolean tryAgain, boolean retryUsingJ2EE, String projectname) throws Exception {

		this.lastURI = uri;
		File file = new File(uri);

		if (!file.exists()) {

			System.out.println("[INFO] -  " + projectName + " - Fail load config file from " + uri.toString());

			if (tryAgain | retryUsingJ2EE) {
				if (retryUsingJ2EE) {
					this.startByURIInternal(new URI("file:///" + this.realPath.replace('\\', '/') + "WEB-INF/classes/config/"
							+ this.projectName + ".properties"), false, false, projectName);
				} else {
					this.startByURIInternal(new URI("file:///" + this.realPath.replace('\\', '/') + "config/" + this.projectName
							+ ".properties"), false, true, projectName);
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

		Map<String, Object> others = extended;

		for (Object key : tempProp.keySet()) {
			others.put(key.toString(), tempProp.getProperty(key.toString(), null));
		}

		def property = others.get("accessLevel");
		if (property != null) {
			this.accessLevel = String.valueOf(property);
		}

		property = others.get("clusterId");
		if (property != null) {
			this.clusterId = property;
		}

		property = others.get("emailSleepTime");
		if (property != null) {
			this.emailSleepTime = Integer.valueOf(property);
		}

		property = others.get("accessPath");
		if (property != null) {
			this.accessPath = String.valueOf(property);
		}

		property = others.get("accessPattern");
		if (property != null) {
			this.accessPattern = String.valueOf(property);
		}

		property = others.get("rootLevel");
		if (property != null) {
			this.rootLevel = String.valueOf(property);
		}

		property = others.get("rootPath");
		if (property != null) {
			this.rootPath = String.valueOf(property);
		}

		property = others.get("rootPattern");
		if (property != null) {
			this.rootPattern = String.valueOf(property);
		}

		property = others.get("serverCreated");
		if (property != null) {
			this.serverCreated = String.valueOf(property);
		}

		property = others.get("serverDebug");
		if (property != null) {
			this.serverDebug = Boolean.valueOf(property);
		}

		property = others.get("serverVersion");
		if (property != null) {
			this.serverVersion = String.valueOf(property);
		}

		property = others.get("stdOutLevel");
		if (property != null) {
			this.stdOutLevel = String.valueOf(property);
		}

		property = others.get("stdOutPattern");
		if (property != null) {
			this.stdOutPattern = String.valueOf(property);
		}

		property = others.get("stdOutPath");
		if (property != null) {
			this.stdOutPath = String.valueOf(property);
		}

		property = others.get("sqlLevel");
		if (property != null) {
			this.sqlLevel = String.valueOf(property);
		}

		property = others.get("sqlPattern");
		if (property != null) {
			this.sqlPattern = String.valueOf(property);
		}

		property = others.get("sqlPath");
		if (property != null) {
			this.sqlPath = String.valueOf(property);
		}

		property = others.get("emailSendEnabled");
		if (property != null) {
			this.emailSendEnabled = Boolean.valueOf(property);
		}

		property = others.get("browserShowJSErrors");
		if (property != null) {
			this.browserShowJSErrors = Boolean.valueOf(property);
		}

		property = others.get("email");
		if (property != null) {
			this.email = property;
		}

		property = others.get("emailPassword");
		if (property != null) {
			this.emailPassword = property;
		}

		property = others.get("emailSmtp");
		if (property != null) {
			this.emailSmtp = property;
		}

		property = others.get("emailMaxFIFO");
		if (property != null) {
			this.emailMaxFIFO = Integer.parseInt(property);
		}

		if (this.reloadable != null) {
			this.reloadable.reload(this, others);
		}

	}

	 void save(String propertie, String value) throws Exception {

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

	 void addObserver(String type, ConfigObserver observer) {

		if (observer != null && type.toLowerCase().equals("reload")) {
			this.reloadObservers.add(observer);
		}
	}

	 boolean removeObserver(String type, ConfigObserver observer) {
		if (observer != null && type.toLowerCase().equals("reload")) {
			return this.reloadObservers.remove(observer);
		}
		return false;
	}

	 void clearObserver(String type) {
		if (type.toLowerCase().equals("reload")) {
			this.reloadObservers.clear();
		}
	}

	  
}