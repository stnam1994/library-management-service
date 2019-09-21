package com.st.lms.service;

import java.util.*;
import com.st.lms.dao.AuthorDao;
import com.st.lms.dao.BookDao;
import com.st.lms.dao.PublisherDao;
import com.st.lms.model.Book;

public class Service {

	private AuthorDao authDao;
	private BookDao bookDao;
	private PublisherDao pubDao;

	public Service() {
		authDao = new AuthorDao();
		bookDao = new BookDao();
		pubDao = new PublisherDao();
	}

	public String getAuthorName(String authId) {
		return authDao.getAuthorName(authId);
	}

	public String getPublisherName(String pubId) {
		return pubDao.getPublisherName(pubId);
	}

	public void performTask(int taskType, int objectType, Scanner scan) {
		String name, id, authId, pubId, address;
		authDao.readAuthors();
		bookDao.readBooks();
		pubDao.readPublishers();
		System.out.println();

		switch (objectType) {
		case 1: // Author
			System.out.println("Enter the author's id:");
			id = scan.nextLine();
			switch (taskType) {
			case 1: // Add
				System.out.println("Enter the author's name:");
				name = scan.nextLine();
				authDao.addAuthor(name, id);
				break;
			case 2: // Update
				System.out.println("Enter the author's new name:");
				name = scan.nextLine();
				authDao.updateAuthor(id, name);
				break;
			case 3: // Retrieve
				authDao.retrieveAuthor(id);
				break;
			case 4: // Remove
				authDao.removeAuthor(id);
				break;
			default:
				break;
			}
			break;
		case 2: // Book
			System.out.println("Enter the book's id:");
			id = scan.nextLine();
			switch (taskType) {
			case 1: // Add
				System.out.println("Enter the book's name:");
				name = scan.nextLine();
				System.out.println("Enter the author id:");
				authId = scan.nextLine();
				System.out.println("Enter the publisher id:");
				pubId = scan.nextLine();
				bookDao.addBook(name, id, authId, pubId);
				break;
			case 2: // Update
				System.out.println("Enter the new book name:");
				name = scan.nextLine();
				System.out.println("Enter the new author id:");
				authId = scan.nextLine();
				System.out.println("Enter the new publisher id:");
				pubId = scan.nextLine();
				bookDao.updateBook(id, name, authId, pubId);
				break;
			case 3: // Retrieve
				betterBookInfo(id);
				break;
			case 4: // Remove
				bookDao.removeBook(id);
				break;
			default:
				break;
			}
			break;
		case 3: // Publisher
			System.out.println("Enter the publisher's id:");
			id = scan.nextLine();
			switch (taskType) {
			case 1: // Add
				System.out.println("Enter the publisher's name:");
				name = scan.nextLine();
				System.out.println("Enter the publisher's address:");
				address = scan.nextLine();
				pubDao.addPublisher(name, id, address);
				break;
			case 2: // Update
				System.out.println("Enter the new publisher name:");
				name = scan.nextLine();
				System.out.println("Enter the new publisher address:");
				address = scan.nextLine();
				pubDao.updatePublisher(id, name, address);
				break;
			case 3: // Retrieve
				pubDao.retrievePublisher(id);
				break;
			case 4: // Remove
				pubDao.removePublisher(id);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		System.out.println();
		synchronized (this) {
			saveChangesToCSV();
		}
	}

	public void betterBookInfo(String id) {
		try {
			Book book = bookDao.getBookById(id);
			String authName = authDao.getAuthorName(book.getAuthId());
			String pubName = pubDao.getPublisherName(book.getPubId());

			System.out.println();
			System.out.printf("Book Name: %s\n", book.getName());
			System.out.printf("Book Id:   %s\n", id);
			System.out.printf("Author:    %s (id: %s)\n", authName, book.getAuthId());
			System.out.printf("Publisher: %s (id: %s)\n", pubName, book.getPubId());

		} catch (NullPointerException e) {
			System.out.println("\nBook not found.");
		}
	}

	// When an author or publisher is removed, remove all books under that author &
	// publisher
	public void updateBookList() {
		List<String> authList = authDao.returnIDList();
		List<String> pubList = pubDao.returnIDList();
		List<String> bookAuthList = bookDao.getAuthorList();
		List<String> bookPubList = bookDao.getPublisherList();

		bookAuthList.removeAll(authList);
		bookPubList.removeAll(pubList);

		bookAuthList.stream().forEach(id -> {
			Book book = bookDao.getBookByAuthId(id);
			bookDao.removeBook(book.getId());
		});
		bookPubList.stream().forEach(id -> {
			Book book = bookDao.getBookByPubId(id);
			bookDao.removeBook(book.getId());
		});
	}

	public void saveChangesToCSV() {
		updateBookList();
		authDao.saveToCSV();
		bookDao.saveToCSV();
		pubDao.saveToCSV();
	}

}
