CREATE TABLE IF NOT EXISTS USER (
  id            INTEGER PRIMARY KEY AUTO_INCREMENT,
  login         VARCHAR(50) UNIQUE,
  password      VARCHAR(50),
  salt          VARCHAR(50),
  photo_link    VARCHAR(50)
);
