package userInterface;

import java.util.List;

import dataAccessObject.PersonDataAccessObject;
import model.Person;

public class PersonTextMenu extends SpecificTextMenu{

	 private PersonDataAccessObject personDataAccessObject;

	    public PersonTextMenu() {
	        super();
	        personDataAccessObject = new PersonDataAccessObject();
	    }

	    private int getPersonId() {
	        System.out.print("Escolha o id da pessoa: ");
	        int PersonId = inputScanner.nextInt();
	        inputScanner.nextLine();

	        return PersonId;
	    }

	    private Person getPersonData(Person InformedPerson) {
	        Person person;

	        if (InformedPerson == null) {
	        	person = new Person();
	        } else {
	        	person = InformedPerson;
	        }

	        System.out.print("Digite o nome da pessoa: ");
	        String personName = inputScanner.nextLine();

	        System.out.print("Digite a idade da pessoa: ");
	        int personAge = inputScanner.nextInt();
	        inputScanner.nextLine();

	        person.setPersonName(personName);
	        person.setPersonAge(personAge);

	        return person;
	    }

	    public void add() {
	        System.out.println("Add Person");
	        System.out.println();

	        Person newPerson = getPersonData(null);

	        personDataAccessObject.insert(newPerson);
	    }

	    public void edit() {
	        System.out.println("Edit Person");
	        System.out.println();

	        printPeople();

	        int personId = getPersonId();

	        Person personModify = personDataAccessObject.selectById(personId);

	        Person newPerson = getPersonData(personModify);

	        newPerson.setPersonId(personModify.getPersonId());
	        personDataAccessObject.update(newPerson);
	    }

	    public void delete() {
	        System.out.println("Excluir Pessoa");
	        System.out.println();

	        printPeople();

	        int personId = getPersonId();

	        personDataAccessObject.delete(personId);
	    }

	    public void listAll() {
	        System.out.println("Lista de Pessoas");
	        System.out.println();
	        printPeople();
	    }

	    private void printPeople() {
	        List<Person> listPerson = personDataAccessObject.selectAll();
	        System.out.println("Person Id\tPerson Name\tPerson Age");
	        for (Person person : listPerson) {
	            System.out.println(person.getPersonId() + "\t" + person.getPersonName() + "\t" + person.getPersonAge());
	        }
	    }
	
}