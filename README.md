# Generato
Generato - it is Test helper that generates method to generate test values for Entity

Write to console needed fumctions for generate class

forexample you have several nested classes such
```java
    class Device{
        private int id;
        private String type;
        private String name;

        public int getId() {return id;}
        public void setId(int id) {this.id = id;}
        public String getType() {return type;}
        public void setType(String type) {this.type = type;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
    }

    class DeviceWrapper{
        private long id;
        private Device device;
        private boolean active;

        public long getId() {return id;}
        public void setId(long id) {this.id = id;}
        public Device getDevice() {return device;}
        public void setDevice(Device device) {this.device = device;}
        public boolean isActive() {return active;}
        public void setActive(boolean active) {this.active = active;}
    }

    class Settings{
        private int timeout;
        private String host;
        private int port;

        public int getTimeout() {return timeout;}
        public void setTimeout(int timeout) {this.timeout = timeout;}
        public String getHost() {return host;}
        public void setHost(String host) {this.host = host;}
        public int getPort() {return port;}
        public void setPort(int port) {this.port = port;}
    }
    
    class System1{
        DeviceWrapper deviceWrapper;
        String name;
        Settings settings;

        public DeviceWrapper getDeviceWrapper() {return deviceWrapper;}
        public void setDeviceWrapper(DeviceWrapper deviceWrapper) {this.deviceWrapper = deviceWrapper;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public Settings getSettings() {return settings;}
        public void setSettings(Settings settings) {this.settings = settings;}
    }
```	
	then you want to create new System object for tests then you need to write generate functions.
	
	With this project you can automate this routine in next 
```java	
	Generato.generateFunctions(System.class);
```	
	and in output you will see next functions:
	
```java
	public static Settings generateSettings(int id){
		Settings settings = new Settings();
		settings.setPort(id);
		settings.setTimeout(id);
		settings.setHost("host"+id);
		return settings;
	}
	public static Device generateDevice(int id){
		Device device = new Device();
		device.setName("name"+id);
		device.setType("type"+id);
		device.setId(id);
		return device;
	}
	public static DeviceWrapper generateDeviceWrapper(int id){
		DeviceWrapper devicewrapper = new DeviceWrapper();
		devicewrapper.setId((long)id);
		devicewrapper.setDevice(generateDevice(id));
		devicewrapper.setActive(id%2==0);
		return devicewrapper;
	}
	public static System1 generateSystem1(int id){
		System1 system1 = new System1();
		system1.setName("name"+id);
		system1.setSettings(generateSettings(id));
		system1.setDeviceWrapper(generateDeviceWrapper(id));
		return system1;
	}
```	
	Works with:
	int
	long
	float
	double
	List<>
	String
	boolean
	and your custom classes
	Required min java 1_8