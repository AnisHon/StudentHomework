

```sql
# account
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_pk_2` (`username`),
  UNIQUE KEY `account_pk` (`email`)
);

create table class
(
    id   int auto_increment
        primary key,
    name varchar(50) not null comment '班级名'
);

CREATE TABLE `subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

create table teacher
(
    id         int auto_increment
        primary key,
    name       varchar(50)       not null,
    gender     enum ('男', '女') null,
    account_id int               not null,
    constraint teacher_pk
        unique (account_id),
    constraint teacher_account_id_fk
        foreign key (account_id) references db_homework_01.account (id)
            on delete cascade
);

create table student
(
    id         int auto_increment
        primary key,
    name       varchar(50)       not null,
    gender     enum ('男', '女') null,
    age        int               not null,
    address    varchar(255)      null,
    account_id int               not null,
    class_id   int               not null,
    constraint student_pk_2
        unique (account_id),
    constraint student_account_id_fk
        foreign key (account_id) references db_homework_01.account (id)
            on update cascade on delete cascade,
    constraint student_class_id_fk
        foreign key (class_id) references db_homework_01.class (id)
);

create table score
(
    id         int auto_increment
        primary key,
    student_id int not null,
    subject_id int not null,
    score      int not null comment '实际分数应当 / 1000',
    constraint score_student_id_fk
        foreign key (student_id) references db_homework_01.student (id)
            on delete cascade,
    constraint score_subject_id_fk
        foreign key (subject_id) references db_homework_01.subject (id)
            on delete cascade
);
```
