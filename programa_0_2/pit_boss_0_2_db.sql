CREATE TABLE `asignaciones` (
  `ID_Asignacion` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Mesa` int(11) DEFAULT NULL,
  `ID_Trabajador` int(11) DEFAULT NULL,
  `ID_Turno` int(11) DEFAULT NULL,
  `id_dia` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Asignacion`),
  KEY `ID_Mesa` (`ID_Mesa`),
  KEY `ID_Trabajador` (`ID_Trabajador`),
  KEY `ID_Turno` (`ID_Turno`),
  KEY `id_dia` (`id_dia`),
  CONSTRAINT `asignaciones_ibfk_1` FOREIGN KEY (`ID_Mesa`) REFERENCES `mesas` (`ID_Mesa`),
  CONSTRAINT `asignaciones_ibfk_2` FOREIGN KEY (`ID_Trabajador`) REFERENCES `trabajadores` (`ID_Trabajador`),
  CONSTRAINT `asignaciones_ibfk_3` FOREIGN KEY (`ID_Turno`) REFERENCES `turnos` (`ID_Turno`),
  CONSTRAINT `asignaciones_ibfk_4` FOREIGN KEY (`id_dia`) REFERENCES `dias` (`id_dia`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `asignaciones_turnos_trabajadores` (
  `ID_Asignacion` int(11) NOT NULL,
  `ID_Turno` int(11) NOT NULL,
  `ID_Trabajador` int(11) NOT NULL,
  PRIMARY KEY (`ID_Asignacion`,`ID_Turno`,`ID_Trabajador`),
  KEY `ID_Turno` (`ID_Turno`),
  KEY `ID_Trabajador` (`ID_Trabajador`),
  CONSTRAINT `asignaciones_turnos_trabajadores_ibfk_1` FOREIGN KEY (`ID_Asignacion`) REFERENCES `asignaciones` (`ID_Asignacion`),
  CONSTRAINT `asignaciones_turnos_trabajadores_ibfk_2` FOREIGN KEY (`ID_Turno`) REFERENCES `turnos` (`ID_Turno`),
  CONSTRAINT `asignaciones_turnos_trabajadores_ibfk_3` FOREIGN KEY (`ID_Trabajador`) REFERENCES `trabajadores` (`ID_Trabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `descansos` (
  `ID_Descanso` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Trabajador` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Hora_Inicio` time DEFAULT NULL,
  `Hora_Fin` time DEFAULT NULL,
  PRIMARY KEY (`ID_Descanso`),
  KEY `ID_Trabajador` (`ID_Trabajador`),
  CONSTRAINT `descansos_ibfk_1` FOREIGN KEY (`ID_Trabajador`) REFERENCES `trabajadores` (`ID_Trabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `dias` (
  `id_dia` int(11) NOT NULL AUTO_INCREMENT,
  `dia` date DEFAULT NULL,
  PRIMARY KEY (`id_dia`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `mesas` (
  `ID_Mesa` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  `Estado` enum('Libre','Cerrada','Ocupada') DEFAULT 'Libre',
  PRIMARY KEY (`ID_Mesa`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `trabajadores` (
  `ID_Trabajador` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  `Cargo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_Trabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `turnos` (
  `ID_Turno` int(11) NOT NULL AUTO_INCREMENT,
  `Hora_Inicio` time DEFAULT NULL,
  `Hora_Fin` time DEFAULT NULL,
  PRIMARY KEY (`ID_Turno`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
