package com.st.lms.model;

public class Author implements Comparable<Author> {
	private String name, id;
	
	public Author(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public void printInfo() {
		System.out.println();
		System.out.printf("Author Name: %s\n", name);
		System.out.printf("Author Id:   %s\n", id);
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Author o) {
		return Integer.parseInt(this.getId()) - Integer.parseInt(o.getId());
	}
	
	
}
