package userInterface;

import java.util.Scanner;

abstract class SpecificTextMenu {

    abstract public void add();
    abstract public void edit();
    abstract public void delete();
    abstract public void listAll();
    protected Scanner inputScanner;

    public SpecificTextMenu() {
    	inputScanner = new Scanner(System.in);
	}
}