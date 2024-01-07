package com.proiect.repository;

import com.proiect.model.User;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public User saveUser(String username, String password, String role) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            User existingUser = getUser(username, password);
            if (existingUser != null) {
                throw new Exception("User already exists");
            }

            Transaction transaction = session.beginTransaction();

            User user = new User(username, password, role);

            session.save(user);
            transaction.commit();

            return user;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public User getUser(String username, String password) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return (User) session.createQuery("FROM User WHERE username = :username AND password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateUser(User user) throws Exception{
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.update(user);

            transaction.commit();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
