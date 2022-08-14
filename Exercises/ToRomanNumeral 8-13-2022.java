/* Personal exercise: convert given numbers into Roman numerals
 * 
 * Notes on Roman numerals:
 * 		Conversion chart
 * 		I : 1
 * 		V : 5
 * 		X : 10
 * 		L : 50
 * 		C : 100
 * 		D : 500
 * 		M : 1000
 * 
 * Roman numerals are arranged in a specific way to denote specific numbers.
 * If a numeral is placed after a greater than or of equal-value numeral, the values are added (LXX => 50 + 10 + 10 = 70)
 * If a numeral is placed before a numeral of greater value, the value will be subtracted (IX => (-1) + 10 = 9)
 * 
 * rules:
 * 		1 - When certain numerals are repeated, the number is represented by their sum (III => 1 + 1 + 1 = 3)
 * 		2 - No Roman numerals may appear together more than three times (IIII is invalid)
 * 		3 - Letters V, L, and D are never repeated
 * 		4 - Only I, X, and C may be used to subtract
 * 		5 - When a numeral is placed after a numeral of greater value, the result is additive (XV => 10 + 5 = 15)
 * 		6 - When a numeral is placed before a numeral of greater value, the result is subtractive (IX => (-1) + 10 = 9)
 * 		7 - When a smaller numeral appears between two numerals of greater values, it is automatically subtractive with the last numeral (XIX => 10 + ((-1) + 10) = 19)
 * 		8 - to multiply a number by a factor of 1000, a bar is placed over the number
 * 		9 - Roman numerals do not follow a place-value system (no decimal points)
 * 		10 - There is no Roman numeral for the number 0
 * 		11 - only powers of ten may be subtracted from the next two higher digits
 * 			only I, X, and C can be subtracted and
 * 				I can only subtract V and X
 * 				X can only subtract L and C
 * 				C can only subtract D and M
 * 
 * Souces:
 * 		https://www.cuemath.com/numbers/roman-numerals/
 * 		https://www.crosswordunclued.com/2010/06/roman-numerals.html#:~:text=The%20Classic%20Roman%20Numeral%20Mistake,I%20(1)%20%3D%2049.
 * 
 * write a program that can:
 * 		- prevent the user from trying to convert values <= 0
 * 		- convert at least any number from 0 - 1000 to Roman numerals
 */

/* UML DIAGRAM
 * -------------------------
 * NAME: RomanNumeral
 * -------------------------
 * VARIABLES:
 * + value: final int
 * + numeral: final String
 * -------------------------
 * METHODS
 * + RomanNumeral(int value)
 * + _toNumeral(int value): String romanNumeral
 */

/* client class */
public class ToRomanNumeral {
	// main method
	public static void main(String[] args) {
		// set up main variables
		java.util.Scanner input = new java.util.Scanner(System.in);
		RomanNumeral romanNumeral;
		
		// greeting
		System.out.println("Welcome to the Roman Numeral converter!");
		
		// offer choice
		System.out.println("Would you like to print every number from 1 to 3999? (yes/no)");
		String userInput = input.next();
		while (!userInput.toLowerCase().equals("yes") && !userInput.toLowerCase().equals("no")) {
			System.out.println("Sorry, I did not quite catch that, let's try again.");
			System.out.println("Would you like to print every number from 1 to 3000? (yes/no)");
			userInput = input.next();
		}
		
		// tree
		if (userInput.toLowerCase().equals("yes")) {
			System.out.println("bounds (lower upper): "); short lowerBound = input.nextShort(); short upperBound = input.nextShort();
			for (short i = lowerBound; i <= upperBound; i++) {
				romanNumeral = new RomanNumeral(i);
				System.out.printf("value : %-5d | numeral : %-10s\n", romanNumeral.value, romanNumeral.numeral);
			}
		} else {
			// main loop
			do {
				// ask for number to convert
				System.out.print("Enter a number to convert to Roman Numeral: "); romanNumeral = new RomanNumeral(input.nextInt());
				
				// print results
				System.out.print("Results:"
									+ String.format("\n%20s: %d", "Value", romanNumeral.value)
									+ String.format("\n%20s: %s", "Roman Numeral", romanNumeral.numeral)
									+ "\n");
				
				// prompt for continuation
				System.out.print("Enter \"new\" to continue enter another number: ");
			} while (input.next().toLowerCase().equals("new"));
		}
		// farewell
		System.out.println("Goodbye");
	}
}

/* class to hold Roman numeral value */
class RomanNumeral {
	/* class variables */
	public final int value;
	public final String numeral;
	
	/* constructors */
	public RomanNumeral(int value) {
		this.value = value;
		this.numeral = toNumeral(value);
	}
	
	/* methods */
	// convert numbers to numerals
	public static String toNumeral(int value) {
		// if input is less than 1 or greater than 3999, throw exception
		if (value < 1) {
			throw new ArithmeticException("value for numeral less than 1");
		} else if (value > 3999) {
			throw new ArithmeticException("value is too large (greater than 3999)");
		}
		
		// main variables
		String numeral = "";
		
		// continue to the main program
		// follow through, step-down wise
		// if the value is greater than 1000
		while (value >= 1000) {
			numeral += "M";
			value -= 1000;
		}
		// if the value is greater than 100
		if (value >= 100) {
			// if the value is greater than 900
			if (value >= 900) {
				numeral += "CM";
				value -= 900;
			} else {
				// else if the value is less than 900
				// if the value is between 400 - 499
				if (400 <= value && value < 500) {
					numeral += "CD";
					value -= 400;
				} else {
					// else if the value is not between 400 - 499
					// if the value is greater than 500
					if (value >= 500) {
						numeral += "D";
						value -= 500;
					}
					while (value >= 100) {
						numeral += "C";
						value -= 100;
					}
				}
			}
		}
		// if the value is greater than 10
		if (value >= 10) {
			// if the value is greater than 50
			// if the value is greater than 90
			if (value >= 90) {
				numeral += "XC";
				value -= 90;
			} else {
				// else if the value is less than 80
				// if the value is between 40 - 49
				if (40 <= value && value < 50) {
					numeral += "XL";
					value -= 40;
				} else {
					// else 
					// if the value is greater than 50
					if (value >= 50) {
						numeral += "L";
						value -= 50;
					}
					while (value >= 10) {
						numeral += "X";
						value -= 10;
					}
				}
			}
		
		}
		// if the value is greater than 1
		if (value >= 1) {
			// if the value is 9
			if (value == 9) {
				numeral += "IX";
			} else {
				// else if the value is less than 9
				// if the value is 4
				if (value == 4) {
					numeral += "IV";
					value -=4;
				} else {
					// else if the value is greater than 5
					if (value >= 5) {
						// if the value is greater than 5
						numeral += "V";
						value -= 5;
						}
					}

					while (value >= 1) {
						numeral += "I";
						value -= 1;
					}
				}
			}
		
		return numeral;
	}
}
