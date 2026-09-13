-- One-time migration for the existing local AdminFaculty_Details database.
USE AdminFaculty_Details;
ALTER TABLE Admin_Faculty ADD COLUMN IF NOT EXISTS Email VARCHAR(255);
