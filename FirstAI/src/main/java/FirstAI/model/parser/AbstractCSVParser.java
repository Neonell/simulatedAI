package FirstAI.model.parser;

import java.io.Serializable;

public abstract class AbstractCSVParser<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public AbstractCSVParser() {
		super();
	}

	public T parse(String line) {
		return parse(line.split(";"));
	}
	
	public abstract T parse(String[] tokens);
	
	/**
	 * 
	 * @param in
	 * @return the string without the " " or null if empty
	 */
	protected String toString(String in) {
		if (notEmpty(in)) {
			if(in.startsWith("\"") && in.endsWith("\"") || in.startsWith("'") && in.endsWith("'")){
				in = in.substring(1,in.length()-1);
			}
			return in;
		}
		return null;
	}
	
	protected boolean notEmpty(String in) {
		if (in == null || in.trim().length() == 0 || in.equals("null") || in.equals("\"\"")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Concatenates a number of fields into a string that's suitable for writing
	 * to the log
	 */
	protected String toDebugString(String[] fields) {
		if (fields.length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder("{");
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			sb.append("; ");
			sb.append(fields[i]);
		}
		return sb.append("}").toString();
	}
}

