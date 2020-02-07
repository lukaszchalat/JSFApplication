package de.lukaszchalat.JSFApplication.dao;

import java.util.List;

import de.lukaszchalat.JSFApplication.entity.Contact;

public interface ContactDAO 
{
	public List<Contact> getAllContacts();
	
	public Contact getContact( int id );
	
	public void createContact( Contact contact );
	
	public void updateContact( Contact contact );
	
	public void deleteContact( Contact contact );
}
