package de.lukaszchalat.JSFApplication.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class NameValidator implements Validator 
{
	private static final String NAME_REGEX = "[A-Za-z ]+";
	
	private Pattern pattern;
	private Matcher matcher;
	
	public NameValidator()
	{
		pattern = Pattern.compile( NAME_REGEX );
	}
	
	@Override
	public void validate(FacesContext facesContext, UIComponent UIComponent, Object object) throws ValidatorException 
	{
		String lastOrFirstName = object.toString();
		FacesMessage facesMessage;
		
		// check required length
		if( lastOrFirstName.length() < Constraints.NAME_MIN_LENGTH )
		{
			facesMessage = new FacesMessage( "Name has to have minimum 3 characters !" );
			
			facesMessage.setSeverity( FacesMessage.SEVERITY_ERROR );
			
			throw new ValidatorException( facesMessage );
		}
		
		// check if name consists only of letters
		matcher = pattern.matcher( lastOrFirstName );
		
		if( ! matcher.matches() )
		{
			facesMessage = new FacesMessage( "Name can be consisted of letters only !" );
			
			facesMessage.setSeverity( FacesMessage.SEVERITY_ERROR );
			
			throw new ValidatorException( facesMessage );
		}
	}

}
