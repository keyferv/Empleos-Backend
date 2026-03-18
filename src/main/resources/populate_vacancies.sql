-- Script para poblar la base de datos con 30 vacantes de prueba
-- Ejecutar este script en su gestor de base de datos (PostgreSQL)

-- 1. Asegurar categorías básicas
INSERT INTO categoria (category_name, description) VALUES 
('Ingeniería', 'Software, Civil, Mecánica y más'),
('Diseño', 'UX/UI, Gráfico, Industrial'),
('Marketing', 'Digital, SEO, Community Manager'),
('Ventas', 'Comercial, Account Manager'),
('Administración', 'Recursos Humanos, Contabilidad'),
('Salud', 'Medicina, Enfermería, Terapia')
ON CONFLICT DO NOTHING;

-- 2. Función temporal para insertar vacantes masivamente
DO $$
DECLARE
    i INT := 1;
    v_detalle_id INT;
    v_vacante_id INT;
    v_estado_id INT;
    v_cat_ids INT[];
    v_titles TEXT[] := ARRAY[
        'Desarrollador Java Senior', 'Diseñador UX/UI', 'Analista de Datos', 
        'Gerente de Proyectos', 'Contador General', 'Especialista SEO', 
        'Arquitecto de Software', 'Médico General', 'Vendedor de Software',
        'Asistente Administrativo', 'Ingenior DevOps', 'Consultor SAP',
        'Redactor Creativo', 'Científico de Datos', 'Product Owner'
    ];
    v_locations TEXT[] := ARRAY['Quito, Ecuador', 'Guayaquil, Ecuador', 'Remoto', 'Cuenca, Ecuador', 'Híbrido'];
BEGIN
    -- Obtener un ID de estado activo
    SELECT id INTO v_estado_id FROM estado WHERE type = 'ACTIVO' LIMIT 1;
    
    -- Si no existe el estado, crearlo
    IF v_estado_id IS NULL THEN
        INSERT INTO estado (type) VALUES ('ACTIVO') RETURNING id INTO v_estado_id;
    END IF;

    -- Obtener todos los IDs de categorías
    SELECT array_agg(id) INTO v_cat_ids FROM categoria;

    -- Insertar 30 registros
    FOR i IN 1..30 LOOP
        -- A. Crear el detalle de la oferta
        INSERT INTO oferta_detalle (requirements, responsibilities, benefits, location, start_hour, end_hour, process_selection, salary)
        VALUES (
            'Requisitos para la posición ' || i || ': Experiencia previa, proactividad y trabajo en equipo.',
            'Responsabilidades: Liderar proyectos, reportar avances y cumplir KPIs.',
            'Beneficios: Seguro médico, bonos por desempeño, plan de carrera.',
            v_locations[1 + (i % 5)],
            '09:00:00',
            '18:00:00',
            'Entrevista técnica y psicométrica',
            (2000 + (i * 100))
        ) RETURNING id INTO v_detalle_id;

        -- B. Crear la vacante vinculada al detalle
        INSERT INTO vacantes (offer_name, description, date, salary, featured, image, estado_id, id_detalle)
        VALUES (
            v_titles[1 + (i % 15)] || ' #' || i,
            'Esta es una excelente oportunidad para crecer profesionalmente en una empresa líder del sector. Buscamos talento para la posición número ' || i,
            CURRENT_DATE - (i || ' days')::interval,
            (2000 + (i * 100)),
            (i % 3 = 0), -- Algunas destacadas
            'no-image.png',
            v_estado_id,
            v_detalle_id
        ) RETURNING id INTO v_vacante_id;

        -- C. Vincular a una categoría aleatoria
        INSERT INTO vacante_categoria (vacante_id, categoria_id)
        VALUES (v_vacante_id, v_cat_ids[1 + (i % array_length(v_cat_ids, 1))]);
        
    END LOOP;
END $$;
