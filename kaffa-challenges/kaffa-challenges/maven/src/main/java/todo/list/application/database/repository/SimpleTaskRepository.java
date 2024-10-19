package todo.list.application.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.list.application.database.configuration.JPAConfiguration;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
public class SimpleTaskRepository implements SimpleRepository<TaskEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger("SimpleTaskRepository");

    private final EntityManager entityManager = JPAConfiguration.getEntityManager();

    @Override
    public List<TaskEntity> findAll() {
        List<TaskEntity> tasksFound = new ArrayList<>();
        try {
            TypedQuery<TaskEntity> query = entityManager.createQuery("SELECT t FROM TaskEntity T", TaskEntity.class);
            tasksFound = query.getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            entityManager.close();
        }
        return tasksFound;
    }

    @Override
    public TaskEntity findById(long id) {
        return entityManager.find(TaskEntity.class, id);
    }

    public List<TaskEntity> findByDescription(String description) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<TaskEntity> results = new ArrayList<>();
        try {
            transaction.begin();
            TypedQuery<TaskEntity> query = entityManager.createQuery("SELECT t FROM TaskEntity t WHERE t.description LIKE :description", TaskEntity.class);
            query.setParameter("description", "%" + description + "%");
            results = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
        return results;
    }

    @Override
    public TaskEntity saveOrUpdate(TaskEntity task) {
        TaskEntity taskFound = findById(task.getId());

        EntityTransaction transaction = entityManager.getTransaction();
        TaskEntity result = null;

        try {
            transaction.begin();
            if (taskFound != null) {
                taskFound.setId(task.getId());
                taskFound.setTitle(task.getTitle());
                taskFound.setDescription(task.getDescription());
                taskFound.setStatus(task.getStatus());
                taskFound.setDone(task.isDone());
                taskFound.setCompleted(task.isCompleted());
                taskFound.setErased(task.isErased());
                taskFound.setTags(task.getTags());
                taskFound.setCreatedAt(task.getCreatedAt());
                taskFound.setUpdatedAt(LocalDateTime.now());
                taskFound.setErasedAt(task.getErasedAt());
                result = entityManager.merge(taskFound);
            } else {
                task.setStatus(Status.RUNNING);
                task.setCreatedAt(LocalDateTime.now());
                result = entityManager.merge(task);
            }
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }

        return result;
    }

    @Override
    public void deleteById(long id) {
        TaskEntity taskFound = findById(id);

        if (taskFound == null)
            throw new IllegalArgumentException(String.format("task not found with given id: %d", id));

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.remove(taskFound);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void markAsDone(long taskId) {
        TaskEntity taskFound = findById(taskId);

        if (taskFound == null)
            throw new IllegalArgumentException(String.format("task not found with given id: %d", taskId));

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            taskFound.setDone(true);
            taskFound.setStatus(Status.DONE);
            taskFound.setDoneAt(LocalDateTime.now());
            saveOrUpdate(taskFound);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void markAsCompleted(long taskId) {

        TaskEntity taskFound = findById(taskId);

        if (taskFound == null)
            throw new IllegalArgumentException(String.format("task not found with given id: %d", taskId));

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            taskFound.setCompleted(true);
            taskFound.setStatus(Status.COMPLETED);
            taskFound.setCompletedAt(LocalDateTime.now());
            saveOrUpdate(taskFound);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void eraseById(long id) {
        TaskEntity taskFound = findById(id);

        if (taskFound == null) {
            throw new IllegalArgumentException(String.format("task not found with given id: %d", id));
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            taskFound.setErased(true);
            taskFound.setErasedAt(LocalDateTime.now());
            entityManager.merge(taskFound);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clear() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("DELETE FROM TaskEntity t").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
