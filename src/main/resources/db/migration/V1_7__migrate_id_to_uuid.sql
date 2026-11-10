
-- 1. Drop the existing foreign keys from borrowing
ALTER TABLE borrowing
DROP CONSTRAINT fkpgw9do56tnua1aepwswrbwifh;

ALTER TABLE borrowing
DROP CONSTRAINT fkphewts11cr4frh88ht3y8ua3s;

-- 2. Drop the existing primary key constraints
ALTER TABLE borrowing DROP CONSTRAINT borrowing_pkey;
ALTER TABLE book DROP CONSTRAINT book_pkey;
ALTER TABLE member DROP CONSTRAINT member_pkey;

-- 3. Remove the old ID columns
ALTER TABLE borrowing DROP COLUMN id;
ALTER TABLE borrowing DROP COLUMN book_id;
ALTER TABLE borrowing DROP COLUMN member_id;

ALTER TABLE book DROP COLUMN id;
ALTER TABLE member DROP COLUMN id;

-- 4. Recreate the ID columns as UUIDs
ALTER TABLE book
    ADD COLUMN id UUID NOT NULL DEFAULT gen_random_uuid();

ALTER TABLE member
    ADD COLUMN id UUID NOT NULL DEFAULT gen_random_uuid();

ALTER TABLE borrowing
    ADD COLUMN id UUID NOT NULL DEFAULT gen_random_uuid();

-- 5. Recreate borrowing relationship columns as UUIDs
ALTER TABLE borrowing
    ADD COLUMN book_id UUID NOT NULL;

ALTER TABLE borrowing
    ADD COLUMN member_id UUID NOT NULL;

-- 6. Recreate primary keys
ALTER TABLE book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);

ALTER TABLE member
    ADD CONSTRAINT member_pkey PRIMARY KEY (id);

ALTER TABLE borrowing
    ADD CONSTRAINT borrowing_pkey PRIMARY KEY (id);

-- 7. Recreate foreign keys
ALTER TABLE borrowing
    ADD CONSTRAINT fk_borrowing_book
        FOREIGN KEY (book_id) REFERENCES book(id);

ALTER TABLE borrowing
    ADD CONSTRAINT fk_borrowing_member
        FOREIGN KEY (member_id) REFERENCES member(id);
