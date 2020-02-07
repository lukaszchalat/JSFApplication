package de.lukaszchalat.JSFApplication.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;

public class CustomExceptionHandler extends ExceptionHandlerWrapper 
{
	private static final Logger logger = Logger.getLogger( CustomExceptionHandler.class.getCanonicalName() );
	
	private ExceptionHandler exceptionHandler;
	
	public CustomExceptionHandler( ExceptionHandler exceptionHandler )
	{
		this.exceptionHandler = exceptionHandler;
	}
	

	@Override
	public ExceptionHandler getWrapped() 
	{
		return exceptionHandler;
	}
	
	public void handle() throws FacesException
	{	
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while( iterator.hasNext() )
		{		
			ExceptionQueuedEvent event          = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			Throwable throwable = context.getException();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			try
			{
				logger.error( "Exception has occured : " + throwable.getMessage() );
				
				facesContext.getExternalContext().getRequestMap().put( "exceptionMessage",  throwable.getMessage() );

				facesContext.getApplication().getNavigationHandler().handleNavigation( facesContext, null, "/error?faces-redirect=true" );
				
				facesContext.renderResponse();
			}
			finally
			{
				iterator.remove();
			}
		}
		
		getWrapped().handle();
	}

}
