CREATE TABLE card_message (
  id       INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  message  VARCHAR(255) NOT NULL,
  added_on DATETIME                          DEFAULT CURRENT_TIMESTAMP(),
);


CREATE TABLE card (
  id         CHAR(3) NOT NULL PRIMARY KEY,
  message_id INTEGER NOT NULL,
  created_on DATETIME DEFAULT CURRENT_TIMESTAMP(),
  CONSTRAINT fk_card_card_message_id FOREIGN KEY (message_id) REFERENCES card_message (id) ON DELETE RESTRICT
);


CREATE TABLE card_registration (
  id            UUID          NOT NULL PRIMARY KEY,
  card_id        CHAR(3)       NOT NULL,
  first_name    VARCHAR(50)   NULL,
  location      VARCHAR(100)  NULL,
  location_longitude     DECIMAL(9, 6) NULL,
  location_latitude      DECIMAL(9, 6) NULL,
  user_longitude     DECIMAL(9, 6) NULL,
  user_latitude      DECIMAL(9, 6) NULL,
  selfie_uri    VARCHAR       NULL, -- URI. Could be local but could also be S3
  registered_on DATETIME DEFAULT CURRENT_TIMESTAMP(),
  CONSTRAINT fk_card_registration_card_id FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
);


CREATE TABLE selfie_upload (-- records in this table are deleted on timeout and when the registration was completed
  id              UUID    NOT NULL PRIMARY KEY, -- UUID
  card_id          CHAR(3) NOT NULL, -- uploaded related to this card
  image_uri       VARCHAR NOT NULL, -- URI. Could be local but could also be S3
  image_mime_type VARCHAR NOT NULL,
  uploaded_on     DATETIME DEFAULT CURRENT_TIMESTAMP(), -- if image is not within X minutes, it is automatically deleted
  CONSTRAINT fk_selfie_upload_card_id FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE RESTRICT -- can't autodelete as we need to do cleanup
);

-- insert initial card_messages
INSERT INTO card_message (id, message) VALUES (1, 'Ik wens jou alvast hele fijne feestdagen en een geweldig nieuwjaar!');
INSERT INTO card_message (id, message) VALUES (2, 'Geniet elke dag alsof het je laatste is. Lach wanneer je kan!');
INSERT INTO card_message (id, message) VALUES (3, 'A smile everyday, keeps the darkness away');
INSERT INTO card_message (id, message) VALUES (4, 'Hoe vaak heb jij al gelachen vandaag?');
INSERT INTO card_message (id, message) VALUES (5, 'Wanneer was jouw laatste goede daad?');
INSERT INTO card_message (id, message) VALUES (6, 'Als de trein op tijd rijdt, dan is dat perrongeluk...');
INSERT INTO card_message (id, message) VALUES (7, 'Wat maakt iemand zijn dag? Juist, dat is een welgemeende glimlach!');
INSERT INTO card_message (id, message) VALUES (8, 'Ik wens jou een fijne dag. Geef het door!');
INSERT INTO card_message (id, message) VALUES (9, 'Je mag trots zijn op wie je bent');

-- insert already generated cards
INSERT INTO card (id, message_id) VALUES ('BGD', 1);
INSERT INTO card (id, message_id) VALUES ('4WK', 1);
INSERT INTO card (id, message_id) VALUES ('NVS', 1);
INSERT INTO card (id, message_id) VALUES ('SD5', 1);
INSERT INTO card (id, message_id) VALUES ('2TI', 1);
INSERT INTO card (id, message_id) VALUES ('UL2', 1);
INSERT INTO card (id, message_id) VALUES ('HZ7', 1);
INSERT INTO card (id, message_id) VALUES ('3DJ', 1);
INSERT INTO card (id, message_id) VALUES ('PBL', 1);
INSERT INTO card (id, message_id) VALUES ('QRI', 1);
INSERT INTO card (id, message_id) VALUES ('KEH', 2);
INSERT INTO card (id, message_id) VALUES ('0OD', 2);
INSERT INTO card (id, message_id) VALUES ('4BR', 2);
INSERT INTO card (id, message_id) VALUES ('4N9', 2);
INSERT INTO card (id, message_id) VALUES ('35O', 2);
INSERT INTO card (id, message_id) VALUES ('UJ1', 2);
INSERT INTO card (id, message_id) VALUES ('1WU', 2);
INSERT INTO card (id, message_id) VALUES ('YMZ', 2);
INSERT INTO card (id, message_id) VALUES ('XGX', 2);
INSERT INTO card (id, message_id) VALUES ('MQF', 2);
INSERT INTO card (id, message_id) VALUES ('DK1', 3);
INSERT INTO card (id, message_id) VALUES ('L3C', 3);
INSERT INTO card (id, message_id) VALUES ('OF8', 3);
INSERT INTO card (id, message_id) VALUES ('RXL', 3);
INSERT INTO card (id, message_id) VALUES ('BG5', 3);
INSERT INTO card (id, message_id) VALUES ('3GN', 3);
INSERT INTO card (id, message_id) VALUES ('IP3', 3);
INSERT INTO card (id, message_id) VALUES ('MVY', 3);
INSERT INTO card (id, message_id) VALUES ('92T', 3);
INSERT INTO card (id, message_id) VALUES ('A1D', 3);
INSERT INTO card (id, message_id) VALUES ('2RF', 4);
INSERT INTO card (id, message_id) VALUES ('TYN', 4);
INSERT INTO card (id, message_id) VALUES ('LXO', 4);
INSERT INTO card (id, message_id) VALUES ('M8J', 4);
INSERT INTO card (id, message_id) VALUES ('NII', 4);
INSERT INTO card (id, message_id) VALUES ('5QF', 4);
INSERT INTO card (id, message_id) VALUES ('F7F', 4);
INSERT INTO card (id, message_id) VALUES ('54O', 4);
INSERT INTO card (id, message_id) VALUES ('K3A', 4);
INSERT INTO card (id, message_id) VALUES ('317', 4);
INSERT INTO card (id, message_id) VALUES ('K9M', 5);
INSERT INTO card (id, message_id) VALUES ('7FR', 5);
INSERT INTO card (id, message_id) VALUES ('U8Q', 5);
INSERT INTO card (id, message_id) VALUES ('G6Z', 5);
INSERT INTO card (id, message_id) VALUES ('K1H', 5);
INSERT INTO card (id, message_id) VALUES ('HDS', 5);
INSERT INTO card (id, message_id) VALUES ('L21', 5);
INSERT INTO card (id, message_id) VALUES ('HSO', 5);
INSERT INTO card (id, message_id) VALUES ('6SX', 5);
INSERT INTO card (id, message_id) VALUES ('GII', 5);
INSERT INTO card (id, message_id) VALUES ('NGV', 6);
INSERT INTO card (id, message_id) VALUES ('KTM', 6);
INSERT INTO card (id, message_id) VALUES ('UNN', 6);
INSERT INTO card (id, message_id) VALUES ('LCH', 6);
INSERT INTO card (id, message_id) VALUES ('K46', 6);
INSERT INTO card (id, message_id) VALUES ('8NP', 6);
INSERT INTO card (id, message_id) VALUES ('HSG', 6);
INSERT INTO card (id, message_id) VALUES ('RLI', 6);
INSERT INTO card (id, message_id) VALUES ('9AQ', 6);
INSERT INTO card (id, message_id) VALUES ('HJ9', 6);
INSERT INTO card (id, message_id) VALUES ('1RE', 7);
INSERT INTO card (id, message_id) VALUES ('BQD', 7);
INSERT INTO card (id, message_id) VALUES ('6AS', 7);
INSERT INTO card (id, message_id) VALUES ('AWE', 7);
INSERT INTO card (id, message_id) VALUES ('ITS', 7);
INSERT INTO card (id, message_id) VALUES ('QTF', 7);
INSERT INTO card (id, message_id) VALUES ('1UP', 7);
INSERT INTO card (id, message_id) VALUES ('7WW', 7);
INSERT INTO card (id, message_id) VALUES ('0U5', 7);
INSERT INTO card (id, message_id) VALUES ('U2U', 7);
INSERT INTO card (id, message_id) VALUES ('KBH', 8);
INSERT INTO card (id, message_id) VALUES ('I6V', 8);
INSERT INTO card (id, message_id) VALUES ('QUP', 8);
INSERT INTO card (id, message_id) VALUES ('8DQ', 8);
INSERT INTO card (id, message_id) VALUES ('GDA', 8);
INSERT INTO card (id, message_id) VALUES ('DTR', 8);
INSERT INTO card (id, message_id) VALUES ('6RO', 8);
INSERT INTO card (id, message_id) VALUES ('EZ4', 8);
INSERT INTO card (id, message_id) VALUES ('BS1', 8);
INSERT INTO card (id, message_id) VALUES ('SOY', 8);
INSERT INTO card (id, message_id) VALUES ('V39', 9);
INSERT INTO card (id, message_id) VALUES ('1S3', 8);
INSERT INTO card (id, message_id) VALUES ('Y3C', 8);
INSERT INTO card (id, message_id) VALUES ('1JA', 8);
INSERT INTO card (id, message_id) VALUES ('J7K', 8);
INSERT INTO card (id, message_id) VALUES ('YDX', 8);
INSERT INTO card (id, message_id) VALUES ('XT4', 8);
INSERT INTO card (id, message_id) VALUES ('U4F', 8);
INSERT INTO card (id, message_id) VALUES ('3RB', 8);
INSERT INTO card (id, message_id) VALUES ('VSU', 8);
INSERT INTO card (id, message_id) VALUES ('TT4', 1);
INSERT INTO card (id, message_id) VALUES ('VQ9', 1);
INSERT INTO card (id, message_id) VALUES ('A0I', 1);
INSERT INTO card (id, message_id) VALUES ('MJ7', 1);
INSERT INTO card (id, message_id) VALUES ('HIO', 1);
INSERT INTO card (id, message_id) VALUES ('XRT', 1);
INSERT INTO card (id, message_id) VALUES ('67D', 1);
INSERT INTO card (id, message_id) VALUES ('B9C', 1);
INSERT INTO card (id, message_id) VALUES ('NVJ', 1);
INSERT INTO card (id, message_id) VALUES ('1V8', 1);