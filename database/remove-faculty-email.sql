-- One-time migration for the local AdminFaculty_Details database.
-- Run this after stopping the backend services that use Admin_Faculty.
USE AdminFaculty_Details;
ALTER TABLE Admin_Faculty ADD COLUMN IF NOT EXISTS Email VARCHAR(255);
