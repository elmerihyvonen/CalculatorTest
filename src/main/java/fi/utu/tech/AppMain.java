package fi.utu.tech;

/**
 * Helper class that calls the actual CalculatorApp.
 * Seems strange, but here you can see why this is needed: https://stackoverflow.com/a/57691362
 */
public class AppMain {
	public static void main(String[] args) {
		CalculatorApp.launch(CalculatorApp.class, args);
	}
}