--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2024-05-10 13:53:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = ''UTF8'';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config(''search_path'', '''', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '''';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16517)
-- Name: abonent_debts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.abonent_debts
(
    debt_id              integer               NOT NULL,
    abonent_phone_number character varying(25) NOT NULL,
    debt_date            date                  NOT NULL,
    debt_amount          numeric               NOT NULL
);


ALTER TABLE public.abonent_debts
    OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16532)
-- Name: abonent_debts_debt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.abonent_debts
    ALTER COLUMN debt_id ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME public.abonent_debts_debt_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );


--
-- TOC entry 218 (class 1259 OID 16524)
-- Name: remains; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.remains
(
    remains_id           integer               NOT NULL,
    abonent_phone_number character varying(25) NOT NULL,
    service_in_tariff_id integer               NOT NULL,
    remains              integer               NOT NULL
);


ALTER TABLE public.remains
    OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16533)
-- Name: remains_remains_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.remains
    ALTER COLUMN remains_id ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME public.remains_remains_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );


--
-- TOC entry 216 (class 1259 OID 16434)
-- Name: service_in_tariff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service_in_tariff
(
    link_id    integer NOT NULL,
    tariff_id  integer NOT NULL,
    service_id integer NOT NULL,
    cost       numeric,
    count      integer
);


ALTER TABLE public.service_in_tariff
    OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16429)
-- Name: services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.services
(
    service_id          integer               NOT NULL,
    service_name        character varying(60) NOT NULL,
    service_description character varying(255),
    measure_unit        character varying(25)
);


ALTER TABLE public.services
    OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16424)
-- Name: tariffs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tariffs
(
    tariff_id          integer               NOT NULL,
    tariff_name        character varying(60) NOT NULL,
    tariff_description character varying(255),
    abonent_payment    numeric,
    tariff_extra       integer
);


ALTER TABLE public.tariffs
    OWNER TO postgres;

--
-- TOC entry 3348 (class 0 OID 16517)
-- Dependencies: 217
-- Data for Name: abonent_debts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.abonent_debts (debt_id, abonent_phone_number, debt_date, debt_amount) FROM stdin;
\.


--
-- TOC entry 3349 (class 0 OID 16524)
-- Dependencies: 218
-- Data for Name: remains; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.remains (remains_id, abonent_phone_number, service_in_tariff_id, remains) FROM stdin;
15223	79215763555	4	50
15224	79215763555	5	100
15225	79215433555	4	50
15226	79215433555	5	100
\.


--
-- TOC entry 3347 (class 0 OID 16434)
-- Dependencies: 216
-- Data for Name: service_in_tariff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.service_in_tariff (link_id, tariff_id, service_id, cost, count) FROM stdin;
1	11	2	1.5	\N
2	11	4	2.5	\N
3	11	5	0	\N
4	12	5	\N	50
5	12	6	\N	100
\.


--
-- TOC entry 3346 (class 0 OID 16429)
-- Dependencies: 215
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.services (service_id, service_name, service_description, measure_unit) FROM stdin;
1	IN_ROMASHKA	Входящие телефонные звонки абонентам Ромашки	minutes
2	OUT_ROMASHKA	Исходящие телефонные звонки абонентам Ромашки	minutes
3	IN_OTHER	Исходящие телефонные звонки абонентам других операторов	minutes
4	OUT_OTHER	Исходящие телефонные звонки абонентам других операторов	minutes
5	IN_ANY	Исходящие телефонные звонки любым абонентам	minutes
6	OUT_ANY	Исходящие телефонные звонки любым абонентам	minutes
\.


--
-- TOC entry 3345 (class 0 OID 16424)
-- Dependencies: 214
-- Data for Name: tariffs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tariffs (tariff_id, tariff_name, tariff_description, abonent_payment, tariff_extra) FROM stdin;
11	Классика	Классический, дефолтненький тариф	\N	\N
12	Помесячный	Помесячный, так сказать ЕЖЕмесячный тарифчик	400	11
\.


--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 219
-- Name: abonent_debts_debt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval(''public.abonent_debts_debt_id_seq'', 1684, true);


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 220
-- Name: remains_remains_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval(''public.remains_remains_id_seq'', 15226, true);


--
-- TOC entry 3197 (class 2606 OID 16523)
-- Name: abonent_debts abonent_debts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abonent_debts
    ADD CONSTRAINT abonent_debts_pkey PRIMARY KEY (debt_id);


--
-- TOC entry 3199 (class 2606 OID 16535)
-- Name: remains remains_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remains
    ADD CONSTRAINT remains_pkey PRIMARY KEY (remains_id);


--
-- TOC entry 3195 (class 2606 OID 16459)
-- Name: service_in_tariff service_in_tariff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service_in_tariff
    ADD CONSTRAINT service_in_tariff_pkey PRIMARY KEY (link_id);


--
-- TOC entry 3193 (class 2606 OID 16433)
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (service_id);


--
-- TOC entry 3191 (class 2606 OID 16428)
-- Name: tariffs tariffs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tariffs
    ADD CONSTRAINT tariffs_pkey PRIMARY KEY (tariff_id);


--
-- TOC entry 3200 (class 2606 OID 16442)
-- Name: service_in_tariff fk_service_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service_in_tariff
    ADD CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES public.services (service_id);


--
-- TOC entry 3202 (class 2606 OID 16527)
-- Name: remains fk_service_in_tariff_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remains
    ADD CONSTRAINT fk_service_in_tariff_id FOREIGN KEY (service_in_tariff_id) REFERENCES public.service_in_tariff (link_id);


--
-- TOC entry 3201 (class 2606 OID 16437)
-- Name: service_in_tariff fk_tariff_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service_in_tariff
    ADD CONSTRAINT fk_tariff_id FOREIGN KEY (tariff_id) REFERENCES public.tariffs (tariff_id);


-- Completed on 2024-05-10 13:53:30

--
-- PostgreSQL database dump complete
--

