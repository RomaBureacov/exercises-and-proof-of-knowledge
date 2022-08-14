/*
 * Roman Bureacov
 * CSCI 142 Java Programming I
 * Instructor:	Syeda Nizami
 * Project: Draw an image using DrawingPanel and one of java's graphics packages
 * After finishing the image, the program will prompt the use to save the image
 */

import java.util.Scanner;
import java.awt.*;
import java.io.*;

public class FinalDrawingPanel_RomanBureacov {
//main method
	public static void main(String[] args) {
		//print to console
		System.out.println("A spider!");
		
		//draw the image
			//set canvas and graphics
		DrawingPanel canvas = new DrawingPanel(1000, 1000);
		Graphics graphics = canvas.getGraphics();
			//draw aspects
		drawBackground(canvas, graphics);
		drawWebbing(canvas, graphics);
		drawSpider(canvas, graphics);
		drawSignature(canvas, graphics);
		
		//prompt the user to save image
		Scanner get = new Scanner(System.in);
		System.out.println("Would you like to save this image (yes/no)? (Dimensions: 1000x1000)");
		//print error if improper input
		String userInput = get.next().toLowerCase();
		System.out.println();
		while (!(userInput.equals("yes")) && !(userInput.equals("no"))) {
			System.out.println("Improper input.");
			System.out.println("Would you like to save this image (yes/no)? (Dimensions: 1000x1000)");
			userInput = get.next().toLowerCase();
			get.nextLine();
			System.out.println();
		}
		if (userInput.equals("yes")) {
			//prompt for directory to save in
			System.out.println("Please provide a directory:");
			get.nextLine(); //consume the newline character from previous input, made after hitting the "enter" key
			String fileName = get.nextLine();
			canvas.save(fileName);
			System.out.println();
			//loop while image has failed to save, based on filename string provided being non-existent
			while (!((new File(fileName)).exists())) {
				System.out.println("Image failed to save, let's try again.");
				System.out.println("Please provide a directory:");
				fileName = get.nextLine();
				canvas.save(fileName);
				System.out.println();
			}
			System.out.println("Done!");
		}
		//tell the user farewell and exit program
		System.out.print("Thanks for viewing my image!");
		System.exit(0);
	}
//secondary methods
	//draw background
	public static void drawBackground(DrawingPanel canvas, Graphics graphics) {
		//set static background color
		canvas.setBackground(Color.BLACK);
		//draw background randomized line elements
		graphics.setColor(Color.WHITE);
		for (byte lines = 0; lines < 25; lines++) {
			//set initial variables
			short beginx = 0;
			short beginy = 0;
			short beginSide = 0;
			//set randomized begin point
				//randomize if beginning on the opposing side
			if (Math.round(Math.random() * 2) % 2 == 0) beginSide = 1000; 
				//randomize if on x- or y-axis and where on the respective axis
			if (Math.round(Math.random() * 2) % 2 == 0) {
				beginx = (short)(Math.random() * 1000);
				beginy = beginSide;
			} else {
				beginy = (short)(Math.random() * 1000);
				beginx = beginSide;
			}
			
			//loop to draw the line
			graphics.setColor(new Color(150, 0, 0));
			for (byte moves = 0; moves < Math.round(Math.random() * 50 + 20); moves++) {
				//make the end coordinates a move from the beginning coordinates
				//set initial additive coordinates
				byte lineLength = (byte)(Math.random() * 100);
				byte endx = lineLength;
				byte endy = lineLength;
				//randomize if the additive coordinates are positive or negative
				if (Math.round(Math.random() * 2) % 2 == 0) endx = (byte)(-endx);
				if (Math.round(Math.random() * 2) % 2 == 0) endy = (byte)(-endy);
				//if the end coordinates add up to go past the canvas bounds, negate the additive coordinate
				if (beginx + endx < 0 || 1000 < beginx + endx) endx = (byte)(-endx);
				if (beginy + endy < 0 || 1000 < beginy + endy) endy = (byte)(-endy);
				//Draw the line with the new coordinates
				graphics.drawLine(beginx, beginy, beginx + endx, beginy + endy);
				//set the end coordinates as the new beginning coordinates
				beginx = (short)(beginx + endx);
				beginy = (short)(beginy + endy);
			}
		}
	}
	//draw "webbing"
	public static void drawWebbing(DrawingPanel canvas, Graphics graphics) {
		//initialize color
		graphics.setColor(Color.GRAY);
		//initial variables
		byte maxLines = 50;
		short maxInfluence = 100;
		short maxLineLength = 275;
		short maxLineDecrease = 100;
		//for every angle partition, draw a line
		for (short degrees = 0; degrees < 360; degrees += (short)(360 / (maxLines))) {
			//for every length partition
				//initialize beginning position and variables
			short beginx = 500;
			short beginy = 500;
			short addx = 0;
			short addy = 0;
			for (short lineLength = maxLineLength, influence = maxInfluence; lineLength > 0; lineLength -= maxLineDecrease, influence -= maxLines / 10) {
				addx = (short)(Math.round(lineLength * Math.cos(Math.toRadians(degrees - 10 * influence))));
				addy = (short)(Math.round(lineLength * Math.sin(Math.toRadians(degrees - 10 * influence))));
				graphics.drawLine(beginx, beginy, beginx + addx, beginy + addy);
				beginx = (short)(beginx + addx);
				beginy = (short)(beginy + addy);
			}
		}
		
		//for every angle partition, draw a line, now in the reverse direction
		for (short degrees = 0; degrees < 360; degrees += (short)(360 / (maxLines))) {
			//for every length partition
				//initialize beginning position and variables
			short beginx = 500;
			short beginy = 500;
			short addx = 0;
			short addy = 0;
			for (short lineLength = maxLineLength, influence = maxInfluence; lineLength > 0; lineLength -= maxLineDecrease, influence -= maxLines / 10) {
				addx = (short)(Math.round(lineLength * Math.cos(Math.toRadians(degrees - 10 * influence))));
				addy = (short)(Math.round(lineLength * Math.sin(Math.toRadians(degrees - 10 * influence))));
				graphics.drawLine(beginx, beginy, beginx - addx, beginy + addy);
				beginx = (short)(beginx - addx);
				beginy = (short)(beginy + addy);
			}
		}
	}
	
	//draw Spider
	public static void drawSpider(DrawingPanel canvas, Graphics graphics) {
		//create polygon that will be the spider
		Polygon body = new Polygon();
		Polygon appendagesL = new Polygon();
		Polygon appendagesR = new Polygon();
			//create arrays that house the x and y coordinates
		short[] bodyCordx = {500, 496, 485, 481, 482, 477, 478,
							481, 473, 466, 462, 463, 470, 479,
							487, 474, 464, 456, 455, 457, 465,
							475, 486, 492, 492, 495, 498, 498,
							500};
		short[] bodyCordy = {431, 424, 424, 430, 436, 442, 453,
							458, 467, 477, 489, 503, 520, 531,
							533, 544, 562, 585, 609, 634, 655, 
							670, 679, 691, 723, 726, 724, 688,
							689};
		
		short[] appendagesCordx = {481, 478, 477, 470, 470, 467,
									467, 460, 449, 439, 435, 437,
									444, 457, 457, 460, 465, 457,
									436, 410, 406, 344, 340, 310,
									304, 276, 270, 268, 263, 261,
									269, 298, 299, 331, 331, 394,
									395, 426, 446, 457, 434, 427,
									422, 359, 351, 342, 332, 328,
									274, 267, 230, 226, 185, 176,
									176, 221, 222, 259, 262, 322,
									325, 327, 347, 391, 438, 445,
									454, 448, 438, 407, 371, 353,
									349, 343, 282, 278, 234, 172,
									164, 167, 221, 224, 232, 253,
									272, 275, 341, 350, 359, 373,
									377, 397, 443, 451, 455, 464,
									465, 456, 448, 447, 442, 439,
									415, 390, 388, 378, 377, 372,
									350, 350, 342, 315, 315, 321,
									352, 356, 361, 361, 365, 390,
									395, 398, 422, 439, 457, 466,
									473, 474, 487, 479, 470, 463,
									462, 466, 473, 481};
		short[] appendagesCordy = {458, 453, 447, 436, 407, 399,
									389, 380, 379, 384, 390, 404,
									410, 408, 428, 436, 450, 443,
									413, 376, 373, 276, 273, 218,
									212, 154, 145, 139, 139, 144,
									161, 213, 219, 276, 283, 379,
									386, 435, 462, 474, 469, 465,
									465, 442, 445, 436, 431, 427,
									391, 390, 356, 356, 324, 321,
									330, 362, 365, 398, 403, 442,
									443, 447, 457, 480, 490, 494,
									495, 500, 502, 506, 521, 520,
									522, 520, 521, 523, 513, 509,
									512, 519, 522, 525, 524, 531,
									532, 536, 536, 537, 536, 534,
									537, 535, 520, 519, 521, 517,
									519, 527, 538, 542, 544, 550,
									578, 617, 625, 637, 652, 660,
									729, 739, 820, 870, 879, 877,
									819, 772, 755, 744, 733, 656,
									647, 632, 609, 590, 567, 551,
									546, 544, 533, 531, 520, 503,
									489, 477, 467, 458};
			//create the spider polygon
				//create body
					//create left portion
		for (short index = 0; index < bodyCordx.length; index++) {
			body.addPoint(bodyCordx[index], bodyCordy[index]);
		}
					//create right portion by mirroring the x-coordinate
		for (short index = (short)(bodyCordx.length - 1); index >= 0; index--) {
			body.addPoint(1000 - bodyCordx[index], bodyCordy[index]);
		}
		
				//create appendages
					//create left appendages
		for (short index = 0; index < appendagesCordx.length; index++) {
			appendagesL.addPoint(appendagesCordx[index], appendagesCordy[index]);
		}
					//create right appendages by mirroring left appendages
		for (short index = (short)(appendagesCordx.length - 1); index >= 0; index--) {
			appendagesR.addPoint(1000 - appendagesCordx[index], appendagesCordy[index]);
		}
		
		//draw spider polygon
			//fill spider polygon
		graphics.setColor(new Color(75, 40, 0));
		graphics.fillPolygon(body);
		graphics.setColor(new Color(50, 20, 0));
		graphics.fillPolygon(appendagesL);
		graphics.fillPolygon(appendagesR);
		
			//create outline
		graphics.setColor(Color.GRAY);
		graphics.drawPolygon(body);
		graphics.drawPolygon(appendagesL);
		graphics.drawPolygon(appendagesR);
		
		//draw details
			//draw eyes
		graphics.setColor(Color.BLACK);
		graphics.fillOval(490, 436, 5, 5);
		graphics.fillOval(1000 - 490 - 5, 436, 5, 5);
		graphics.fillOval(494, 442, 5, 5);
		graphics.fillOval(1000 - 494 - 5, 442, 5, 5);
		graphics.fillOval(496, 434, 5, 5);
		graphics.fillOval(1000 - 496 - 5, 434, 5, 5);
		
	}
	
	//draw signature
	public static void drawSignature(DrawingPanel canvas, Graphics graphics) {
		graphics.setColor(Color.GRAY);
		graphics.drawString("Roman Bureacov", 10, 979);
		graphics.drawString("CSCI 142, JAVA 2", 10, 990);
	}
}
