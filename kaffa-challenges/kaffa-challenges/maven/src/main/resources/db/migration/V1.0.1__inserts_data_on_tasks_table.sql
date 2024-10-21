INSERT INTO tasks (title, description, status, is_done, is_completed, is_erased, updated_at, erased_at, created_at, done_at, completed_at)
VALUES
('Buy tomatoes for pasta', 'Get fresh tomatoes to make a delicious macaroni.', 'RUNNING', false, false, false, '2024-10-19T12:00:00', NULL, '2024-10-18T10:00:00', NULL, NULL),
('Purchase guitar strings for Jack', 'Buy new guitar strings for Jack''s upcoming concert.', 'DONE', true, true, false, '2024-10-18T15:30:00', NULL, '2024-10-18T09:00:00', '2024-10-18T15:00:00', '2024-10-18T15:30:00'),
('Finish reading the new novel', 'Read the latest novel that just got released.', 'COMPLETED', true, true, false, '2024-10-19T14:00:00', NULL, '2024-10-17T11:00:00', '2024-10-19T13:00:00', '2024-10-19T14:00:00'),
('Clean the house', 'Do a thorough cleaning of the entire house this weekend.', 'RUNNING', false, false, false, '2024-10-19T09:00:00', NULL, '2024-10-17T16:00:00', NULL, NULL),
('Prepare for the job interview', 'Study and prepare answers for common interview questions.', 'RUNNING', false, false, false, '2024-10-19T08:00:00', NULL, '2024-10-18T10:00:00', NULL, NULL),
('Submit the project report', 'Complete and submit the final project report by the deadline.', 'COMPLETED', true, true, false, '2024-10-19T13:00:00', NULL, '2024-10-16T10:00:00', '2024-10-18T12:00:00', '2024-10-19T13:00:00'),
('Grocery shopping for the week', 'Buy all necessary groceries for the week.', 'RUNNING', false, false, false, '2024-10-19T11:00:00', NULL, '2024-10-18T10:00:00', NULL, NULL),
('Organize the workspace', 'Declutter and organize the home office workspace.', 'DONE', true, true, false, '2024-10-18T15:30:00', NULL, '2024-10-17T12:00:00', '2024-10-18T15:00:00', '2024-10-18T15:30:00'),
('Plan the weekend trip', 'Plan and finalize details for the weekend trip.', 'RUNNING', false, false, false, '2024-10-19T10:00:00', NULL, '2024-10-18T09:00:00', NULL, NULL),
('Watch the new movie release', 'Enjoy the new movie that just got released in theaters.', 'COMPLETED', true, true, false, '2024-10-19T14:00:00', NULL, '2024-10-17T11:00:00', '2024-10-19T13:00:00', '2024-10-19T14:00:00'),
('Visit the dentist', 'Make an appointment to see the dentist for a checkup.', 'RUNNING', false, false, false, '2024-10-19T09:30:00', NULL, '2024-10-18T08:00:00', NULL, NULL);

INSERT INTO tags (task_id, tag) VALUES (1, 'grocery');
INSERT INTO tags (task_id, tag) VALUES (1, 'vegetables');
INSERT INTO tags (task_id, tag) VALUES (2, 'music');
INSERT INTO tags (task_id, tag) VALUES (2, 'shopping');
INSERT INTO tags (task_id, tag) VALUES (3, 'reading');
INSERT INTO tags (task_id, tag) VALUES (4, 'leisure');
INSERT INTO tags (task_id, tag) VALUES (4, 'chores');
INSERT INTO tags (task_id, tag) VALUES (5, 'cleaning');
INSERT INTO tags (task_id, tag) VALUES (5, 'career');
INSERT INTO tags (task_id, tag) VALUES (6, 'preparation');
INSERT INTO tags (task_id, tag) VALUES (6, 'work');
INSERT INTO tags (task_id, tag) VALUES (7, 'reports');
INSERT INTO tags (task_id, tag) VALUES (7, 'weekly');
INSERT INTO tags (task_id, tag) VALUES (8, 'organization');
INSERT INTO tags (task_id, tag) VALUES (8, 'workspace');
INSERT INTO tags (task_id, tag) VALUES (9, 'travel');
INSERT INTO tags (task_id, tag) VALUES (9, 'planning');
INSERT INTO tags (task_id, tag) VALUES (10, 'entertainment');
INSERT INTO tags (task_id, tag) VALUES (10, 'movies');
INSERT INTO tags (task_id, tag) VALUES (11, 'health');
INSERT INTO tags (task_id, tag) VALUES (11, 'appointments');