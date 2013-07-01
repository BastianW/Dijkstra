package de.bwvaachen.graph.logic.algorithm;

public class ConsoleLogger implements ILogger {

	public ConsoleLogger() {
		resetLogger();
	}

	@Override
	public void writeLine(String line) {

		System.out.println(line.replace(Character.toString('\u221E'), "Inf"));
	}

	@Override
	public void resetLogger() {
		System.out
				.println("-------------------------------------------------------------------------------------------");

	}
}
