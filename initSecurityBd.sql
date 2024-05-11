--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2024-05-12 00:19:50

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
-- TOC entry 215 (class 1259 OID 16539)
-- Name: user_credential; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_credential (
                                        id integer NOT NULL,
                                        name character varying NOT NULL,
                                        password character varying NOT NULL
);


ALTER TABLE public.user_credential OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16538)
-- Name: user_credential_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.user_credential ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_credential_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 3318 (class 0 OID 16539)
-- Dependencies: 215
-- Data for Name: user_credential; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_credential (id, name, password) FROM stdin;
1	abonent	$2a$10$MsoqBTb1gqKlmarAL3lhBO8KCHkHAuaF.ZBTGHIrDT9d8D7oIwp3S
2	manager	$2a$10$uNOnVwePjPY4ZDQcSOw4OesWAFXfbOgeTbDFk1DKM70A4ML5sjz3y
3	admin	$2a$10$CmuqBUYJu1PRsiDxssK2y.G1Wl.VEteqrLjSIJeabeZTwkhEGAjnG
\.


--
-- TOC entry 3324 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_credential_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_credential_id_seq', 3, true);


--
-- TOC entry 3174 (class 2606 OID 16545)
-- Name: user_credential user_credential_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_credential
    ADD CONSTRAINT user_credential_pkey PRIMARY KEY (id);


-- Completed on 2024-05-12 00:19:51

--
-- PostgreSQL database dump complete
--

