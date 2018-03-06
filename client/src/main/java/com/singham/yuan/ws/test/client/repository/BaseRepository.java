package com.singham.yuan.ws.test.client.repository;

import com.singham.yuan.ws.test.client.utils.ReflectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@SuppressWarnings({"unchecked"})
public class BaseRepository<T> {

    private static final int THREE = 3;

    private Class<T> clazz;

    private SessionFactory sessionFactory;

    public BaseRepository() {
        this.clazz = ReflectionUtils.getSuperClassGenericType(getClass());
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public T load(Long id) {
        return id == null ? null : getSession().get(clazz, id);
    }

    public T load(String propertyName, Object propertyValue) {
        return load(clazz, new String[]{propertyName}, new Object[]{propertyValue});
    }

    public T load(String[] propertyNames, Object[] propertyValues) {
        return load(clazz, propertyNames, propertyValues);
    }

    private T load(Class<T> clazz, String[] propertyNames, Object[] propertyValues) {
        return (T) createQuery(clazz, propertyNames, propertyValues).uniqueResult();
    }

    public void save(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    private Query createQuery(Class<?> clazz, String[] propertyNames, Object[] values) {
        Query query = getSession().createQuery(createQueryString(clazz, propertyNames));
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(propertyNames[i], values[i]);
            }
        }
        return query;
    }

    private String createQueryString(Class<?> clazz, String[] propertyNames) {
        String hql = "from " + clazz.getSimpleName() + " _alias";
        StringBuilder sb = new StringBuilder();
        if (propertyNames != null) {
            for (String propertyName : propertyNames) {
                sb.append(" _alias.").append(propertyName).append(" = :").append(propertyName).append(" and");
            }
            hql = hql + " where " + sb.substring(0, sb.length() - THREE);
        }
        return hql;
    }

    @Autowired(required = false)
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
