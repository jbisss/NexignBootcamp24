--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2024-05-10 13:55:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16418)
-- Name: abonents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.abonents (
                                 abonent_id integer NOT NULL,
                                 phone_number character varying(15) NOT NULL,
                                 tariff_id integer NOT NULL,
                                 connection_date date NOT NULL,
                                 balance numeric DEFAULT 0 NOT NULL
);


ALTER TABLE public.abonents OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16536)
-- Name: abonents_abonent_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.abonents ALTER COLUMN abonent_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.abonents_abonent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3318 (class 0 OID 16418)
-- Dependencies: 214
-- Data for Name: abonents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.abonents (abonent_id, phone_number, tariff_id, connection_date, balance) FROM stdin;
24	79115763555	11	2024-01-01	1000.5
25	79215763555	11	2024-01-01	1000.5
26	79213213555	11	2024-01-01	1000.5
27	79215233555	11	2024-01-01	1000.5
28	79215553555	11	2024-01-01	1000.5
29	79212131155	11	2024-01-01	1000.5
30	79211233555	11	2024-01-01	1000.5
31	79212131255	11	2024-01-01	1000.5
32	79212133555	11	2024-01-01	1000.5
33	79215433555	11	2024-01-01	1000.5
\.


--
-- TOC entry 3325 (class 0 OID 0)
-- Dependencies: 215
-- Name: abonents_abonent_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.abonents_abonent_id_seq', 33, true);


--
-- TOC entry 3175 (class 2606 OID 16423)
-- Name: abonents abonents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abonents
    ADD CONSTRAINT abonents_pkey PRIMARY KEY (abonent_id);


-- Completed on 2024-05-10 13:55:10

--
-- PostgreSQL database dump complete
--

