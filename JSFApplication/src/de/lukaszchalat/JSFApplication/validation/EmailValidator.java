package de.lukaszchalat.JSFApplication.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.lukaszchalat.JSFApplication.dao.*;
import de.lukaszchalat.JSFApplication.entity.Contact;
import de.lukaszchalat.JSFApplication.model.ContactBean;

public class EmailValidator implements Validator 
{
	private static final String EMAIL_REGEX = "^(.+)@(.+)$";
	
	private ContactDAOImplementation contactDAOImplementation;
	private Pattern pattern;
	private Matcher matcher;
	
	public EmailValidator()
	{
		pattern = Pattern.compile( EMAIL_REGEX );
		contactDAOImplementation = ContactDAOImplementation.getInstance();
	}
	

	@Override
	public void validate( FacesContext facesCOntext, UIComponent UIComponent, Object object ) throws ValidatorException 
	{	
		FacesMessage facesMessage;
		String email = object.toString();
		
		// check if email has correct format
		matcher = pattern.matcher( email );
		
		if( ! matcher.matches() )
		{
			facesMessage = new FacesMessage( "Invalid email format !" );
			
			facesMessage.setSeverity( FacesMessage.SEVERITY_ERROR );
			
			throw new ValidatorException( facesMessage );
		}
		
		ContactBean editedContact = (ContactBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get( "editedContact" );
		
		// check if email is unique
		Contact contact = contactDAOImplementation.getAllContacts().stream()   
				                                                   .filter( c -> c.getEmail().equals( email ) )
				                                                   .findFirst()
				                                                   .orElse( null );
		
		if( ( editedContact != null && contact != null && editedContact.getId() != contact.getId() ) || ( editedContact == null && contact != null ) )
		{	
			facesMessage = new FacesMessage( "This email alredy exists !" );
			
			facesMessage.setSeverity( FacesMessage.SEVERITY_ERROR );
			
			throw new ValidatorException( facesMessage );
		}
	}
}
