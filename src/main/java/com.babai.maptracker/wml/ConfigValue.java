package wml;

public class ConfigValue<T> extends ConfigEntry {
	private String key;
	private T value;
	
	public ConfigValue(String key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public T getValue() {
		return value;
	}
	
	public String write(int indentLevel) {
		return "\t".repeat(indentLevel)
				+ key
				+ "="
				+ (value instanceof String ? "\"" : "")
				+ value
				+ (value instanceof String ? "\"" : "");
	}
}
