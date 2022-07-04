##################################################
# Version 0.0.4| 04-July-2022
##################################################
Add new parameter longitude & latitude

#DB Changes:
ALTER TABLE customer_activities ADD latitude;
ALTER TABLE customer_activities ADD longitude;


##################################################
# Version 0.0.3| 22-June-2022
##################################################
Bug fix for scheduler


##################################################
# Version 0.0.2| 21-June-2022
##################################################
Scheduler to remove old record
Scheduler to remove generate summary

#Properties changes:
generate.summary.scheduler.enabled=true
remove.history.scheduler.enabled=true

#DB Changes:
CREATE TABLE `customer_activities_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dt` date DEFAULT NULL,
  `storeId` varchar(50) DEFAULT NULL,
  `browser` varchar(50) DEFAULT NULL,
  `device` varchar(50) DEFAULT NULL,
  `os` varchar(50) DEFAULT NULL,
  `page` varchar(200) DEFAULT NULL,
  `totalCount` int DEFAULT NULL,
  `totalUniqueUser` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt` (`dt`,`storeId`,`browser`,`device`,`os`,`page`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


##################################################
# Version 0.0.1| 08-March-2022
##################################################
Initial version