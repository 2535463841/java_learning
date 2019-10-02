package org.zbw.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


class RandomWord{
	final static String LOWER_WORDS = "abcdefghijklmnopqrstuvwxyz";
	char[] words;
	Random random;
	
	public RandomWord() {
		this.words = LOWER_WORDS.toCharArray();
		this.random = new Random();
	}
	
	public String create(int minLength, int maxLength) {
		int randomLength;
		if (minLength == maxLength) {
			randomLength = minLength;
		}else {
			randomLength = (int) (minLength + this.random.nextInt(maxLength - minLength +1));
		}
		String word = "";
		for (int i = 0; i < randomLength; i++) {
			word += this.words[this.random.nextInt(this.words.length)];
		}
		return word;
	}
}


abstract class BaseRandomWordsCreater extends RandomWord{
	int minLength;
	int maxLength;
	int row;
	int column;
	int createdNum;
	HashMap<Integer, Integer> details;

	public BaseRandomWordsCreater(int minLength, int maxLength, int row, int column) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.row = row;
		this.column = column;
		this.details = new HashMap<Integer, Integer>();
	}

	public void printDetails() {
		System.out.println("Detail of words:");
		System.out.println("======================");
		System.out.println("Length Num");
		for (Integer length : this.details.keySet()) {
			System.out.println(String.format("%6s %s", length, this.details.get(length)));
		}
		System.out.println("======================");
	}
	public void createWords() {
		for (int r = 0; r < this.row; r++) {
			ArrayList<String> words = new ArrayList<String>();
			for (int c = 0; c < this.column; c++) {
				String word = this.create(this.minLength, this.maxLength);
				words.add(word);
				this.createdNum ++;
				if (details.containsKey(word.length())) {
					details.put(word.length(), details.get(word.length()) + 1);
				}else {
					details.put(word.length(), 1);
				}
			}
			this.dealWord(words);
		}
	}
	public void start() {
		this.createWords();
		this.finish();
	}
	public void finish() {
		this.printDetails();
	}
	abstract void dealWord(ArrayList<String> words);
}


class RandomWordsCreaterOutput extends BaseRandomWordsCreater{
	final static String SPLIT_SPACE = " ";
	final static String SPLIT_COMMA = ",";
	final static String SPLIT_TAB = "\t";
	String split;
	
	public RandomWordsCreaterOutput(int minLength, int maxLength, int row, int column) {
		super(minLength, maxLength, row, column);
		this.split = " ";
	}
	public RandomWordsCreaterOutput(int minLength, int maxLength, int row, int column, String split) {
		super(minLength, maxLength, row, column);
		this.split = split;
	}
	@Override
	void dealWord(ArrayList<String> words) {
		System.out.println(String.join(this.split, words));
	}
}


class RandomWordsCreaterFile extends RandomWordsCreaterOutput{
	String output;
	BufferedWriter bufferedWriter;
	Thread processThread;
	public RandomWordsCreaterFile(int minLength, int maxLength, int row, int column, String output) throws IOException {
		super(minLength, maxLength, row, column);
		this.output = output;
		this.bufferedWriter = new BufferedWriter(new FileWriter(this.output));
	}
	public RandomWordsCreaterFile(int minLength, int maxLength, int row, int column, String split, String output) throws IOException {
		super(minLength, maxLength, row, column, split);
		this.output = output;
		this.bufferedWriter = new BufferedWriter(new FileWriter(this.output));
	}
	public void showProcess() {
		int total = this.row * this.column;
		this.processThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					double percent = createdNum * 100.0 / total;
					System.out.print(String.format("[%s] %.2f%%\r", ">".repeat((int)percent) , percent ));
					if (percent >= 100) {
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println();
			}
		});
		this.processThread.setDaemon(true);
		this.processThread.start();
	}
	
	@Override
	public void start() {
		this.showProcess();
		super.start();
	}
	@Override
	public void finish() {
		try {
			this.processThread.join();
			this.bufferedWriter.close();
			this.printDetails();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	void dealWord(ArrayList<String> words) {
		try {
			this.bufferedWriter.write(String.join(this.split, words));
			this.bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


public class RandomWordsCreater {
	final static int DEFAULT_ROW = 100;
	final static int DEFAULT_COLUMN = 10;
	final static String DEFAULT_SPLIT= " ";
	
	public static void main(String[] args) throws IOException, ParseException {
		CommandLineParser parser = new BasicParser();
		
		Options options = new Options();
		options.addOption("h", "help", false, "print help message");
		options.addOption("m", "min-lenth", true, "the min length of word");
		options.addOption("M", "max-lenth", true, "the max length of word");
		options.addOption("r", "row", true, "the row num of words to create(default is " + DEFAULT_ROW + ")");
		options.addOption("c", "column", true, "the column num of words to create(default is " + DEFAULT_COLUMN + ")");
		options.addOption("o", "output", true, "save to output file, if not set, print to console,(default is not set)");
		options.addOption("s", "split", true, "the split string in words(default is '" + DEFAULT_SPLIT + "')");
		
		CommandLine commandLine = parser.parse(options, args);
		if (commandLine.hasOption("h") || !commandLine.hasOption("m") || !commandLine.hasOption("M")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("Usage of RandomWordsCreater", options);
			System.exit(1);
		}
		int minLength = Integer.valueOf(commandLine.getOptionValue("m"));
		int maxLength = Integer.valueOf(commandLine.getOptionValue("M"));
		if (maxLength < minLength) {
			System.err.println("参数错误，单词最大长度 必须 >= 最小长度");
			System.exit(1);
		}

		BaseRandomWordsCreater creater;
		if (commandLine.hasOption("o")) {
			creater = new RandomWordsCreaterFile(
					minLength, maxLength,
					Integer.valueOf(commandLine.getOptionValue("r", String.valueOf(DEFAULT_ROW))),
					Integer.valueOf(commandLine.getOptionValue("c", String.valueOf(DEFAULT_COLUMN))),
					commandLine.getOptionValue("s", DEFAULT_SPLIT), commandLine.getOptionValue("o"));
		}else {
			creater = new RandomWordsCreaterOutput(
					minLength, maxLength,
					Integer.valueOf(commandLine.getOptionValue("r", String.valueOf(DEFAULT_ROW))),
					Integer.valueOf(commandLine.getOptionValue("c", String.valueOf(DEFAULT_COLUMN))),
					commandLine.getOptionValue("s", DEFAULT_SPLIT));
		}
		creater.start();
	}
}
