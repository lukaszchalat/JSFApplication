package de.lukaszchalat.JSFApplication.controller;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import de.lukaszchalat.JSFApplication.dao.ContactDAOImplementation;
import de.lukaszchalat.JSFApplication.entity.Contact;
import de.lukaszchalat.JSFApplication.model.ContactBean;

@ManagedBean
@SessionScoped
public class ContactController
{
	private ContactDAOImplementation contactDAOImplementation = ContactDAOImplementation.getInstance();
	private List<Contact> contacts;
	
	public List<Contact> getContacts() 
	{
		return contacts;
	}

	public void getAllContacts()
	{			
		contacts = contactDAOImplementation.getAllContacts();
	}
	
	public String createContact( ContactBean contactBean )
	{
		contactDAOImplementation.createContact( contactBean.toContact() );
		
		return "index?faces-redirect=true";
	}
	
	public String editContact( int contactId )
	{  
		Contact contact = contactDAOImplementation.getContact( contactId );
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		requestMap.put( "editedContact", contact.toContactBean() );
		sessionMap.put( "editedContact", contact.toContactBean() );

		return "editContact.xhtml";
	}
	
	public String updateContact( ContactBean contactBean )
	{
		contactDAOImplementation.updateContact( contactBean.toContact() );
		
		return "index?faces-redirect=true";
	}
	
	public String deleteContact( Contact contact )
	{
		contactDAOImplementation.deleteContact( contact );
		
		return "index?faces-redirect=true";
	}
}
