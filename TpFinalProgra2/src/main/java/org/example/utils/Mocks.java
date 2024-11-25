package org.example.utils;

import org.example.models.Address;
import org.example.models.Card;

public class Mocks {

    public static final String[] ALIAS = {   // 8,887,600 combinaciones distintas
            "AGUA", "AIRE", "AMIGO", "ANIMAL", "ARBOL", "BICICLETA", "BOLA", "CAJA", "CAMINO", "CANTO",
            "CIELO", "CUADERNO", "DIA", "DEDO", "DOLOR", "DORMITORIO", "DULCE", "EMOCION", "ENFERMEDAD", "ESTANTE",
            "ESTUDIO", "FABRICA", "FIESTA", "FILME", "FLOR", "FOTOGRAFIA", "GATO", "GENTE", "HOJA", "HORIZONTE",
            "HUESO", "IGLESIA", "IMPULSO", "ISLA", "JARDIN", "JUEGO", "JUNTA", "LLAVE", "LUNA", "MADRE",
            "MANO", "MAQUINA", "MAR", "MESA", "MENTE", "MUSICA", "NUBE", "OJO", "OPORTUNIDAD", "PAIS",
            "PARED", "PASO", "PELICULA", "PERSONA", "PIEDRA", "PLANTA", "PLATO", "PUEBLO", "RAFAGA", "RATA",
            "ROPA", "ROSTRO", "RUTA", "SALUD", "SOL", "SUENO", "SILLA", "TIERRA", "TOQUE", "TORNADO",
            "TRABAJO", "UNIDAD", "VALLE", "VENTANA", "VIENTO", "VIDEO", "VICTIMA", "VOZ", "AGUJERO", "AHORRO",
            "ALIMENTO", "ALUMNO", "AMOR", "ANALISIS", "ANTIGUEDAD", "APORTE", "APRENDIZAJE", "ARTICULO", "AUTO", "AVION",
            "BALON", "BANDA", "BANCO", "BASE", "BOTELLA", "BOTE", "CAMPO", "CANCION", "CAPITULO", "CARNE",
            "CARNET", "CIEGO", "CLIENTE", "COLEGIO", "CONCIERTO", "CONDICION", "CONO", "CORREDOR", "CORREO", "CREDITO",
            "CUIDADO", "CUOTA", "DECISION", "DERECHO", "DETALLE", "DIENTE", "DUDA", "EDUCACION", "ENLACE", "ENFERMO",
            "ESCUELA", "ESPERA", "ESTADO", "ESTUDIO", "EXAMEN", "EXPLOSION", "FABRICA", "FAMILIA", "FALLECIMIENTO", "FESTIVAL",
            "FLORECIMIENTO", "FRUTA", "FUTURO", "GALERIA", "GESTION", "GRUPO", "HORA", "HOSPITAL", "IMPACTO", "IMPUESTO",
            "INCIDENCIA", "INICIO", "INTERES", "JORNADA", "JUICIO", "LABOR", "LIDER", "LUGAR", "LLANTO", "MADRUGADA",
            "MANO", "METODO", "MUNDO", "NAVE", "NEGOCIO", "NOMBRE", "OBRA", "OBJETO", "OBLIGACION", "OPINION",
            "ORDEN", "OTROS", "PAGO", "PARQUE", "PASO", "PAUSA", "PERDIDA", "PERIODO", "PELICULA", "PELOQUERIA",
            "PENSAMIENTO", "PERIODICO", "PIEDRA", "PILOTO", "PLAN", "PLATAFORMA", "PLENITUD", "PROCESO", "PRODUCTO", "PROGRAMA",
            "PROVEEDOR", "PUESTA", "PUNTO", "QUIMICA", "RENDIMIENTO", "RIESGO", "RITMO", "RUTA", "SALDO", "SENAL",
            "SERVICIO", "SISTEMA", "SOLUCION", "SUBIDA", "SUPUESTO", "TAREA", "TECINCA", "TIEMPO", "TOQUE", "TOPICO",
            "TRANSACCION", "TRAJECTORIA", "TRAMITE", "VALOR", "VENTA", "VIAS", "VISIONARIO", "VISTA", "VOLUNTARIO", "ZONA",
            "AMIGOS", "ANALISIS", "APROVECHAMIENTO", "AUTORIZACION", "AUTORIDAD", "CAMPANA", "CONVENIO", "CRITICA", "CONTEXTO", "CORREO",
            "DELITO", "DESARROLLO", "DESAFIO", "DIRECCION", "DONACION", "ELITE", "ESCANDALO", "ESTOQUE", "EXAMEN", "EXPERIENCIA",
            "FACTURA", "GOL", "INNOVACION", "INSTALACION", "INSTRUCCION", "LEY", "META", "MEJORIA", "NAVEGACION", "PAGO",
            "PASAPORTE", "PUNTO", "SECTOR", "SILENCIO", "SITUACION", "RENDIMIENTO", "TERREMOTO", "TRANSPORTE", "VICTIMA", "VOLUNTARIO",
            "ZONA", "CUBO", "TORO", "BUHO", "FOTO", "ENLACE", "PEDAZO", "VALOR", "MANZANA", "CARRO",
            "PERRO", "OSO", "PESA", "SILLA", "MONTAÑA", "ALAS", "PUENTE", "CARRIL", "EJEMPLO", "COCHE",
            "CIELO", "NIEVE", "SOL", "JUEGO", "CONSUMO", "ROJO", "ROPA", "ACERO", "LUNA", "BOCA",
            "MANO", "OJO", "CANCHA", "CABEZA", "PIE", "ALIENTO", "VIAJE", "CARNET", "TELA", "SOMBRERO",
            "RUTA", "MOTO", "FURIA", "RAIZ", "HORIZONTE", "CUADRO", "TERRITORIO", "BAILE", "ARCO", "ROJO",
            "ESQUI", "MOVIMIENTO", "DIA", "NUMERO", "PALABRA", "LETRA", "CARPETA", "CELO", "PIN", "CARACTER",
            "CLIMA", "REY", "PESADO", "LLAVE", "PUERTA", "ALUMNO", "FLORES", "LUNA", "GRADUACION", "DIVERSION",
            "INSTRUCCION", "PAREJA", "CABALLO", "FESTIVAL", "PORTA", "LITRO", "ORACION", "ALQUILER", "MUNDO",
            "GUERRERO", "FIESTA", "EXAMEN", "POSICION", "LADRILLO", "MONEDA", "COBRO", "POSICION", "TORNEO",
            "CANON", "SOL", "MAR", "ENTRENADOR", "CIEGO", "MARZO", "ENERO", "ABRIL", "MAYO", "JUNIO",
            "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE", "SOLICITUD", "OFERTA", "PATRON",
            "LETRA", "ORIGEN", "ACEITE", "CAMBIO", "SERVICIO", "EMPLEO", "GRUPO", "ESTILO", "RUTA", "PROFESION",
            "GUARDIA", "PASTEL", "DESAYUNO", "DEBER", "LADRILLO", "ALUMNO", "CURIOSIDAD", "ACCION", "INTERES", "DESAFIO"
    };

    public static String getRandomAlias() {
        return ALIAS[(int) (Math.random() * ALIAS.length)];
    }



    public static Address generateRandomAddress() {
        String street = getRandomAlias();
        String number = String.valueOf((int) (Math.random() * 10000));
        String city = getRandomAlias();
        String state = getRandomAlias();
        String country = getRandomAlias();
        String postalCode = String.valueOf((int) (Math.random() * 10000));
        return new Address(street, number, city, state, country, postalCode);
    }


}
