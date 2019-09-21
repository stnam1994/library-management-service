package com.st.lms.dao;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import com.st.lms.model.Publisher;

public class PublisherDao {

	private String fileRelativePath = System.getProperty("user.dir") + "\\src\\publishers.csv";
	private Set<Publisher> publishers = new TreeSet<>();

	
	public PublisherDao() {}
	
	public void readPublishers() {
		try (BufferedReader buffer = new BufferedReader(new FileReader(fileRelativePath))) {
			String line;
			
			while((line = buffer.readLine()) != null) {
				String[] values = line.split(";");
				String name = values[0].trim();			
				String id = values[1].trim();
				String address = values[2].trim();
				
				publishers.add(new Publisher(name, id, address));
			}
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Publishers.csv ***");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public String getPublisherName(String id) {
		return publishers.stream()
				.filter(publisher -> publisher.getId().equalsIgnoreCase(id))
				.collect(Collectors.toList())
				.get(0)
				.getName();
	}

	public List<String> returnIDList() {
		return publishers.stream()
				.map(publisher -> publisher.getId())
				.collect(Collectors.toList());
	}
	
	public void addPublisher(String name, String id, String address) {
		sanitize(name, id, address);
		if (!isDuplicateId(id)) publishers.add(new Publisher(name, id, address));
		else System.out.println("Publisher Id already exists, cannot add publisher with duplicate Id.");
	}
	
	public void updatePublisher(String queryId, String newName, String newAddress) {
		if (isDuplicateId(queryId))
			publishers.stream()
			.filter(publisher -> publisher.getId().equalsIgnoreCase(queryId))
			.forEach(publisher -> {
				if(!newName.equals("")) publisher.setName(newName);
				if(!newAddress.equals("")) publisher.setAddress(newAddress);
			});
		else
			System.out.printf("\nPublisher not found.\n", queryId);
	}
	
	public void retrievePublisher(String queryId) {
		if (isDuplicateId(queryId))
			publishers.stream()
			.filter(publisher -> publisher.getId().equalsIgnoreCase(queryId))
			.forEach(publisher -> publisher.printInfo());
		else
			System.out.printf("\nPublisher not found.\n", queryId);
	}
	
	public void removePublisher(String queryId) {
		if (isDuplicateId(queryId))
			publishers = publishers.stream()
						.filter(publisher -> !publisher.getId().equalsIgnoreCase(queryId))
						.collect(Collectors.toSet());
		else
			System.out.printf("\nPublisher not found.\n", queryId);
	}

	public void saveToCSV() {
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fileRelativePath))) {
			publishers.stream()
			.forEach(publisher -> {
				String line = String.join("; ", publisher.getName(), publisher.getId(), publisher.getAddress());
				try {
					buffer.write(line + "\n");
				} catch (IOException e) {
					System.out.println("*** ERROR: Failed to save to Publishers.csv ***");
					System.out.println(e.getMessage());
					System.exit(0);
				}
			});
			
			buffer.close();
		} catch (IOException e) {
			System.out.println("*** ERROR: Failed to load Publishers.csv ***");
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
	
	// Checks if there already exists a Publisher with some Id value
	public boolean isDuplicateId(String idToCheck) {
		return publishers.stream().anyMatch(publisher -> publisher.getId().equalsIgnoreCase(idToCheck));
	}
	
}
