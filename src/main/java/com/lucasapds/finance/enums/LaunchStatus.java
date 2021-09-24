package com.lucasapds.finance.enums;

public enum LaunchStatus {

	PENDING,
	CALLEDOFF,
	MADEEFFECTIVE;

	/*
	 * private int code;
	 * 
	 * private LaunchStatus(int code) { this.code = code; }
	 * 
	 * public int getCode() { return code; }
	 * 
	 * public static LaunchStatus valueOf(int code) { for (LaunchStatus value :
	 * LaunchStatus.values()) { if (value.getCode() == code) { return value; } }
	 * 
	 * throw new IllegalArgumentException("Invalid LaunchStatus code"); }
	 */
}
