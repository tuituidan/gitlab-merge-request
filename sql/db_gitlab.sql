

-- ----------------------------
-- Table structure for t_developer
-- ----------------------------
DROP TABLE IF EXISTS "db_gitlab"."t_developer";
CREATE TABLE "db_gitlab"."t_developer" (
  "c_id" varchar(32) NOT NULL,
  "c_loginid" varchar(100),
  "c_name" varchar(100),
  "c_password" varchar(100),
  "c_avatar_url" varchar(300),
  "n_project_count" int4,
  "n_merge_request_count" int4,
  "n_discussion_count" int4,
  "n_nodiscussion_count" int4,
  "dt_create_time" timestamp(3),
  "dt_update_time" timestamp(3)
)
;

-- ----------------------------
-- Table structure for t_markdown
-- ----------------------------
DROP TABLE IF EXISTS "db_gitlab"."t_markdown";
CREATE TABLE "db_gitlab"."t_markdown" (
  "n_id" int4 NOT NULL,
  "c_markdown" text
)
;

-- ----------------------------
-- Table structure for t_merge_request
-- ----------------------------
DROP TABLE IF EXISTS "db_gitlab"."t_merge_request";
CREATE TABLE "db_gitlab"."t_merge_request" (
  "n_id" int4 NOT NULL,
  "n_merge_id" int4,
  "n_project_id" int4,
  "c_source_branch" varchar(300),
  "c_web_url" varchar(600),
  "c_author" varchar(100),
  "n_discussion_count" int4,
  "n_change_files" int4,
  "n_change_lines" int4,
  "n_add_lines" int4,
  "n_delete_lines" int4,
  "dt_merged_time" timestamp(3),
  "dt_create_time" timestamp(3),
  "dt_update_time" timestamp(3)
)
;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS "db_gitlab"."t_project";
CREATE TABLE "db_gitlab"."t_project" (
  "n_id" int4 NOT NULL,
  "c_name" varchar(300),
  "c_name_space" varchar(600),
  "c_desc" varchar(900),
  "c_create_user" varchar(100),
  "c_target_branch" varchar(300),
  "arr_labels" varchar[],
  "arr_developers" varchar[],
  "dt_create_time" timestamp(3),
  "dt_update_time" timestamp(3)
)
;

-- ----------------------------
-- Primary Key structure for table t_developer
-- ----------------------------
ALTER TABLE "db_gitlab"."t_developer" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("c_id");

-- ----------------------------
-- Primary Key structure for table t_markdown
-- ----------------------------
ALTER TABLE "db_gitlab"."t_markdown" ADD CONSTRAINT "t_markdown_pkey" PRIMARY KEY ("n_id");

-- ----------------------------
-- Primary Key structure for table t_merge_request
-- ----------------------------
ALTER TABLE "db_gitlab"."t_merge_request" ADD CONSTRAINT "t_merge_request_pkey" PRIMARY KEY ("n_id");

-- ----------------------------
-- Primary Key structure for table t_project
-- ----------------------------
ALTER TABLE "db_gitlab"."t_project" ADD CONSTRAINT "t_project_pkey" PRIMARY KEY ("n_id");
