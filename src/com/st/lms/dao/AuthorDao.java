package com.st.lms.dao;
import com.st.lms.model.Author;
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class AuthorDao {
	
	private String fileRelativePath = System.getProperty("user.dir") + "\\src\\authors.csv";
	private Set<Author> authors = new TreeSet<>();
	
	public AuthorDao() {}

	public void readAuthors() {
		try (BufferedReader buffer = new BufferedReader(new FileReader(fileRelativePath))) {
			String line;
			
			while((line = buffer.readLine()) != null) {
				String[] values = line.split(";");
				String name = values[0].trim();
				String id = values[1].trim();
				
				authors.add(new Author(name, id));
			}
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Authors.csv ***");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public String getAuthorName(String id) {
		return authors.stream()
				.filter(author -> author.getId().equalsIgnoreCase(id))
				.collect(Collectors.toList())
				.get(0)
				.getName();
	}
	
	public List<String> returnIDList() {
		return authors.stream()
				.map(author -> author.getId())
				.collect(Collectors.toList());
	}
	
	public void addAuthor(String name, String id) {
		sanitize(name, id);
		if (!isDuplicateId(id)) authors.add(new Author(name, id));
		else System.out.println("Author Id already exists, cannot add author with duplicate Id.");
	}
	
	public void updateAuthor(String queryId, String newName) {
		if (isDuplicateId(queryId))
			authors.stream()
			.filter(author -> author.getId().equalsIgnoreCase(queryId))
			.forEach(author -> {
				if(!newName.equals("")) author.setName(newName);
			});
		else
			System.out.printf("\nAuthor not found.\n", queryId);
	}
	
	public void retrieveAuthor(String queryId) {
		if (isDuplicateId(queryId))
			authors.stream()
			.filter(author -> author.getId().equalsIgnoreCase(queryId))
			.forEach(author -> author.printInfo());
		else
			System.out.printf("\nAuthor not found.\n", queryId);
	}
	
	public void removeAuthor(String queryId) {
		if (isDuplicateId(queryId))
			authors = authors.stream()
					.filter(author -> !author.getId().equalsIgnoreCase(queryId))
					.collect(Collectors.toSet());
		else
			System.out.printf("\nAuthor not found.\n", queryId);
	}
	
	public void saveToCSV() {
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fileRelativePath))) {	
			authors.stream()
			.forEach(author -> {
				String line = String.join("; ", author.getName(), author.getId());
				try {
					buffer.write(line + "\n");
				} catch (IOException e) {
					System.out.println("*** ERROR: Failed to save to Authors.csv ***");
					System.out.println(e.getMessage());
					System.exit(0);
				}
			});
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Authors.csv ***");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	// Removes special characters (just ; at the moment)
	public void sanitize(String ...args) {
		Set<String> strings = new TreeSet<>(Arrays.asList(args));
		strings.stream()
		.forEach(string -> string.replaceAll(";", ""));
	}
	
	// Checks if there already exists an Author with some Id value
	public boolean isDuplicateId(String idToCheck) {
		return authors.stream().anyMatch(author -> author.getId().equalsIgnoreCase(idToCheck));
	}
	
}
