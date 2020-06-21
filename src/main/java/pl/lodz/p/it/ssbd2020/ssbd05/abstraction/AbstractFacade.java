package pl.lodz.p.it.ssbd2020.ssbd05.abstraction;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Klasa abstrakcyjnej fasady
 *
 * @param <T> Parametr typu encji
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Konstruktor bezparametrowy.
     *
     * @param entityClass Klasa encji.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Metoda pobierająca instancję klasy EntityManager.
     *
     * @return Obiekt klasy EntityManager.
     */
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

    /**
     * Dodaj nowy obiekt encji.
     *
     * @param entity Encja.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny.
     */
    public void create(T entity) throws AppBaseException {
        validate(entity);
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    /**
     * Edytuj istniejący obiekt encji.
     *
     * @param entity Encja.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny.
     */
    public void edit(T entity) throws AppBaseException {
        validate(entity);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    /**
     * Usuń istniejący obiekt encji.
     *
     * @param entity Encja.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny.
     */
    public void remove(T entity) throws AppBaseException {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    /**
     * Znajdź obiekt encji o podanym identyfikatorze.
     *
     * @param id Identyfikator bazodanowy.
     * @return Obiekt encji opakowany w obiekt Optional.
     */
    public Optional<T> find(Object id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    /**
     * Pobierz wsystkie obiekty encji danego typu.
     *
     * @return Lista obiektów encji.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny.
     */
    public List<T> findAll() throws AppBaseException {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Pobierz ilość obiektów encji danego typu.
     *
     * @return Ilość obiektów.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
