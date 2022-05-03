package com.sciodev.blogoner.models.dao;

import com.sciodev.blogoner.models.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("UserDAOJPA")
public class UserDAO implements IUserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Transactional
    @Override
    public void save(User user) {
        entityManager.merge(user);
    }
    @Transactional
    @Override
    public User findOne(Long id) { return entityManager.find(User.class, id); }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.remove(findOne(id));
    }
}
