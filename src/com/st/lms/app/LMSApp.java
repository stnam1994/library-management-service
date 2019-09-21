package com.st.lms.app;

import com.st.lms.service.Service;
import java.util.*;

public class LMSApp {

	private static Scanner scan = new Scanner(System.in);
	private Service DaoService = new Service();

	public static void main(String[] args) {
		LMSApp app = new LMSApp();
		app.start();
	}

	public void start() {
		System.out.println("=============== Library Management Service ===============\n");
		showTitleMenu();
		exitApp();
	}

	public void showTitleMenu() {
		System.out.println("Select from the following options (type the option number):\n");
		System.out.println("[1] Add new...");
		System.out.println("[2] Update existing...");
		System.out.println("[3] Retrieve existing...");
		System.out.println("[4] Remove existing...");
		System.out.println("[5] Exit service\n");

		try {
			int taskChoice = Integer.parseInt(scan.nextLine());

			if (taskChoice == 5)
				exitApp();
			if (taskChoice < 1 || taskChoice > 5)
				throw new NumberFormatException();

			System.out.println();
			System.out.println("[1] Author");
			System.out.println("[2] Book");
			System.out.println("[3] Publisher");
			System.out.println("[4] Exit service\n");

			int objectChoice = Integer.parseInt(scan.nextLine());

			if (objectChoice == 4)
				exitApp();
			if (objectChoice < 1 || objectChoice > 4)
				throw new NumberFormatException();

			DaoService.performTask(taskChoice, objectChoice, scan);

		} catch (NumberFormatException e) {
			System.out.println("Please type a valid option number.\n");
		}

		System.out.println("==========================================================\n");
		showTitleMenu();
	}

	public void exitApp() {
		System.out.println("==========================================================\n");
		System.out.println("Thank you for using the Library Management Service!");
		System.out.println("We hope you have a great day.");
		scan.close();
		System.exit(0);
	}
}
