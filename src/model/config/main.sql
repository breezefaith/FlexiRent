/*
 Navicat Premium Data Transfer

 Source Server         : sqlite
 Source Server Type    : SQLite
 Source Server Version : 3021000
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3021000
 File Encoding         : 65001

 Date: 07/10/2018 15:07:57
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for apartment
-- ----------------------------
DROP TABLE IF EXISTS "apartment";
CREATE TABLE "apartment" (
  "property_id" real NOT NULL,
  "per_day_fee" real,
  PRIMARY KEY ("property_id")
);

-- ----------------------------
-- Records of apartment
-- ----------------------------
INSERT INTO "apartment" VALUES ('A_700BM', 319.0);
INSERT INTO "apartment" VALUES ('A_701BM', 210.0);
INSERT INTO "apartment" VALUES ('A_702BM', 319.0);
INSERT INTO "apartment" VALUES ('A_703BM', 143.0);
INSERT INTO "apartment" VALUES ('A_704BM', 210.0);
INSERT INTO "apartment" VALUES ('A_705BM', 319.0);
INSERT INTO "apartment" VALUES ('A_706BM', 319.0);
INSERT INTO "apartment" VALUES ('A_707BM', 210.0);
INSERT INTO "apartment" VALUES ('A_708BM', 143.0);
INSERT INTO "apartment" VALUES ('A_709BM', 210.0);

-- ----------------------------
-- Table structure for premium_suite
-- ----------------------------
DROP TABLE IF EXISTS "premium_suite";
CREATE TABLE "premium_suite" (
  "property_id" real NOT NULL,
  "last_maintenance_date" text,
  "per_day_fee" real,
  PRIMARY KEY ("property_id")
);

-- ----------------------------
-- Records of premium_suite
-- ----------------------------
INSERT INTO "premium_suite" VALUES ('S_633WS', 29092018, 554.0);
INSERT INTO "premium_suite" VALUES ('S_634WS', '04102018', 554.0);
INSERT INTO "premium_suite" VALUES ('S_635WS', '04102018', 554.0);
INSERT INTO "premium_suite" VALUES ('S_636WS', 27092018, 554.0);
INSERT INTO "premium_suite" VALUES ('S_637WS', 28092018, 554.0);

-- ----------------------------
-- Table structure for rental_property
-- ----------------------------
DROP TABLE IF EXISTS "rental_property";
CREATE TABLE "rental_property" (
  "property_id" text NOT NULL,
  "street_number" text,
  "street_name" text,
  "suburb" text,
  "bedrooms_number" INTEGER,
  "property_type" text,
  "property_status" text,
  "image_name" text,
  "description" text,
  "create_time" text,
  PRIMARY KEY ("property_id")
);

-- ----------------------------
-- Records of rental_property
-- ----------------------------
INSERT INTO "rental_property" VALUES ('A_700BM', 700, 'Bourke Street', 'Melbourne', 3, 'apartment', 'currently available for rent', 'A_700BM.jpg', 'This is A_700BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('S_633WS', 633, 'Whiteman Street', 'Southbank', 3, 'premium_suite', 'currently available for rent', 'S_633WS.jpg', 'This is S_633WS.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:36');
INSERT INTO "rental_property" VALUES ('A_701BM', 701, 'Bourke Street', 'Melbourne', 2, 'apartment', 'being rented', 'A_701BM.jpg', 'This is A_701BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_702BM', 702, 'Bourke Street', 'Melbourne', 3, 'apartment', 'currently available for rent', 'A_702BM.jpg', 'This is A_702BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_703BM', 703, 'Bourke Street', 'Melbourne', 1, 'apartment', 'currently available for rent', 'A_703BM.jpg', 'This is A_703BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_704BM', 704, 'Bourke Street', 'Melbourne', 2, 'apartment', 'currently available for rent', 'A_704BM.jpg', 'This is A704BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_705BM', 705, 'Bourke Street', 'Melbourne', 3, 'apartment', 'currently available for rent', 'A_705BM.jpg', 'This is A705BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_706BM', 706, 'Bourke Street', 'Melbourne', 3, 'apartment', 'currently available for rent', 'A_706BM.jpg', 'This is A706BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_707BM', 707, 'Bourke Street', 'Melbourne', 2, 'apartment', 'currently available for rent', 'A_707BM.jpg', 'This is A707BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_708BM', 708, 'Bourke Street', 'Melbourne', 1, 'apartment', 'currently available for rent', 'A_708BM.jpg', 'This is A708BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('A_709BM', 709, 'Bourke Street', 'Melbourne', 2, 'apartment', 'currently available for rent', 'A_709BM.jpg', 'This is A709BM.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('S_634WS', 634, 'Whiteman Street', 'Southbank', 3, 'premium_suite', 'currently available for rent', 'S_634WS.jpg', 'This is S_634WS.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('S_635WS', 635, 'Whiteman Street', 'Southbank', 3, 'premium_suite', 'currently available for rent', 'S_635WS.jpg', 'This is S_635WS.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('S_636WS', 636, 'Whiteman Street', 'Southbank', 3, 'premium_suite', 'currently available for rent', 'S_636WS.jpg', 'This is S_636WS.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');
INSERT INTO "rental_property" VALUES ('S_637WS', 637, 'Whiteman Street', 'Southbank', 3, 'premium_suite', 'currently available for rent', 'S_637WS.jpg', 'This is S_637WS.The scale of the community: Only six apartment building with a flower garden in the center. There is a pond and lawn in the flower garden. To the left there is a gym. The apartment is on the 4th floor in Building No. 2. The layout of the apartment include one bedroom, one living room, one kitchen and bathroom, and a balcony.', '2018-10-04 06:36:35');

-- ----------------------------
-- Table structure for rental_record
-- ----------------------------
DROP TABLE IF EXISTS "rental_record";
CREATE TABLE "rental_record" (
  "record_id" text NOT NULL,
  "property_id" text,
  "customer_id" text,
  "rent_date" text,
  "estimated_return_date" text,
  "actual_return_date" text,
  "rental_fee" real,
  "late_fee" real,
  PRIMARY KEY ("record_id")
);

-- ----------------------------
-- Records of rental_record
-- ----------------------------
INSERT INTO "rental_record" VALUES ('A_701BM_CUS700_01102018', 'A_701BM', 'CUS700', '2018-10-01', '2018-10-02', NULL, NULL, NULL);
INSERT INTO "rental_record" VALUES ('A_701BM_CUS700_22092018', 'A_701BM', 'CUS700', '2018-09-22', '2018-09-23', '2018-09-23', 319.0, 0.0);
INSERT INTO "rental_record" VALUES ('A_700BM_CUS700_01102018', 'A_700BM', 'CUS700', '2018-10-01', '2018-10-03', '2018-10-04', 638.0, 366.85);
INSERT INTO "rental_record" VALUES ('S_633WS_ccc_04102018', 'S_633WS', 'ccc', '2018-10-04', '2018-10-05', '2018-10-07', 554.0, 1324.0);
INSERT INTO "rental_record" VALUES ('A_700BM_ccc_04102018', 'A_700BM', 'ccc', '2018-10-04', '2018-10-06', '2018-10-05', 319.0, 0.0);

PRAGMA foreign_keys = true;
