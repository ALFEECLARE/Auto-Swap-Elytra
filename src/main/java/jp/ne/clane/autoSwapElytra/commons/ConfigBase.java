package jp.ne.clane.autoSwapElytra.commons;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;

public abstract class ConfigBase {
	private Class<? extends ConfigBase> configClass = null;
	private String modId = null;
	
	public ConfigBase(Class<? extends ConfigBase> conClass, String modIdLocal) {
		configClass = conClass;
		modId = modIdLocal;
	}
	
	public void saveConfig(File configFileName) throws IllegalAccessException, IOException {
		List<String> saveStrings = new LinkedList<String>();
		for (Field member : configClass.getFields()) {
			try {
				saveStrings.add(member.getName() + " = " + member.get(configClass).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw e;
			}
		}
		try {
			Files.write(configFileName.toPath(), saveStrings,StandardOpenOption.WRITE,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
				throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadConfig(File configFileName) throws IllegalAccessException,IOException {
		List<String> loadStrings = Files.readAllLines(configFileName.toPath());
		Map<String, String> loadMap = new HashMap<String, String>();
		for (String line : loadStrings) {
			String[] keyValue = line.split("=",2);
			loadMap.putIfAbsent(keyValue[0].strip(), keyValue[1].strip());
		}
		for (Field member : configClass.getFields()) {
			try {
				if (loadMap.containsKey(member.getName())) {
					String value = loadMap.get(member.getName()); 
					member.set(configClass,	switch (member.get(configClass)) {
						case Enum enumValue -> Enum.valueOf((Class<? extends Enum>) member.getDeclaringClass(),value);
						case Boolean boolValue -> Boolean.valueOf(value);
						default -> value;
					});
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw e;
			}
		}
	}
	  
	public File getConfigFile() {
		return getConfigFile(modId);
	}

	public static File getConfigFile(String saveFileName) {
		  return getConfigFile("", saveFileName);
	}

	public static File getConfigFile(String relativePath, String saveFileName) {
		  return getConfigFile(relativePath, saveFileName, "txt");
	}

	public static File getConfigFile(String relativePath, String saveFileName, String extention) {
		  Minecraft mc = Minecraft.getInstance();
		  return Paths.get(mc.gameDirectory.getPath(),"config",relativePath,saveFileName + "." + extention).toFile();
	}
}
