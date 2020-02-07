package de.lukaszchalat.JSFApplication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lukaszchalat.JSFApplication.model.ContactBean;


@Entity
@Table( name="contacts" )
public class Contact
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	@Column( name="id" )
	private int id;
	
	@Column( name="firstName" )
	private String firstName;
	
	@Column( name="lastName" )
	private String lastName;
	
	@Column( name="email" )
	private String email;
	
	public Contact()
	{
		
	}
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getFirstName() 
	{
		return firstName;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public String getLastName() 
	{
		return lastName;
	}

	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public ContactBean toContactBean()
	{
		ContactBean contactBean = new ContactBean();
		
		contactBean.setId( this.getId() );
		contactBean.setFirstName( this.getFirstName() );
		contactBean.setLastName( this.getLastName() );
		contactBean.setEmail( this.getEmail() );
		
		return contactBean;
	}

	@Override
	public String toString() 
	{
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Contact other = (Contact) obj;
		
		if (email == null) 
		{
			if (other.email != null) return false;
		} 
		else if (!email.equals(other.email)) return false;
		
		if (firstName == null) 
		{
			if (other.firstName != null) return false;
			
		} 
		else if (!firstName.equals(other.firstName)) return false;
		
		if (id != other.id) return false;
		
		if (lastName == null) 
		{
			if (other.lastName != null) return false;
		} 
		else if (!lastName.equals(other.lastName)) return false;
		
		return true;
	}
	
	
}
