# :books: library-management-service
:man_office_worker: **Team members:** Stephen, Skyler, Juan

A library management service (LMS) application by ***Team SSJ*** *(Super Smoothstack-Jin)*



## Implementation
Our LMS application follows the Data Access Object (DAO) design scheme.

```
App
> LMSApp.java

DAO
> AuthorDao.java
> BookDao.java
> PublisherDao.java

Service
> Service.java

Model
> Author.java
> Book.java
> Publisher.java
```

The App class (LMSApp.java) implements the menu interface, program logic, and user input.

The DAO classes provide file processing (CSV files) and database methods (add/update/retrieve/remove) to the App class.

The Service class acts as a bridge between DAOs, providing relational methods (Author-Book and Publisher-Book).

The Model classes hold data fields (e.g. Author name/id), which can be retrieved or modified by DAOs.

### Implementation Notes

1. Book class fields (name, id, authId, pubId)
    * I chose to not use Author and Publisher objects as fields because that would complicate instantiation for Book objects.
    * It would either require relational methods within the Book class (to pass a valid Author/Publisher object), or delegation of the addBook() method to the Service class (encapsulation problem).
    * Instead, I encapsulated all relational methods within the Service class.
    
2. Service.betterBookInfo() vs Book.printInfo()
    * This split naturally follows the design choice that I explained above.
    * betterBookInfo(): printing an Author/Publisher's name from a Book object is a relational method, so it is implemented in the Service class.
    * printBookInfo(): this is a more primitive print method that only shows the raw Author/Publisher Id.
    
3. Service.updateBookList()
    * This method is called *after* an operation has completed (add/update/retrieve/remove).
    * In the event of an Author/Publisher removal, it:
         - Finds the corresponding Id value (from a list of Books) that no longer exists (in a list of Authors/Publishers)
         - Finds the book(s) that link(s) to this now-deleted Author/Publisher (by its Id value), and delete the book(s).

## :memo: Task List
- [x] Implement model classes with relevant methods (print, getter/setter, equals, hashCode, compareTo)
- [x] Implement DAO classes for each model (read from CSV, add/update/retrieve/remove, save to CSV)
- [x] Implement Service class (pseudo-relational database operations)
- [x] Implement main application (command-line interface)
- [x] Miscellaneous features
  - [x] Input validation (allows retries without closing app)
  - [x] Exception handling with custom error messages
  - [x] Relational integrity when Author/Publisher is removed

- [ ] Next Steps
  - [ ] Unit testing

