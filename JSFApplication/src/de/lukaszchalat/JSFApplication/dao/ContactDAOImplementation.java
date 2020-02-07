package de.lukaszchalat.JSFApplication.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import de.lukaszchalat.JSFApplication.entity.Contact;
import de.lukaszchalat.JSFApplication.util.HibernateUtil;

public class ContactDAOImplementation implements ContactDAO 
{
	public static final Logger logger = Logger.getLogger( ContactDAOImplementation.class );
	private static ContactDAOImplementation instance;
	private Map<Integer, Contact> cache;
	boolean cached = false;
	
	private ContactDAOImplementation()
	{
		
	}
	
	public static ContactDAOImplementation getInstance()
	{
		if( instance == null )
		{
			instance = new ContactDAOImplementation();
		}
		
		return instance;
	}

	@Override
	public List<Contact> getAllContacts() 
	{			
		if( cached )
		{			
			return cache.values().stream().collect( Collectors.toList() );
		}
		
		List<Contact> contacts = new ArrayList<>();
		
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			
			session.beginTransaction();
			
			logger.info( "Querying all contacts from contacts table." );
		
			contacts = session.createQuery( "from Contact", Contact.class ).getResultList();
		
			cache = contacts.stream().collect( Collectors.toMap( Contact::getId, c -> c ) );
			
			cached = true;
			
			logger.info( "All contacts were successful queried and cached." );
			
			session.getTransaction().commit();
		}
		catch( Exception ex )
		{
			logger.error( "Query for all contacts failed. Exception message : " + ex.getMessage() );
			
			ex.printStackTrace();
		}
		
		return contacts;
	}

	@Override
	public Contact getContact(int id) 
	{
		return cache.get( id );
	}

	@Override
	public void createContact(Contact contact) 
	{
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
			session.beginTransaction();
		
			Integer id = (Integer) session.save( contact );
		
			contact.setId( id );
			
			logger.info( "New contact was created successful : " + contact );
		
			cache.put( id, contact );
		
			session.getTransaction().commit();
		}
		catch( Exception ex )
		{
			logger.error( "Creation of new contct failed. Exception message : " + ex.getMessage() );
			
			ex.printStackTrace();
		}
		
	}

	@Override
	public void updateContact(Contact contact) 
	{
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
			session.beginTransaction();
		
			session.update( contact );
			
			logger.info( "Contact was updated successful : " + contact );
		
			cache.put( contact.getId(), contact );
		
			session.getTransaction().commit();
		}
		catch( Exception ex )
		{
			logger.error( "Contact update failed. Exception message : " + ex.getMessage() );
			
			ex.printStackTrace();
		}
		
	}

	@Override
	public void deleteContact( Contact contact ) 
	{
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
			session.beginTransaction();
		
			session.delete( contact );
			
			logger.info( "Contact witd id " + contact.getId() + " was deleted successful." );
		
			cache.remove( contact.getId() );
		
			session.getTransaction().commit();
		}
		catch( Exception ex )
		{
			logger.error( "Contact deletion failed. Exception message : " + ex.getMessage() );
			
			ex.printStackTrace();
		}
		
	}

}
