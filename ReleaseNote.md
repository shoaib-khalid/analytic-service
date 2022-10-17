#################################################
# Version 0.0.9 | 17-Oct-2022
##################################################
Receive cart list in customer activities
Save session cart into new table : customer_session_carts

##DB Changes:
CREATE TABLE `customer_session_carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sessionId` varchar(50) DEFAULT NULL,
  `cartId` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cartId` (`cartId`),
  KEY `sessionId` (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


#################################################
# Version 0.0.8 | 14-Oct-2022
##################################################
New scheduler to update channel in customer activities table


#################################################
# Version 0.0.7| 21-Sept-2022
##################################################
Generate total unique user by country

##DB Changes:
ALTER TABLE total_unique_user_overall ADD country VARCHAR(3) default 'MYS';
ALTER TABLE total_unique_user ADD country VARCHAR(3) default 'MYS';


##################################################
# Version 0.0.6| 18-Aug-2022
##################################################
Put google api key in config
Add new parameter channel in customer_activities

##Config changes:
google.query.location.apikey=AIzaSyBM0MCDypNSq6JIqTVPAfRBWy5lXdrqhJE

##DB Changes:
ALTER TABLE customer_activities ADD channel VARCHAR(50);


##################################################
# Version 0.0.5| 12-July-2022
##################################################

Create new table to store customer session and its location details
New cron to query google to update address based on latitude & longitude

#DB Changes:
CREATE TABLE customer_session (
sessionId VARCHAR(50) PRIMARY KEY,
latitude VARCHAR(50),
longitude VARCHAR(50),
address VARCHAR(200),
city VARCHAR(50),
postcode VARCHAR(50),
state VARCHAR(50),
country VARCHAR(50),
created DATETIME,
updated DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

ALTER TABLE total_unique_user ADD totalUniqueGuest INT(11);

CREATE TABLE total_unique_user_overall (
id BIGINT PRIMARY  KEY AUTO_INCREMENT,
dt DATE,
totalUnique INT,
totalUniqueGuest INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


#Config Changes:
update.location.scheduler.enabled=true


##################################################
# Version 0.0.4| 04-July-2022
##################################################
Add new parameter longitude & latitude

#DB Changes:
ALTER TABLE customer_activities ADD latitude;
ALTER TABLE customer_activities ADD longitude;
ALTER TABLE customer_activities ADD address  varchar(200);
ALTER TABLE customer_activities ADD city  varchar(50);
ALTER TABLE customer_activities ADD postcode  varchar(50);
ALTER TABLE customer_activities ADD state varchar(50);
ALTER TABLE customer_activities ADD country varchar(50);


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