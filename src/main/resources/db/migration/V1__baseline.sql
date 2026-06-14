--
-- PostgreSQL database dump
--

-- Dumped from database version 17.7 (Ubuntu 17.7-0ubuntu0.25.04.1)
-- Dumped by pg_dump version 17.7 (Ubuntu 17.7-0ubuntu0.25.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: contact_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.contact_type AS ENUM (
    'EMAIL',
    'PHONE',
    'MESSENGER',
    'OTHER'
);


ALTER TYPE public.contact_type OWNER TO postgres;

--
-- Name: gender; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.gender AS ENUM (
    'MALE',
    'FEMALE'
);


ALTER TYPE public.gender OWNER TO postgres;

--
-- Name: invitation_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.invitation_status AS ENUM (
    'PENDING',
    'APPROVED',
    'REJECTED',
    'CANCELLED'
);


ALTER TYPE public.invitation_status OWNER TO postgres;

--
-- Name: message_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.message_type AS ENUM (
    'USER',
    'ASSISTANT',
    'DOCTOR_SUGGESTIONS',
    'INVITATION'
);


ALTER TYPE public.message_type OWNER TO postgres;

--
-- Name: urgency_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.urgency_status AS ENUM (
    'SAFE',
    'CONCERNING',
    'CRITICAL'
);


ALTER TYPE public.urgency_status OWNER TO postgres;

--
-- Name: user_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.user_role AS ENUM (
    'PATIENT',
    'DOCTOR'
);


ALTER TYPE public.user_role OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: assistant_message_payload; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assistant_message_payload (
    message_id integer NOT NULL,
    possible_causes text[] DEFAULT '{}'::text[],
    urgency public.urgency_status,
    recommendations text[] DEFAULT '{}'::text[],
    doctors text[] DEFAULT '{}'::text[]
);


ALTER TABLE public.assistant_message_payload OWNER TO postgres;

--
-- Name: chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chats (
    id integer NOT NULL,
    user_id integer NOT NULL,
    title text NOT NULL,
    context text,
    last_message_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT now()
);


ALTER TABLE public.chats OWNER TO postgres;

--
-- Name: chats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.chats ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: doctor_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor_profile (
    user_id integer NOT NULL,
    address text,
    education text,
    description text,
    practice_start_date date,
    CONSTRAINT doctor_profile_practice_start_date_check CHECK ((practice_start_date <= CURRENT_DATE))
);


ALTER TABLE public.doctor_profile OWNER TO postgres;

--
-- Name: doctor_suggestions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor_suggestions (
    doctor_id integer NOT NULL,
    message_id integer NOT NULL
);


ALTER TABLE public.doctor_suggestions OWNER TO postgres;

--
-- Name: doctors_specializations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctors_specializations (
    doctor_id integer NOT NULL,
    specialization_id integer NOT NULL
);


ALTER TABLE public.doctors_specializations OWNER TO postgres;

--
-- Name: invitations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invitations (
    id integer NOT NULL,
    patient_id integer NOT NULL,
    doctor_id integer NOT NULL,
    content text,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    status public.invitation_status DEFAULT 'PENDING'::public.invitation_status NOT NULL,
    responded_at timestamp with time zone,
    message_id integer
);


ALTER TABLE public.invitations OWNER TO postgres;

--
-- Name: invitations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.invitations ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.invitations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id integer NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    chat_id integer NOT NULL,
    type public.message_type NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.messages ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: notifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifications (
    id integer NOT NULL,
    receiver_id integer NOT NULL,
    invitation_id integer NOT NULL,
    is_read boolean,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    historic_status public.invitation_status NOT NULL
);


ALTER TABLE public.notifications OWNER TO postgres;

--
-- Name: notifications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.notifications ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.notifications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: patient_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_profile (
    user_id integer NOT NULL,
    address text,
    birthdate date,
    gender public.gender,
    medical_history text,
    CONSTRAINT patient_profile_birthdate_check CHECK ((birthdate <= CURRENT_DATE))
);


ALTER TABLE public.patient_profile OWNER TO postgres;

--
-- Name: refresh_tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refresh_tokens (
    token text NOT NULL,
    expires_at timestamp with time zone DEFAULT (now() + '7 days'::interval) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.refresh_tokens OWNER TO postgres;

--
-- Name: specializations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.specializations (
    id integer NOT NULL,
    name text
);


ALTER TABLE public.specializations OWNER TO postgres;

--
-- Name: specializations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.specializations ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.specializations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_contacts (
    id integer NOT NULL,
    user_id integer NOT NULL,
    type public.contact_type NOT NULL,
    value text NOT NULL,
    is_primary boolean DEFAULT false NOT NULL
);


ALTER TABLE public.user_contacts OWNER TO postgres;

--
-- Name: user_contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.user_contacts ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_message_payload; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_message_payload (
    message_id integer NOT NULL,
    content text NOT NULL
);


ALTER TABLE public.user_message_payload OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    role public.user_role DEFAULT 'PATIENT'::public.user_role NOT NULL,
    full_name text
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: assistant_message_payload assistant_message_payload_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistant_message_payload
    ADD CONSTRAINT assistant_message_payload_pkey PRIMARY KEY (message_id);


--
-- Name: chats chats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (id);


--
-- Name: doctor_profile doctor_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_profile
    ADD CONSTRAINT doctor_profile_pkey PRIMARY KEY (user_id);


--
-- Name: doctors_specializations doctors_specializations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors_specializations
    ADD CONSTRAINT doctors_specializations_pkey PRIMARY KEY (doctor_id, specialization_id);


--
-- Name: invitations invitations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invitations
    ADD CONSTRAINT invitations_pkey PRIMARY KEY (id);


--
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- Name: patient_profile patient_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_profile
    ADD CONSTRAINT patient_profile_pkey PRIMARY KEY (user_id);


--
-- Name: refresh_tokens refresh_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pkey PRIMARY KEY (token);


--
-- Name: specializations specializations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specializations
    ADD CONSTRAINT specializations_pkey PRIMARY KEY (id);


--
-- Name: user_contacts user_contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_pkey PRIMARY KEY (id);


--
-- Name: user_contacts user_contacts_user_id_type_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_user_id_type_key UNIQUE (user_id, type);


--
-- Name: user_message_payload user_message_payload_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_message_payload
    ADD CONSTRAINT user_message_payload_pkey PRIMARY KEY (message_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: assistant_message_payload assistant_message_payload_message_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistant_message_payload
    ADD CONSTRAINT assistant_message_payload_message_id_fkey FOREIGN KEY (message_id) REFERENCES public.messages(id);


--
-- Name: chats chats_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: doctor_profile doctor_profile_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_profile
    ADD CONSTRAINT doctor_profile_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: doctor_suggestions doctor_suggestions_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_suggestions
    ADD CONSTRAINT doctor_suggestions_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);


--
-- Name: doctor_suggestions doctor_suggestions_message_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_suggestions
    ADD CONSTRAINT doctor_suggestions_message_id_fkey FOREIGN KEY (message_id) REFERENCES public.messages(id);


--
-- Name: doctors_specializations doctors_specializations_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors_specializations
    ADD CONSTRAINT doctors_specializations_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor_profile(user_id);


--
-- Name: doctors_specializations doctors_specializations_specialization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors_specializations
    ADD CONSTRAINT doctors_specializations_specialization_id_fkey FOREIGN KEY (specialization_id) REFERENCES public.specializations(id);


--
-- Name: invitations invitations_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invitations
    ADD CONSTRAINT invitations_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);


--
-- Name: invitations invitations_message_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invitations
    ADD CONSTRAINT invitations_message_id_fkey FOREIGN KEY (message_id) REFERENCES public.messages(id) ON DELETE SET NULL;


--
-- Name: invitations invitations_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invitations
    ADD CONSTRAINT invitations_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.users(id);


--
-- Name: messages messages_chat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES public.chats(id);


--
-- Name: notifications notifications_invitation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_invitation_id_fkey FOREIGN KEY (invitation_id) REFERENCES public.invitations(id);


--
-- Name: notifications notifications_receiver_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_receiver_id_fkey FOREIGN KEY (receiver_id) REFERENCES public.users(id);


--
-- Name: patient_profile patient_profile_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_profile
    ADD CONSTRAINT patient_profile_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: refresh_tokens refresh_tokens_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_contacts user_contacts_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_message_payload user_message_payload_message_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_message_payload
    ADD CONSTRAINT user_message_payload_message_id_fkey FOREIGN KEY (message_id) REFERENCES public.messages(id);


--
-- PostgreSQL database dump complete
--

