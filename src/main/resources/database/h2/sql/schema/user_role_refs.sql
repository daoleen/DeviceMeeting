CREATE TABLE IF NOT EXISTS user_role_refs (
  `user_id` BIGINT NOT NULL,
  `role_id` INT    NOT NULL,

  PRIMARY KEY ("user_id", "role_id"),
  FOREIGN KEY ("user_id") REFERENCES "users" (id),
  FOREIGN KEY ("role_id") REFERENCES "roles" (id)
);