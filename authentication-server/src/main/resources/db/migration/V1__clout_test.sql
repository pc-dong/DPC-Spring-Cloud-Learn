create TABLE user(
  id BIGINT PRIMARY KEY,
  username VARCHAR(128),
  name     VARCHAR(128),
  insert_time     DATETIME,
  password VARCHAR(128),
  department VARCHAR(128)
);