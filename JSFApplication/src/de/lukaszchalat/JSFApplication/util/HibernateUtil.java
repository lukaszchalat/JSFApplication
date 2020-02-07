package de.lukaszchalat.JSFApplication.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.lukaszchalat.JSFApplication.entity.Contact;
 
public class HibernateUtil 
{
    private static SessionFactory sessionFactoryObj = buildSessionFactoryObj();
 
    private static SessionFactory buildSessionFactoryObj() 
    {
        try 
        {
            sessionFactoryObj = new Configuration().configure( "hibernate.cfg.xml" )
            		                               .addAnnotatedClass( Contact.class )
            		                               .buildSessionFactory();    
        } 
        catch ( ExceptionInInitializerError exceptionObj ) 
        {
            exceptionObj.printStackTrace();
        }
        
        return sessionFactoryObj;
    }
 
    public static SessionFactory getSessionFactory() 
    {
        return sessionFactoryObj;
    }
}
