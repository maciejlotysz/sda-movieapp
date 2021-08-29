ALTER TABLE movies ADD COLUMN added_at TIMESTAMPTZ not null;
SET TIMEZONE = 'Europe/Warsaw';