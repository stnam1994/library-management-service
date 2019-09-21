package com.st.lms.model;

public class Book implements Comparable<Book> {
	private String name, id, authId, pubId;

	public Book(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public Book(String name, String id, String authId, String pubId) {
		this.name = name;
		this.id = id;
		this.authId = authId;
		this.pubId = pubId;
	}
	
	public void printInfo() {
		System.out.println();
		System.out.printf("Book Name:    %s\n", name);
		System.out.printf("Book Id:      %s\n", id);
		System.out.printf("Author Id:    %s\n", authId);
		System.out.printf("Publisher Id: %s\n", pubId);
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

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authId == null) ? 0 : authId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pubId == null) ? 0 : pubId.hashCode());
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
		Book other = (Book) obj;
		if (authId == null) {
			if (other.authId != null)
				return false;
		} else if (!authId.equals(other.authId))
			return false;
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
		if (pubId == null) {
			if (other.pubId != null)
				return false;
		} else if (!pubId.equals(other.pubId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Book o) {
		return Integer.parseInt(this.getId()) - Integer.parseInt(o.getId());
	}

}
