package pl.lodz.p.it.ssbd2020.ssbd05.abstraction;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    private void validate(T entity) throws AppBaseException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if(!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation<T> violation : violations) {
                stringBuilder.append(violation.getPropertyPath()).append(" ")
                        .append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(stringBuilder.toString());
        }
    }

    public void create(T entity) throws AppBaseException {
        validate(entity);
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) throws AppBaseException {
        validate(entity);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) throws AppBaseException {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public Optional<T> find(Object id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    public List<T> findAll() throws AppBaseException {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
