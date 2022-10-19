// Uploaded to GitHub, was proud of the primitive algorithm on getting date from milliseconds since 1970, Jan 1. Note that this was a textbook exercise.

/* Exercise 10.14: The MyDate Class
 * Design a class called MyDate that contains
 * - data fields year, month, and day representing a date. month should be 0-based
 * - no-arg constructor that creates a MyDate object for the current date
 * - constructor that creates a MyDate object for the current date
 * - constructor that creates a MyDate object with a specified time after januray 1, 1970
 * - constructor that creates a MyDate object with a specified year, month, and day
 * - getter methods for year, month, and day each
 * - method setDate(long elapsedTime) that sets a new date for the object using a the elapsed time
 * 
 * Draw a UML diagram and implement the class.
 * 
 * Write a test program that creates two MyDate objects 
 * use constructors MyDate() and new MyDate(34355555133101L)
 * display the object years, months, and days
 * 
 */

/* UML DIAGRAM
 * NAME
 * MyDate
 * VARIABLES
 * - year: short
 * - month: byte
 * - day: byte
 * METHODS
 * + MyDate()
 * + MyDate(timeElapsed: long)
 * + MyDate(year: int, month: int, day: int)
 * + setDate(timeElapsed: long): void
 * + year(): int
 * + month(): int
 * + day(): int
 */

package TextbookExercises;

/* test program */
public class Exercise_10p14 {
	public static void main(String[] args) {
		MyDate date = new MyDate();
		System.out.println("Today's date: " + date.month() + "/" + date.day() + "/" + date.year());
	}
}

/* MyDate class */
class MyDate {
	// variables
	private short year;
	private byte month;
	byte day;
	
	// constructors
	public MyDate() {
		setDate(System.currentTimeMillis());
	}
	
	public MyDate(int year, int month, int day) {
		this.year = (short) year;
		this.month = (byte) month;
		this.day = (byte) day;
	}
	
	public MyDate(long timeElapsed) {
		setDate(timeElapsed);
	}
	
	// methods
	// set the date based on long milliseconds since 1970, Jan 1
	public void setDate(long timeElapsed) {
		// covert from milliseconds to days
		
		/* count number years */
		short yearCount = 1970;
		long numberDays = timeElapsed / (24 * 60 * 60 * 1000) + 1; // day number 1 + however many other days exist
		
		while (numberDays > 365) {
			// if leap year and days remain
			if (numberDays >= 366 && (yearCount % 4 == 0 && yearCount % 100 != 0) || (yearCount % 400 == 0)) {
				yearCount++;
				numberDays -= 366;
			// else if not leap year and more than a year of days
			} else {
				yearCount++;
				numberDays -= 365;
			}
			
		} 
		
		year = (short) (yearCount);
		
		/* count number months */
		boolean leapYear = (yearCount % 4 == 0 && yearCount % 100 != 0) || (yearCount % 400 == 0);
		byte numberMonths = 1;
		final byte[] numberDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		for (byte i = 0; i < numberDaysInMonth.length; i++) {
			/* if-else tree for day subtraction and month counting */
			// if february
			if (i == 1) {
				// if leap year and days more then february
				if (numberDays > 29 && leapYear)
					numberDays -= numberDaysInMonth[i] + 1;
				// else if not leap year
				else if (numberDays > 28 && !leapYear)
					numberDays -= numberDaysInMonth[i];
				else 
					break;
				
				// move to next iteration if days remain
				numberMonths++;
				continue;
				
			// else if there are 31 days or fewer left and the month is a 31 day month
			} else if (i != 3 && i != 5 && i != 8 && i != 10 && numberDays <= 31) {
				break;
				
			// else if there are 30 days or fewer left and the month is a 30 day month
			} else if (numberDays <= 30) {
				break;
			}
			
			numberDays -= numberDaysInMonth[i];
			numberMonths++;
		}
		
		month = numberMonths;
		
		/* assign remaining days */
		day = (byte)(numberDays);
	}
	
	public int year() {
		return year;
	}
	
	public int month() {
		return month;
	}
	
	public int day() {
		return day;
	}
	
	@Override
	public String toString() {
		return month + "/" + day + "/" + year; 
	}
}