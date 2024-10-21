package todo.list.application.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class SimpleTaskRepository implements SimpleCrudRepository<TaskEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger("SimpleTaskRepository");

    private final EntityManager entityManager;

    public SimpleTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TaskEntity> findAll() {
        List<TaskEntity> tasksFound = new ArrayList<>();

        try {
            TypedQuery<TaskEntity> query = entityManager.createQuery("SELECT t FROM TaskEntity t", TaskEntity.class);
            tasksFound = query.getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return tasksFound;
    }

    @Override
    public Optional<TaskEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(TaskEntity.class, id));
    }

    @Override
    public TaskEntity save(TaskEntity task) {

        if (task == null)
            throw new IllegalArgumentException("task cannot be null");

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            task.setStatus(Status.RUNNING);
            task.setCreatedAt(LocalDateTime.now());
            entityManager.persist(task);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return task;
    }

    @Override
    public TaskEntity update(TaskEntity task) {

        if (task == null) throw new IllegalArgumentException("task cannot be null");

        Optional<TaskEntity> optionalTask = findById(task.getId());

        if (optionalTask.isEmpty())
            throw new IllegalArgumentException("task not found to be updated");

        EntityTransaction transaction = entityManager.getTransaction();

        TaskEntity taskFound = optionalTask.get();

        try {
            transaction.begin();
            TaskEntity taskToBeUpdated = exchangeData(task, taskFound);
            task = entityManager.merge(taskToBeUpdated);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return task;
    }

    private TaskEntity exchangeData(TaskEntity oldTask, TaskEntity newTask) {

        if (oldTask == null || newTask == null) return null;

        newTask.setId(oldTask.getId());
        newTask.setTitle(oldTask.getTitle());
        newTask.setDescription(oldTask.getDescription());
        newTask.setStatus(oldTask.getStatus());
        newTask.setDone(oldTask.isDone());
        newTask.setCompleted(oldTask.isCompleted());
        newTask.setErased(oldTask.isErased());
        newTask.setTags(oldTask.getTags());
        newTask.setCreatedAt(oldTask.getCreatedAt());
        newTask.setUpdatedAt(LocalDateTime.now());
        newTask.setErasedAt(oldTask.getErasedAt());
        newTask.setDoneAt(oldTask.getDoneAt());

        return newTask;
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
        }

        return results;
    }


    @Override
    public TaskEntity saveOrUpdate(TaskEntity task) {
        findById(task.getId())
                .map(this::update)
                .orElseGet(() -> save(task));

        return task;
    }

    @Override
    public void deleteById(long id) {
        Optional<TaskEntity> taskFound = findById(id);

        if (taskFound.isEmpty())
            throw new IllegalArgumentException("task not found to be deleted");

        EntityTransaction transaction = entityManager.getTransaction();

        TaskEntity taskToDeleteForever = taskFound.get();

        try {
            transaction.begin();
            entityManager.remove(taskToDeleteForever);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    public void markAsDone(long taskId) {
        Optional<TaskEntity> taskFound = findById(taskId);

        if (taskFound.isEmpty())
            throw new IllegalArgumentException("task not found to be marked as done");

        EntityTransaction transaction = entityManager.getTransaction();

        TaskEntity taskToMarkAsDone = taskFound.get();

        try {
            taskToMarkAsDone.setDone(true);
            taskToMarkAsDone.setStatus(Status.DONE);
            taskToMarkAsDone.setDoneAt(LocalDateTime.now());
            saveOrUpdate(taskToMarkAsDone);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    public void markAsCompleted(long taskId) {

        Optional<TaskEntity> taskFound = findById(taskId);

        if (taskFound.isEmpty())
            throw new IllegalArgumentException("task not found to be marked as completed");

        EntityTransaction transaction = entityManager.getTransaction();

        TaskEntity taskToMarkAsCompleted = taskFound.get();

        try {
            taskToMarkAsCompleted.setCompleted(true);
            taskToMarkAsCompleted.setStatus(Status.COMPLETED);
            taskToMarkAsCompleted.setCompletedAt(LocalDateTime.now());

            update(taskToMarkAsCompleted);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @Override
    public void eraseById(long taskId) {
        Optional<TaskEntity> optionalTask = findById(taskId);

        if (optionalTask.isEmpty())
            throw new IllegalArgumentException("task not found to be marked as erased");

        EntityTransaction transaction = entityManager.getTransaction();

        TaskEntity taskToMarkAsErased = optionalTask.get();

        try {
            transaction.begin();
            taskToMarkAsErased.setErased(true);
            taskToMarkAsErased.setErasedAt(LocalDateTime.now());
            entityManager.merge(taskToMarkAsErased);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
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
        }
    }
}
