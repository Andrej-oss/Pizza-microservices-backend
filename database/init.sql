CREATE ROLE pizza_service WITH LOGIN PASSWORD 'pzz-srv';
-- CREATE ROLE service_prices WITH LOGIN PASSWORD 'srv-prcs';
-- CREATE ROLE service_users WITH LOGIN PASSWORD 'srv-usrs';

CREATE SCHEMA pizza AUTHORIZATION pizza_service;
-- CREATE SCHEMA service_prices AUTHORIZATION service_prices;
-- CREATE SCHEMA service_users AUTHORIZATION service_users;

