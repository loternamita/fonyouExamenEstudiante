create database pruebatecnica

create table examen(
	id serial primary key,
	total_puntos integer not null
);

create table pregunta(
	id serial primary key,
	id_examen integer not null references examen(id),
	enunciado text not null,
	opcion_correcta char not null,
	puntaje integer not null
);

create table opcion(
	id serial primary key,
	id_pregunta integer not null references pregunta(id),
	opcion char not null,
	texto text not null
);

create table estudiante(
	id serial primary key,
	nombre varchar(100) not null,
	edad integer not null,
	ciudad varchar(100) not null,
	zona_horaria varchar(100) not null
);

create table examen_estudiante(
	id_examen integer not null references examen(id),
	id_estudiante integer not null references estudiante(id),
	fecha_examen_zona_horaria TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	calificacion integer,
	estado varchar(100) not null,
	primary key (id_examen, id_estudiante)
);

create table respuesta_estudiante( 
	id_estudiante integer not null references estudiante(id),
	id_pregunta integer not null references pregunta(id),
	opcion_elegida char not null,
	primary key (id_estudiante, id_pregunta)
);





