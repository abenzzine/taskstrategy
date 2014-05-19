CREATE TABLE users (
  USER_ID varchar(45) NOT NULL,
  USERNAME varchar(45) NOT NULL,
  PASSWORD varchar(200) NOT NULL,
  ENABLED tinyint(1) NOT NULL,
  PRIMARY KEY (USER_ID)
);

CREATE TABLE Tag (
  name varchar(45) NOT NULL,
  userId varchar(45) NOT NULL,
  favorite bit(1) NOT NULL,
  displayColor varchar(45) DEFAULT NULL,
  createDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  lastModifiedDate timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (name,userId),
  KEY FK_User_Id_idx (userId),
  KEY INDX_FAV (favorite),
  CONSTRAINT FK_User_Id_TaskTag FOREIGN KEY (userId) REFERENCES users (USER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE TaskPriority (
  id varchar(45) NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Task (
  id varchar(45) NOT NULL,
  userId varchar(45) NOT NULL,
  parentTaskId varchar(45),
  name varchar(200) NOT NULL,
  description text,
  priorityId varchar(45) NOT NULL,
  dueDate date,
  createDate timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  lastModifiedDate timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  complete tinyint(1) DEFAULT '0',
  passCount int(11) DEFAULT 0,
  frequencyId varchar(45) NOT NULL ,
  notification bit (1) DEFAULT b'0',
  PRIMARY KEY (id),
  KEY FK_Task_Priority_idx (priorityId),
  KEY FK_User_Id_idx (userId),
  KEY INDX_DUE_DATE (dueDate),
  KEY INDX_NAME (name),
  CONSTRAINT FK_User_Id FOREIGN KEY (userId) REFERENCES users (USER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_Task_Priority FOREIGN KEY (priorityId) REFERENCES TaskPriority (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);



CREATE TABLE TaskTag (
  taskId varchar(50) NOT NULL,
  name varchar(45) NOT NULL,
  userId varchar(45) NOT NULL,
  KEY fk_TaskTag_1_idx (name),
  KEY fk_TaskTag_3_idx (taskId),
  KEY FK_Tag_UserId (userId),
  CONSTRAINT FK_Tag_UserId FOREIGN KEY (userId) REFERENCES users (USER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_Tag_Name FOREIGN KEY (name) REFERENCES Tag (name) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_TaskTag_TaskId FOREIGN KEY (taskId) REFERENCES Task (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);