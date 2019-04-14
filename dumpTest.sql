
-- Database: test 
DROP DATABASE IF EXISTS test;
CREATE DATABASE test
WITH OWNER = postgres
ENCODING = 'UTF8'
LC_COLLATE = 'French_France.1252' 
LC_CTYPE = 'French_France.1252' 
TABLESPACE = pg_default 
CONNECTION LIMIT = -1;
\c test;

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO pony;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_news; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE  public.t_news (
    id bigint NOT NULL,
    content text NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    modification_date timestamp without time zone,
    slug character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.t_news OWNER TO pony;

--
-- Name: t_news_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_news_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_news_user_id_seq OWNER TO pony;

--
-- Name: t_news_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_news_user_id_seq OWNED BY public.t_news.user_id;


--
-- Name: t_roles; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_roles (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.t_roles OWNER TO pony;

--
-- Name: t_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_roles_id_seq OWNER TO pony;

--
-- Name: t_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_roles_id_seq OWNED BY public.t_roles.id;


--
-- Name: t_tokens; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_tokens (
    id integer NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    type integer NOT NULL,
    value uuid NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.t_tokens OWNER TO pony;

--
-- Name: t_tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_tokens_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_tokens_id_seq OWNER TO pony;

--
-- Name: t_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_tokens_id_seq OWNED BY public.t_tokens.id;


--
-- Name: t_tokens_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_tokens_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_tokens_user_id_seq OWNER TO pony;

--
-- Name: t_tokens_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_tokens_user_id_seq OWNED BY public.t_tokens.user_id;


--
-- Name: t_user_currencies; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_user_currencies (
    id integer NOT NULL,
    diamonds bigint NOT NULL,
    golds bigint NOT NULL,
    woods bigint NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.t_user_currencies OWNER TO pony;

--
-- Name: t_user_currencies_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_user_currencies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_currencies_id_seq OWNER TO pony;

--
-- Name: t_user_currencies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_user_currencies_id_seq OWNED BY public.t_user_currencies.id;


--
-- Name: t_user_currencies_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_user_currencies_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_currencies_user_id_seq OWNER TO pony;

--
-- Name: t_user_currencies_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_user_currencies_user_id_seq OWNED BY public.t_user_currencies.user_id;


--
-- Name: t_user_roles; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.t_user_roles OWNER TO pony;

--
-- Name: t_user_roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_user_roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_roles_role_id_seq OWNER TO pony;

--
-- Name: t_user_roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_user_roles_role_id_seq OWNED BY public.t_user_roles.role_id;


--
-- Name: t_user_roles_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_user_roles_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_roles_user_id_seq OWNER TO pony;

--
-- Name: t_user_roles_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_user_roles_user_id_seq OWNED BY public.t_user_roles.user_id;


--
-- Name: t_users; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_users (
    id integer NOT NULL,
    first_name character varying(255),
    is_active boolean NOT NULL,
    is_banned boolean NOT NULL,
    is_suspended boolean NOT NULL,
    last_name character varying(255),
    mail character varying(255) NOT NULL,
    normalized_mail character varying(255) NOT NULL,
    normalized_user_name character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    phone character varying(255),
    user_name character varying(255) NOT NULL
);


ALTER TABLE public.t_users OWNER TO pony;

--
-- Name: t_users_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_users_id_seq OWNER TO pony;

--
-- Name: t_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_users_id_seq OWNED BY public.t_users.id;


--
-- Name: t_users_tokens; Type: TABLE; Schema: public; Owner: pony
--

CREATE TABLE public.t_users_tokens (
    user_id integer NOT NULL,
    tokens_id integer NOT NULL
);


ALTER TABLE public.t_users_tokens OWNER TO pony;

--
-- Name: t_users_tokens_tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_users_tokens_tokens_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_users_tokens_tokens_id_seq OWNER TO pony;

--
-- Name: t_users_tokens_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_users_tokens_tokens_id_seq OWNED BY public.t_users_tokens.tokens_id;


--
-- Name: t_users_tokens_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pony
--

CREATE SEQUENCE public.t_users_tokens_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_users_tokens_user_id_seq OWNER TO pony;

--
-- Name: t_users_tokens_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pony
--

ALTER SEQUENCE public.t_users_tokens_user_id_seq OWNED BY public.t_users_tokens.user_id;


--
-- Name: t_news user_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_news ALTER COLUMN user_id SET DEFAULT nextval('public.t_news_user_id_seq'::regclass);


--
-- Name: t_roles id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_roles ALTER COLUMN id SET DEFAULT nextval('public.t_roles_id_seq'::regclass);


--
-- Name: t_tokens id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_tokens ALTER COLUMN id SET DEFAULT nextval('public.t_tokens_id_seq'::regclass);


--
-- Name: t_tokens user_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_tokens ALTER COLUMN user_id SET DEFAULT nextval('public.t_tokens_user_id_seq'::regclass);


--
-- Name: t_user_currencies id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_currencies ALTER COLUMN id SET DEFAULT nextval('public.t_user_currencies_id_seq'::regclass);


--
-- Name: t_user_currencies user_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_currencies ALTER COLUMN user_id SET DEFAULT nextval('public.t_user_currencies_user_id_seq'::regclass);


--
-- Name: t_user_roles user_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_roles ALTER COLUMN user_id SET DEFAULT nextval('public.t_user_roles_user_id_seq'::regclass);


--
-- Name: t_user_roles role_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_roles ALTER COLUMN role_id SET DEFAULT nextval('public.t_user_roles_role_id_seq'::regclass);


--
-- Name: t_users id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users ALTER COLUMN id SET DEFAULT nextval('public.t_users_id_seq'::regclass);


--
-- Name: t_users_tokens user_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users_tokens ALTER COLUMN user_id SET DEFAULT nextval('public.t_users_tokens_user_id_seq'::regclass);


--
-- Name: t_users_tokens tokens_id; Type: DEFAULT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users_tokens ALTER COLUMN tokens_id SET DEFAULT nextval('public.t_users_tokens_tokens_id_seq'::regclass);


--
-- Data for Name: t_news; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_news (id, content, creation_date, modification_date, slug, title, user_id) FROM stdin;
8	<p>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</p>	2018-07-18 12:18:12.152	\N	title_1	Title	1
9	<p>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</p>	2018-07-18 12:19:38.817	\N	title_2	Title	1
10	<p>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</p>	2018-07-18 12:47:02.057	\N	title_3	Title	1
11	<p>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</p>	2018-07-18 12:48:14.625	\N	title_4	Title	1
12	<p>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</p>	2018-07-18 12:49:19.066	\N	title_5	Title	1
1	<p><strong>A robot who has developed sentience, and is the only robot of his kind shown to be still functioning on Earth.</strong></p>	2018-07-17 16:33:36.847	\N	title	Title	1
\.


--
-- Data for Name: t_roles; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_roles (id, name) FROM stdin;
3	ADMIN
4	SUPERADMIN
5	MODERATOR
6	USER
\.


--
-- Data for Name: t_tokens; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_tokens (id, creation_date, type, value, user_id) FROM stdin;
2	2018-07-17 16:29:03.897	0	7b7df0f4-3017-4318-b457-85cd6e0a8326	1
\.


--
-- Data for Name: t_user_currencies; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_user_currencies (id, diamonds, golds, woods, user_id) FROM stdin;
\.


--
-- Data for Name: t_user_roles; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_user_roles (user_id, role_id) FROM stdin;
1	4
\.


--
-- Data for Name: t_users; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_users (id, first_name, is_active, is_banned, is_suspended, last_name, mail, normalized_mail, normalized_user_name, password_hash, phone, user_name) FROM stdin;
1	\N	f	f	f	\N	guingrich.kevin@gmail.com	GUINGRICH.KEVIN@GMAIL.COM	KEVIN	$2a$10$Fd5OGNaqsv8WCWOYmloEAuDBeF62eeWZ3STpyjn17qPe7Pc/85dlu	\N	kevin
\.


--
-- Data for Name: t_users_tokens; Type: TABLE DATA; Schema: public; Owner: pony
--

COPY public.t_users_tokens (user_id, tokens_id) FROM stdin;
1	2
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.hibernate_sequence', 56, true);


--
-- Name: t_news_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_news_user_id_seq', 1, false);


--
-- Name: t_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_roles_id_seq', 1, false);


--
-- Name: t_tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_tokens_id_seq', 1, false);


--
-- Name: t_tokens_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_tokens_user_id_seq', 1, true);


--
-- Name: t_user_currencies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_user_currencies_id_seq', 1, false);


--
-- Name: t_user_currencies_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_user_currencies_user_id_seq', 1, false);


--
-- Name: t_user_roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_user_roles_role_id_seq', 1, false);


--
-- Name: t_user_roles_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_user_roles_user_id_seq', 1, false);


--
-- Name: t_users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_users_id_seq', 1, false);


--
-- Name: t_users_tokens_tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_users_tokens_tokens_id_seq', 1, false);


--
-- Name: t_users_tokens_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pony
--

SELECT pg_catalog.setval('public.t_users_tokens_user_id_seq', 1, false);


--
-- Name: t_news t_news_pkey; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_news
    ADD CONSTRAINT t_news_pkey PRIMARY KEY (id);


--
-- Name: t_roles t_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_roles
    ADD CONSTRAINT t_roles_pkey PRIMARY KEY (id);


--
-- Name: t_tokens t_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_tokens
    ADD CONSTRAINT t_tokens_pkey PRIMARY KEY (id);


--
-- Name: t_user_currencies t_user_currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_currencies
    ADD CONSTRAINT t_user_currencies_pkey PRIMARY KEY (id);


--
-- Name: t_users t_users_pkey; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users
    ADD CONSTRAINT t_users_pkey PRIMARY KEY (id);


--
-- Name: t_users uk_5ebsdi5h725fqsiarxvgl1j43; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users
    ADD CONSTRAINT uk_5ebsdi5h725fqsiarxvgl1j43 UNIQUE (user_name);


--
-- Name: t_users uk_5jaqo023k1nx2xjrp3th508ox; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users
    ADD CONSTRAINT uk_5jaqo023k1nx2xjrp3th508ox UNIQUE (normalized_user_name);


--
-- Name: t_users uk_a2m5rwh7dl4y2ndh3ty86r3dg; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users
    ADD CONSTRAINT uk_a2m5rwh7dl4y2ndh3ty86r3dg UNIQUE (normalized_mail);


--
-- Name: t_users_tokens uk_d2i9ro851l1g6tptfxtedhvad; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users_tokens
    ADD CONSTRAINT uk_d2i9ro851l1g6tptfxtedhvad UNIQUE (tokens_id);


--
-- Name: t_users uk_e5x1g6594tkf0qhuago01630x; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users
    ADD CONSTRAINT uk_e5x1g6594tkf0qhuago01630x UNIQUE (mail);


--
-- Name: t_roles uk_go0rigpiaq82tlqv1kqnwx2ya; Type: CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_roles
    ADD CONSTRAINT uk_go0rigpiaq82tlqv1kqnwx2ya UNIQUE (name);


--
-- Name: t_users_tokens fk1uf6mlu0gxmgq7quh2rban63v; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users_tokens
    ADD CONSTRAINT fk1uf6mlu0gxmgq7quh2rban63v FOREIGN KEY (tokens_id) REFERENCES public.t_tokens(id);


--
-- Name: t_tokens fk4yapf70j8ywq6xye5ypmr4a9g; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_tokens
    ADD CONSTRAINT fk4yapf70j8ywq6xye5ypmr4a9g FOREIGN KEY (user_id) REFERENCES public.t_users(id);


--
-- Name: t_news fk5ktaqk0aoifat27umhyk6l2ci; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_news
    ADD CONSTRAINT fk5ktaqk0aoifat27umhyk6l2ci FOREIGN KEY (user_id) REFERENCES public.t_users(id);


--
-- Name: t_users_tokens fkcl6e1xvbl0wg0aai7jlapusp1; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_users_tokens
    ADD CONSTRAINT fkcl6e1xvbl0wg0aai7jlapusp1 FOREIGN KEY (user_id) REFERENCES public.t_users(id);


--
-- Name: t_user_roles fkh6f43s5qsc7a5rvn1kgvb2tyw; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_roles
    ADD CONSTRAINT fkh6f43s5qsc7a5rvn1kgvb2tyw FOREIGN KEY (user_id) REFERENCES public.t_users(id);


--
-- Name: t_user_currencies fkqxhternu1dco4i9yls9qwpgwa; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_currencies
    ADD CONSTRAINT fkqxhternu1dco4i9yls9qwpgwa FOREIGN KEY (user_id) REFERENCES public.t_users(id);


--
-- Name: t_user_roles fks6chhvbxjrqhhlb3grun2691k; Type: FK CONSTRAINT; Schema: public; Owner: pony
--

ALTER TABLE ONLY public.t_user_roles
    ADD CONSTRAINT fks6chhvbxjrqhhlb3grun2691k FOREIGN KEY (role_id) REFERENCES public.t_roles(id);


--
-- PostgreSQL database dump complete
--

