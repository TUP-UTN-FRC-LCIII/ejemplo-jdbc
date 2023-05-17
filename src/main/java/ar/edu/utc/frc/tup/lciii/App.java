package ar.edu.utc.frc.tup.lciii;

import ar.edu.utc.frc.tup.lciii.entities.Country;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Country arg = new Country(1L, "Argentina");
        Country chile = new Country(2L, "Chile");
        Country uruguay = new Country(3L, "Uruguay");
        Country brasil = new Country(5L, "Brasil");
        Country colombia = new Country();
        colombia.setName("Colombia");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.save(arg);
            session.save(chile);
            session.save(uruguay);
            session.save(brasil);
            session.save(colombia);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Country> countries = session.createQuery("from Country ", Country.class).list();
            countries.forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
