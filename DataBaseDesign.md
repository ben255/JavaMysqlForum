table user:
+------------+-----------------+------+-----+---------+----------------+
| Field      | Type            | Null | Key | Default | Extra          |
+------------+-----------------+------+-----+---------+----------------+
| id         | int(8) unsigned | NO   | PRI | NULL    | auto_increment |
| first_name | varchar(80)     | NO   |     | NULL    |                |
| last_name  | varchar(80)     | NO   |     | NULL    |                |
| email      | varchar(80)     | NO   |     | NULL    |                |
| birth_date | int(4)          | NO   |     | NULL    |                |
| password   | varchar(80)     | NO   |     | NULL    |                |
+------------+-----------------+------+-----+---------+----------------+

table thread:
+---------+-----------------+------+-----+-------------------+----------------+
| Field   | Type            | Null | Key | Default           | Extra          |
+---------+-----------------+------+-----+-------------------+----------------+
| id      | int(8) unsigned | NO   | PRI | NULL              | auto_increment |
| user_id | int(8) unsigned | NO   | MUL | NULL              |                |
| name    | varchar(80)     | NO   |     | NULL              |                |
| ts      | timestamp       | NO   |     | CURRENT_TIMESTAMP |                |
+---------+-----------------+------+-----+-------------------+----------------+

table post:
+-----------+-----------------+------+-----+-------------------+----------------+
| Field     | Type            | Null | Key | Default           | Extra          |
+-----------+-----------------+------+-----+-------------------+----------------+
| id        | int(8) unsigned | NO   | PRI | NULL              | auto_increment |
| user_id   | int(8) unsigned | NO   | MUL | NULL              |                |
| thread_id | int(8) unsigned | NO   | MUL | NULL              |                |
| post      | varchar(600)    | YES  |     | NULL              |                |
| ts        | timestamp       | NO   |     | CURRENT_TIMESTAMP |                |
+-----------+-----------------+------+-----+-------------------+----------------+
