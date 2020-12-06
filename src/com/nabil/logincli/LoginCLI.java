package com.nabil.logincli;
import java.util.*;
/**
 * A CLI-based login system
 * @author Nabil(2440075441
 */
public class LoginCLI {
	static Scanner sc = new Scanner(System.in);
	static String[] availableMenus = {"Login", "Register", "Exit"};
	static String[] availableMemberMenus = {"View Board", "Update Profile", "Delete Profile", "Exit/Logout"};
	static boolean isMenuOpen = true;
	static boolean recentlyDeletingUser = false;
	//  First index refer to user name, second index for the password, and third index for the full name
	static String[] userInSession = {};
	static ArrayList<String[]> members = new ArrayList<String[]>();

	/**
	 * Workflow
	 * @param args
	 * @return {void}
	 */
	public static void main(String[] args) {
		displayingMenu();
		while (isMenuOpen) {
			System.out.print("Type the menu number >>> ");
			int selectedMenu = 0;
			try {
				selectedMenu = sc.nextInt();
			}
			catch (Exception e) {
				selectedMenu = 0;
				sc.next();
			}
			switch(selectedMenu) {
				case 1:
					login();
					break;
				case 2:
					register();
					break;
				case 3:
					System.out.println("Closing the menu ...");
					isMenuOpen = false;
					System.exit(0);
				default: 
					break;
			}
		}
	}
	
	/**
	 * Running Login Interface
	 * @return {void}
	 */
	private static void login() {
		boolean isLoginOpen = true;
		while (isLoginOpen) {
			System.out.print("Username (type exit to back to main menu) : ");
			String inputtedUsername = sc.next();
			//  Handle if user asked to exit
			if (inputtedUsername.equals("exit")) {
				System.out.println("Returning to main menu...");
				displayingMenu();
				isLoginOpen = false;
				return;
			}
			System.out.print("Password : ");
			String inputtedPassword = sc.next();
			//  Handle if given credentials aren't registered yet
			if (!isUserRegistered(inputtedUsername, inputtedPassword)) {
				System.out.println("Incorrect username or password.");
				continue;
			}
			//  Register data
			System.out.println("Successfully logged in!");
			userInSession = getUser(inputtedUsername);
			isLoginOpen = false;
			
		}
	}
	
	/**
	 * Running user registration interface
	 * @return {void}
	 */
	private static void register() {
		System.out.println("New User Registration");
		System.out.println("========================");
		boolean isRegisterOpen = true;
		while (isRegisterOpen) {
			/**=============================
			 * Username Creation Interface
			 * =============================
			 */
			boolean checkUsername = true;
			String username = "";
			while (checkUsername) {
				System.out.print("Create a new username (4-20 characters) : ");
				username = sc.next();
				//  Handle if the user name length is less than minimum or above the maximum
				if (isUsernameOutOfRange(username)) {
					System.out.println("The username must be in range of 4-20 characters!");
					continue;
				}
				//  Handle if inputted user name is not available
				if (isUsernameUnavailable(username)) {
					System.out.println("'" + username + "'" + " is not available. Try something else.");
					continue;
				}
				//  Handle if inputted user name contains symbol
				if (!username.matches("[a-zA-Z0-9.]*")) {
					System.out.println("Username must only contains alphabets and numerics.");
					continue;
				}
				checkUsername = false;	
			}
			/**=============================
			 * Password Creation Interface
			 * =============================
			 */
			boolean checkPassword = true;
			String password = "";
			while (checkPassword) {
				System.out.print("Create a new password (only alphabets) : ");
				password = sc.next();
				//  Handle if password is empty
				if (password.length() <= 0) {
					System.out.println("Password cannot be empty.");
					continue;
				}
				//  Handle if password contains numeric or symbols
				if (!password.matches("[a-zA-Z.]*")) {
					System.out.println("Password must only contains alphabets.");
					continue;
				}
				//  Confirming password must match
				System.out.print("Retype the password as confirmation : ");
				String confirmPassword = sc.next();
				if (!confirmPassword.equals(password)) {
					System.out.println("Password does not match.");
					continue;
				}
				checkPassword = false;
			}
			/**=============================
			 * Fullname Creation Interface
			 * =============================
			 */
			boolean checkFullName = true;
			String fullName = "";
			while (checkFullName) {
				System.out.print("Create a full name (3-25 characters): ");
				fullName = sc.next();
				//  Handle if fullName is out of range
				if ((fullName.length() < 3) || (fullName.length() > 25)) {
					System.out.println("Fullname must be in range of 3-25 characters.");
					continue;
				}
				checkFullName = false;
			}
			//  Register data.
			String[] userData = {username, password, fullName};
			members.add(userData);
		    System.out.flush();
			System.out.println("Successfully registering your account!");
			isRegisterOpen = false;
		}
		sc.nextLine();
	    System.out.println("Press \"ENTER\" to continue...");
	    sc.nextLine();
	    displayingMenu();
	}
	
	/**
	 * Running Member Interface
	 * @return {void}
	 */
	private static void member() {
		displayingMemberMenu();
		boolean isMemberInterfaceOpen = true;
		while (isMemberInterfaceOpen) {
			System.out.print("Type the menu number >>> ");
			int selectedMenu = 0;
			try {
				selectedMenu = sc.nextInt();
			}
			catch (Exception e) {
				selectedMenu = 0;
				sc.next();
			}
			switch(selectedMenu) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					System.out.println("Logging out session...");
					isMemberInterfaceOpen = false;
					break;
			}
		}
	}
	
	private static void deleteAccount() {
		System.out.println("Are you sure you want to delete this account? type \"DELETE\" to confirm : ");
		String confirm = sc.next();
		if (confirm.equals("DELETE")) {
			//  Deleting user's data
			for (int i=0; i<members.size(); i++) {
				String[] user = members.get(i);
				if (user[0].equals(userInSession[0])) {
					members.remove(i);
					Arrays.fill(userInSession, null);;
					System.out.println("This account has successfully deleted and now you will return to the main menu...");
					recentlyDeletingUser = true;
					return;
				}
			}
		}
	}
	
	/**
	 * Check if the inputted username is out of range
	 * @param {String} username
	 * @return {boolean}
	 */
	private static boolean isUsernameOutOfRange(String username) {
		return (username.length() < 4) || (username.length() > 20);
	}
	
	/**
	 * Check if the inputted username is available or not
	 * @param {String} username
	 * @return {boolean}
	 */
	
	private static boolean isUsernameUnavailable(String username) {
		//  Direct return false if registered users are empty
		if (members.size() <= 0) return false;
		for (int i=0; i<members.size(); i++) {
			String[] user = members.get(i);
			//  If found registered user name that matched with inputted user name, then return true
			if (user[0].equals(username)) return true;
		}
		//  If no results are found, then considered as empty and false
		return false;
	}
	
	/**
	 * Check user data availability in the storage
	 * @param {String} username
	 * @param {String} password
	 * @return {boolean}
	 */
	private static boolean isUserRegistered(String username, String password) {
		//  Direct return if registered users are empty
		if (members.size() <= 0) return false;
		for (int i=0; i<members.size(); i++) {
			String[] user = members.get(i);
			//  If user is found with the correct credential, then return true
			if ((user[0].equals(username)) && (user[1].equals(password))) return true;
		}
		//  If no results are found, then considered as user not registered.
		return false;
	}

	/**
	 * Get user from storage
	 * @param {String} username
	 * @return
	 */
	private static String[] getUser(String username) {
		for (int i=0; i<members.size(); i++) {
			String[] user = members.get(i);
			if (user[0].equals(username)) return user;
		}
		//  Handle if no user has been found
		String[] fallback = {};
		return fallback;
	}
	
	/**
	 * Displaying elements in availableMenus.
	 * @return {void}
	 */
	private static void displayingMenu() {
		System.out.println("========================");
		System.out.println(" CLI-based Login System ");
		System.out.println(" Made by 2440075441     ");
		System.out.println("========================");
		int menuSize = availableMenus.length;
		for (int i=0; i<menuSize; i++) {
			System.out.println((i+1) + ". " + availableMenus[i] + (i >= (menuSize-1) ? "\n" : ""));
		}
	}
	
	/**
	 * Displaying elements in availableMemberMenus.
	 * @return {void}
	 */
	private static void displayingMemberMenu() {
		System.out.println("========================");
		System.out.println(" Welcome,");
		System.out.println(userInSession[2] + "!");
		System.out.println("========================");
		int menuSize = availableMemberMenus.length;
		for (int i=0; i<menuSize; i++) {
			System.out.println((i+1) + ". " + availableMenus[i] + (i >= (menuSize-1) ? "\n" : ""));
		}
	}
}
