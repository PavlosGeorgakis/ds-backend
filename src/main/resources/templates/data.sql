-- Insert users if not exists
INSERT INTO users (first_name, last_name, vat, username, email, password)
SELECT 'Johnny', 'Depp', '075369553', 'johnny', 'johnny@hua.gr', '$2a$10$1rNHS..xCJv6dyz8qWNNs.ors.8LZXNZExLScSw1VfaBEtdW/GFFa'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'johnny');

INSERT INTO users (first_name, last_name, vat, username, email, password)
SELECT 'Leonardo', 'DiCaprio', '089218786', 'leo', 'leo@hua.gr', '$2a$10$m7kKXcFLiehVJrh0yzK4E.cYHCNElkaaUBEPmroG9iR4YaT.6rXx2'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'leo');

INSERT INTO users (first_name, last_name, vat, username, email, password)
SELECT 'Brad', 'Pitt', '049211582', 'achilles', 'achilles@hua.gr', '$2a$10$NrxQDadEACPES/fzUrA5Gupv6MCr/p8N7pHgoiOxnxoCOcZFeYhfu'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'achilles');

-- Insert cooperatives if not exists
INSERT INTO cooperatives (name, vat, status, notes, employee_id)
SELECT 'Heroes', '061248142', 'processing', null, null
WHERE NOT EXISTS (SELECT 1 FROM cooperatives WHERE name = 'Heroes');

INSERT INTO cooperatives (name, vat, status, notes, employee_id)
SELECT 'Villains', '007601674', 'approved', 'well done', 2
WHERE NOT EXISTS (SELECT 1 FROM cooperatives WHERE name = 'Villains');

INSERT INTO cooperatives (name, vat, status, notes, employee_id)
SELECT 'Anti-Villains', '029273149', 'rejected', 'invalid vat', 2
WHERE NOT EXISTS (SELECT 1 FROM cooperatives WHERE name = 'Anti-Villains');

-- Insert products if not exists
INSERT INTO products (name, category, price)
SELECT 'Feta', 'Cheese', 2.99
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Feta');

INSERT INTO products (name, category, price)
SELECT 'Apple', 'Fruit', 0.99
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Apple');

INSERT INTO products (name, category, price)
SELECT 'Orange', 'Fruit', 0.49
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Orange');

-- Insert cultivation locations if not exists
INSERT INTO cultivation_locations (address, area, zip_code)
SELECT 'Thiseos 70', 'Kallithea', '176 76'
WHERE NOT EXISTS (SELECT 1 FROM cultivation_locations WHERE address = 'Thiseos 70');

INSERT INTO cultivation_locations (address, area, zip_code)
SELECT 'Omirou 9', 'Tavros', '177 78'
WHERE NOT EXISTS (SELECT 1 FROM cultivation_locations WHERE address = 'Omirou 9');

INSERT INTO cultivation_locations (address, area, zip_code)
SELECT 'Agiou Spiridonos 28', 'Egaleo', '122 43'
WHERE NOT EXISTS (SELECT 1 FROM cultivation_locations WHERE address = 'Agiou Spiridonos 28');

-- Associate farmers with their cooperatives if not exists
INSERT INTO cooperative_farmers (cooperative_id, farmer_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM cooperative_farmers WHERE cooperative_id = 1 AND farmer_id = 1);

INSERT INTO cooperative_farmers (cooperative_id, farmer_id)
SELECT 2, 1
WHERE NOT EXISTS (SELECT 1 FROM cooperative_farmers WHERE cooperative_id = 2 AND farmer_id = 1);

INSERT INTO cooperative_farmers (cooperative_id, farmer_id)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 1 FROM cooperative_farmers WHERE cooperative_id = 3 AND farmer_id = 3);

-- Associate products with their cooperatives if not exists
INSERT INTO cooperative_products (cooperative_id, product_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM cooperative_products WHERE cooperative_id = 1 AND product_id = 1);

INSERT INTO cooperative_products (cooperative_id, product_id)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 1 FROM cooperative_products WHERE cooperative_id = 2 AND product_id = 2);

INSERT INTO cooperative_products (cooperative_id, product_id)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 1 FROM cooperative_products WHERE cooperative_id = 3 AND product_id = 3);

-- Associate cultivation locations with their cooperatives if not exists
INSERT INTO cooperative_locations (cooperative_id, location_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM cooperative_locations WHERE cooperative_id = 1 AND location_id = 1);

INSERT INTO cooperative_locations (cooperative_id, location_id)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 1 FROM cooperative_locations WHERE cooperative_id = 2 AND location_id = 2);

INSERT INTO cooperative_locations (cooperative_id, location_id)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 1 FROM cooperative_locations WHERE cooperative_id = 3 AND location_id = 3);

-- Add roles to users if not exists
INSERT INTO user_roles (user_id, role_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 1 AND role_id = 1);

INSERT INTO user_roles (user_id, role_id)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 2 AND role_id = 2);

INSERT INTO user_roles (user_id, role_id)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = 3 AND role_id = 3);
