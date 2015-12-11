package com.trotter.analytics;

public class Environment {

	private enum ENVIRONMENT_MODE {
		PRODUCTION,
		BETA,
		DEVELOPMENT
	}

	private static ENVIRONMENT_MODE environmentMode = ENVIRONMENT_MODE.DEVELOPMENT;

	public static Boolean isProdMode() { return environmentMode == ENVIRONMENT_MODE.PRODUCTION; }
	public static Boolean isBetaMode() { return environmentMode == ENVIRONMENT_MODE.BETA; }
	public static Boolean isDevMode() { return environmentMode == ENVIRONMENT_MODE.DEVELOPMENT; }

	public static Boolean shouldCheckForAdWordsInstall() {
		switch (environmentMode) {
			case PRODUCTION: return false;
			case BETA: return false;
			case DEVELOPMENT: return true;
			default: return false;
		}
	}

}