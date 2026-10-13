-- ==================== 用户-部门关联 ====================
-- 给 sys_account 表新增 department_id 字段，关联 sys_department.id
ALTER TABLE `sys_account` ADD COLUMN `department_id` BIGINT DEFAULT NULL COMMENT '所属部门ID' AFTER `phone`;

-- 添加外键约束
ALTER TABLE `sys_account`
    ADD CONSTRAINT `fk_account_department`
    FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`)
    ON DELETE SET NULL ON UPDATE CASCADE;

-- 添加索引
ALTER TABLE `sys_account` ADD INDEX `idx_account_department_id` (`department_id`);

-- 为已有账号关联部门（将 admin 关联到研发部）
UPDATE `sys_account` a
SET a.`department_id` = (SELECT d.`id` FROM `sys_department` d WHERE d.`code` = 'RD' AND d.`deleted` = 0 LIMIT 1)
WHERE a.`username` = 'admin' AND a.`deleted` = 0;
