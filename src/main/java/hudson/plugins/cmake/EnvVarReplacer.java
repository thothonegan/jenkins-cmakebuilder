package hudson.plugins.cmake;

import java.util.Map;
import java.util.Set;

public class EnvVarReplacer {
	
	public static String replace(String stringContainingEnvVars, Map<String, String> envVars) {
		Set<String> keys = envVars.keySet();
    	for (String key : keys) {
    		stringContainingEnvVars = 
    			stringContainingEnvVars.replaceAll("\\$" + key, envVars.get(key));

			stringContainingEnvVars =
				stringContainingEnvVars.replaceAll("\\$\\{" + key + "\\}", envVars.get(key)); // bash style ${VAR}. Even though $VARblah works due to previous line, its not nice.
    	}
    	return stringContainingEnvVars;
	}

}
