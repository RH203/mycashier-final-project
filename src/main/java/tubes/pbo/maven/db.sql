-- we don't know how to generate root <with-no-name> (class Root) :(

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on . to root@'127.0.0.1';

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on . to root@'::1';

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on . to root@localhost;

create table menu
(
    id_menu   int auto_increment
        primary key,
    nama_menu varchar(100)   null,
    Kategori  varchar(100)   null,
    harga     decimal(10, 2) null
);

create table pembayaran
(
    id_pembayaran      int auto_increment
        primary key,
    id_pesanan         int                                   null,
    total_pembayaran   decimal(10, 2)                        null,
    tanggal_pembayaran timestamp default current_timestamp() not null
);

create table detail_pesanan
(
    id_detail_pesanan int auto_increment
        primary key,
    id_menu           int            null,
    jumlah            int            null,
    harga_total       decimal(10, 2) null,
    Status            varchar(12)    null,
    id_pembayaran     int            null,
    constraint detail_pesanan_ibfk_2
        foreign key (id_menu) references menu (id_menu),
    constraint detail_pesanan_ibfk_3
        foreign key (id_pembayaran) references pembayaran (id_pembayaran)
);

create index id_menu
    on detail_pesanan (id_menu);

create index id_pesanan
    on pembayaran (id_pesanan);

create definer = root@localhost view detail_pesanan_diproses_view as
select dp.id_detail_pesanan AS id_detail_pesanan,
       dp.id_menu           AS id_menu,
       dp.jumlah            AS jumlah,
       dp.harga_total       AS harga_total,
       dp.Status            AS Status,
       m.nama_menu          AS nama_menu,
       m.Kategori           AS Kategori
from (mycashier-pbo-final.detail_pesanan dp join mycashier-pbo-final.menu m
      on (dp.id_menu = m.id_menu))
where dp.Status = 'Diproses';

create definer = root@localhost view makanan_view as
select mycashier-pbo-final.menu.id_menu   AS id_menu,
       mycashier-pbo-final.menu.nama_menu AS nama_menu,
       mycashier-pbo-final.menu.harga     AS harga,
       mycashier-pbo-final.menu.Kategori  AS katergori
from mycashier-pbo-final.menu
where mycashier-pbo-final.menu.Kategori = 'Makanan';

create definer = root@localhost view minuman_view as
select mycashier-pbo-final.menu.id_menu   AS id_menu,
       mycashier-pbo-final.menu.nama_menu AS nama_menu,
       mycashier-pbo-final.menu.harga     AS harga,
       mycashier-pbo-final.menu.Kategori  AS katergori
from mycashier-pbo-final.menu
where mycashier-pbo-final.menu.Kategori = 'Minuman';

create definer = root@localhost view snack_view as
select mycashier-pbo-final.menu.id_menu   AS id_menu,
       mycashier-pbo-final.menu.nama_menu AS nama_menu,
       mycashier-pbo-final.menu.harga     AS harga,
       mycashier-pbo-final.menu.Kategori  AS katergori
from mycashier-pbo-final.menu
where mycashier-pbo-final.menu.Kategori = 'Snack';

create
definer = root@localhost procedure kurangi_detail_pesanan(IN p_id_menu int, IN p_jumlah int)
BEGIN
    DECLARE v_harga DECIMAL(10,2);

SELECT harga INTO v_harga FROM menu WHERE id_menu = p_id_menu;

-- Check if the item is already in the order
IF EXISTS (SELECT * FROM detail_pesanan WHERE id_menu = p_id_menu AND Status = 'Diproses') THEN
        -- If yes, update the quantity
UPDATE detail_pesanan
SET jumlah = CASE WHEN (jumlah - p_jumlah) > 0 THEN (jumlah - p_jumlah) ELSE 0 END,
    harga_total = harga_total - v_harga * p_jumlah
WHERE id_menu = p_id_menu AND Status = 'Diproses';

-- Check if the quantity becomes zero after subtraction
IF (SELECT jumlah FROM detail_pesanan WHERE id_menu = p_id_menu AND Status = 'Diproses') = 0 THEN
-- If yes, delete the record
DELETE FROM detail_pesanan
WHERE id_menu = p_id_menu AND Status = 'Diproses';
END IF;
END IF;

end;

create
definer = root@localhost procedure proses_pembayaran()
BEGIN
    DECLARE v_total_pembayaran DECIMAL(10,2);
    DECLARE v_new_id_pembayaran INT;  -- Menyimpan ID pembayaran yang baru di-generate

    -- Menghitung total pembayaran dari detail pesanan yang berstatus 'Diproses'
SELECT COALESCE(SUM(harga_total), 0) INTO v_total_pembayaran
FROM detail_pesanan
WHERE Status = 'Diproses';

-- Memasukkan data pembayaran ke tabel pembayaran
INSERT INTO pembayaran (id_pesanan, total_pembayaran, tanggal_pembayaran)
VALUES (NULL, v_total_pembayaran, CURRENT_TIMESTAMP());

-- Menyimpan ID pembayaran yang baru di-generate
SET v_new_id_pembayaran = LAST_INSERT_ID();

    -- Mengubah status detail pesanan yang berstatus 'Diproses' menjadi 'Selesai'
UPDATE detail_pesanan
SET Status = 'Selesai', id_pembayaran = v_new_id_pembayaran
WHERE Status = 'Diproses';

END;

create
definer = root@localhost procedure tambahQuantity_detail_pesanan(IN p_id_menu int, IN p_jumlah int)
BEGIN
    DECLARE v_harga DECIMAL(10,2);
    DECLARE v_status VARCHAR(12);

SELECT harga INTO v_harga FROM menu WHERE id_menu = p_id_menu;
SET v_status = 'Diproses';

    -- Check item true or false
    IF EXISTS (SELECT * FROM detail_pesanan WHERE id_menu = p_id_menu AND Status = 'Diproses') THEN
        -- If yes, update the quantity
UPDATE detail_pesanan
SET jumlah = jumlah + p_jumlah,
    harga_total = harga_total + v_harga * p_jumlah
WHERE id_menu = p_id_menu AND Status = 'Diproses';
ELSE
        -- Kondisi false
        INSERT INTO detail_pesanan (id_menu, jumlah, harga_total, Status)
        VALUES (p_id_menu, CASE WHEN p_jumlah > 0 THEN p_jumlah ELSE 0 END, v_harga * p_jumlah, v_status);
END IF;
END;

create
definer = root@localhost procedure tambah_detail_pesanan(IN p_id_menu int, IN p_jumlah int)
BEGIN
    DECLARE v_harga DECIMAL(10,2);
    DECLARE v_status VARCHAR(12);

SELECT harga INTO v_harga FROM menu WHERE id_menu = p_id_menu;
SET v_status = 'Diproses';
INSERT INTO detail_pesanan (id_menu, jumlah, harga_total, Status)
VALUES (p_id_menu, p_jumlah, v_harga * p_jumlah, v_status);
END;