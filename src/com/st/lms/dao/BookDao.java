package com.st.lms.dao;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import com.st.lms.model.Book;

public class BookDao {

	private String fileRelativePath = System.getProperty("user.dir") + "\\src\\books.csv";
	private Set<Book> books = new TreeSet<>();

	public BookDao() {}
	
	public void readBooks() {
		try (BufferedReader buffer = new BufferedReader(new FileReader(fileRelativePath))) {
			String line;
			
			while((line = buffer.readLine()) != null) {
				String[] values = line.split(";");
				String name = values[0].trim();
				String id = values[1].trim();
				String authId = values[2].trim();
				String pubId = values[3].trim();
				
				books.add(new Book(name, id, authId, pubId));
			}
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Books.csv ***");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public Book getBookById(String id) {
		if (isDuplicateId(id))
			return books.stream()
					.filter(book -> book.getId().equalsIgnoreCase(id))
					.collect(Collectors.toList())
					.get(0);
		else
			return null;
	}
	
	public Book getBookByAuthId(String id) {
		return books.stream()
				.filter(book -> book.getAuthId().equalsIgnoreCase(id))
				.collect(Collectors.toList())
				.get(0);		
	}
	
	public Book getBookByPubId(String id) {
		return books.stream()
				.filter(book -> book.getPubId().equalsIgnoreCase(id))
				.collect(Collectors.toList())
				.get(0);		
	}
	
	public List<String> getAuthorList() {
		return books.stream()
				.map(author -> author.getAuthId())
				.collect(Collectors.toList());
	}
	
	public List<String> getPublisherList() {
		return books.stream()
				.map(author -> author.getPubId())
				.collect(Collectors.toList());
	}
	
	public void addBook(String name, String id, String authId, String pubId) {
		sanitize(name, id, authId, pubId);
		if (!isDuplicateId(id)) books.add(new Book(name, id, authId, pubId));
		else System.out.println("Book Id already exists, cannot add book with duplicate Id.");
	}
	
	public void updateBook(String queryId, String newName, String newAuthId, String newPubId) {
		if (isDuplicateId(queryId))
			books.stream()
			.filter(book -> book.getId().equalsIgnoreCase(queryId))
			.forEach(book -> {
				if(!newName.equals("")) book.setName(newName);
				if(!newAuthId.equals("")) book.setAuthId(newAuthId);
				if(!newPubId.equals("")) book.setPubId(newPubId);
			});
		else
			System.out.printf("\nBook not found.\n", queryId);
	}
	
	public void retrieveBook(String queryId) {
		if (isDuplicateId(queryId))
			books.stream()
			.filter(book -> book.getId().equalsIgnoreCase(queryId))
			.forEach(book -> book.printInfo());
		else
			System.out.printf("\nBook not found.\n", queryId);
	}
	
	public void removeBook(String queryId) {
		if (isDuplicateId(queryId))
			books = books.stream()
					.filter(book -> !book.getId().equalsIgnoreCase(queryId))
					.collect(Collectors.toSet());
		else
			System.out.printf("\nBook not found.\n", queryId);
	}
	
	public void saveToCSV() {
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fileRelativePath))) {
			books.stream()
			.forEach(book -> {
				String line = String.join("; ", book.getName(), book.getId(), book.getAuthId(), book.getPubId());
				try {
					buffer.write(line + "\n");
				} catch (IOException e) {
					System.out.println("*** ERROR: Failed to save to Books.csv ***");
					System.out.println(e.getMessage());
					System.exit(0);
				}
			});
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Books.csv ***");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	// Removes special characters (just ; at the moment)
	public void sanitize(String ...args) {
		Set<String> strings = new TreeSet<>(Arrays.asList(args));
		strings.stream().forEach(string -> string.replaceAll(";", ""));
	}
	
	// Checks if there already exists a Book with some Id value
	public boolean isDuplicateId(String idToCheck) {
		return books.stream().anyMatch(book -> book.getId().equalsIgnoreCase(idToCheck));
	}
	
}
