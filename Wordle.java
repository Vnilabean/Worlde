import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
// Worlde Game by Phil "Vnilabean"
public class Wordle {

	/**
	 * Takes a input for a file and returns a string array with each word in the file as a separate array position
	 * @param fileName
	 * @return a array containing all the words from a file as items in the array
	 * @throws FileNotFoundException
	 */
	public static String[] wordsFromFile(String fileName) throws FileNotFoundException {
		String filename = fileName;
		File file = new File(filename);
		Scanner s = new Scanner(file);
		int lines = s.nextInt();
		int counter = 0;
		String[] wordList = new String[lines];
		while (s.hasNextLine()) {
			if (s.hasNext()) {
				wordList[counter] = s.next();
				counter++;
			}

		}
		s.close();
		return wordList;
	}

	/**
	 * picks a random word from the wordsFromFile method
	 * @param array
	 * @return random word string
	 */
	public static String pickRandomWord(String[] array) {
		Random generator = new Random();
		int arrayLength = array.length;
		int num = generator.nextInt(arrayLength);
		String randomWord = array[num];
		return randomWord;
	}

	/**
	 * chacks to see if a word is in a array
	 * @param word
	 * @param words
	 * @return true if word is in array and false otherwise
	 */
	public static boolean wordInArray(String word, String[] words) {

		for (String i : words) {
			if (word.equals(i)) {
				return true;
			}

		}
		return false;
	}

	
	/** takes in a user guess and checks if it is present in the array and it is 5 characters long
	 * @param array
	 * @return string of the input guess
	 */
	public static String getUserGuess(String[] array) {
		Scanner inputScanner = new Scanner(System.in);
		String input = null;
		boolean condition = false;
		while (condition == false) {
			System.out.print("Please enter a 5 letter word: ");
			input = inputScanner.next();
			if (input.length() == 5) {
				if (wordInArray(input, array) == true) {
					condition = true;

					return input;
				} else {
					condition = false;

				}

			}
		}
		return "error";

	}

	/**
	 * checks if a letter is present in a given word
	 * @param letter
	 * @param word
	 * @return true if is present, false if not
	 */
	public static boolean letterInWord(char letter, String word) {
		int length = word.length();
		for (int i = 0; i < length; i++) {
			if (word.charAt(i) == letter) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * takes a user input and compares it with the provided array.
	 * @param array
	 * @return the input as capital for letter in correct place, lowercase for letter in
	 *  incorrect place but present in word, and a dash for neither.
	 */
	public static void displayMatching(String guess, String secretWord) {
		
		char[] guessLetters = guess.toCharArray();
		char[] secretWordLetters = secretWord.toCharArray();
		int pos = 0;
		String returnString = "";
		for (char i : guessLetters) {
			if (i == secretWordLetters[pos]) {
				returnString = returnString + Character.toUpperCase(i);
				pos++;
			} else if (letterInWord(i, secretWord) == true) {
				// int indexof = Arrays.asList(guessLetters).indexOf(i);
				returnString = returnString + i;
				pos++;
			} else if (letterInWord(i, secretWord) == false) {
				returnString = returnString + "-";
				pos++;
			}

		}
		System.out.println(returnString);

	}

	/**
	 * main function that takes other functions together to make a wordle game
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String[] words = wordsFromFile("words.txt");
			String secretWord = pickRandomWord(words);
			// sets lives
			int lives = 6;
			//tests for lives > 0  then askes for word and checks if it matches secretWord
			while (lives > 0) {
				String guess = getUserGuess(words);
				displayMatching(guess, secretWord);
				if (guess.equals(secretWord)) {
					System.out.println("You Win!");
					break;
				}
				lives--;
				System.out.println("You have " + lives + " lives left");
			}
			//fails if lives goes to 0
			if (lives == 0) {
				System.out.println("You Lose :(");
				System.out.println("The word was " + secretWord);
			}
		} catch (FileNotFoundException e) {
			System.out.println("unable to find file");
		}

	}

}
