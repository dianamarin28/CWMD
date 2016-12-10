-- use dbName

INSERT INTO `department` (`name`, `uuid`) VALUES ('Matematica', 'abcdefg');
INSERT INTO `department` (`name`, `uuid`) VALUES ('Informatica', 'bcdefgh');
INSERT INTO `department` (`name`, `uuid`) VALUES ('Matematica Informatica Maghiara', 'cdefghi');

INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Dean', 'sdbzxef', '1');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Vice-Dean', 'dbsfcfb', '1');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Secretary', 'ewgrbab', '1');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Dean', 'rabbrbr', '2');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Vice-Dean', 'asbbfab', '2');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Secretary', 'abrbrbr', '2');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Dean', 'tntnymg', '3');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Vice-Dean', 'renesne', '3');
INSERT INTO `function` (`type`, `uuid`, `departmentId`) VALUES ('Secretary', 'gthYjuk', '3');

INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('john@sth.com', 'John', 'Doe', 'john12', 'john12', 'ADMINISTRATOR', 'ghyjuik', '1');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('paul@sth.com', 'Paul', 'Smith', 'paul12', 'paul12', 'MANAGER', 'kilopol', '2');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('helen@sth.com', 'Helen', 'Collins', 'helen12', 'helen12', 'READER', 'hyju78u', '3');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('james@sth.com', 'James', 'Wells', 'james12', 'james12', 'CONTRIBUTOR', 'lolplki', '2');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('alice@sth.com', 'Alice', 'Knight', 'alice12', 'alice12', 'MANAGER', 'saplbwe', '1');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('lily@sth.com', 'Lily', 'Duncan', 'lily12', 'lily12', 'CONTRIBUTOR', 'nhyudef', '2');

INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('daniel@sth.com', 'Daniel', 'Owens', 'daniel12', 'daniel12', 'ADMINISTRATOR', 'gthyjui', '4');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('sarah@sth.com', 'Sarah', 'Porter', 'sarah12', 'sarah12', 'MANAGER', 'ghjytrF', '5');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('ryan@sth.com', 'Ryan', 'Kelley', 'ryan12', 'ryan12', 'READER', 'swdefrg', '6');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('susan@sth.com', 'Susan', 'Freeman', 'susan12', 'susan12', 'CONTRIBUTOR', 'hyjukip', '5');

INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('anna@sth.com', 'Anna', 'Ross', 'anna12', 'anna12', 'ADMINISTRATOR', 'hyjcdef', '7');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('steve@sth.com', 'Steve', 'Coleman', 'steve12', 'steve12', 'MANAGER', 'gtywedr', '8');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('julie@sth.com', 'Julie', 'Ellis', 'julie12', 'julie12', 'READER', 'ploderf', '9');
INSERT INTO `user` (`email`, `firstName`, `lastName`, `password`, `userName`, `userRole`, `uuid`, `functionId`) 
VALUES ('jane@sth.com', 'Jane', 'Hunt', 'jane12', 'jane12', 'CONTRIBUTOR', 'ssddeer', '8');
