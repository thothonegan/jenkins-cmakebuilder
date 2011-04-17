package hudson.plugins.cmake;

import java.io.IOException;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;

public class CmakeLauncher {

	private final Launcher launcher;
	private final EnvVars envs;
	private final FilePath workSpace;
	private final BuildListener listener;
	private final String buildDir;

	public CmakeLauncher(Launcher launcher, 
			EnvVars envs, 
			FilePath workSpace, 
			BuildListener listener, 
			String buildDir) {
		super();
		this.launcher = launcher;
		this.envs = envs;
		this.workSpace = workSpace;
		this.listener = listener;
		this.buildDir = buildDir;
	}
	
	public boolean launchCmake(String cmakeCall) throws IOException, InterruptedException {
		int result = this.launcher.launch(cmakeCall, this.envs, 
				this.listener.getLogger(), new FilePath(this.workSpace, this.buildDir)).join();
		return (result == 0);
	}
	
	public boolean launchMake(String makeCommand) throws IOException, InterruptedException {
		if (makeCommand.trim().isEmpty()) {
			return true;
		}
		makeCommand = 
			EnvVarReplacer.replace(makeCommand, envs);
		int result = this.launcher.launch(makeCommand, this.envs, 
				this.listener.getLogger(), new FilePath(this.workSpace, this.buildDir)).join();
		return (result == 0);
	}
	
	public boolean launchInstall(String installDir, String installCommand) throws IOException, InterruptedException {
		final boolean doInstall = 
			!installDir.isEmpty() && !installCommand.trim().isEmpty();
		if (!doInstall) {
			return true;
		}
		installCommand = 
			EnvVarReplacer.replace(installCommand, this.envs);
		int result = this.launcher.launch(installCommand, this.envs, 
				this.listener.getLogger(), new FilePath(this.workSpace, this.buildDir)).join();
		return (result == 0);
	}
}
