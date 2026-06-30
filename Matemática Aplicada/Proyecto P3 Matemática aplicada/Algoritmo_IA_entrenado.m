% Clasificación de voz mediante MFCC y SVM

%% 1. Carga de datos
datos = load('matriz_mfcc_corregida.mat');
nombres_variables = fieldnames(datos);
matriz_MFCC = datos.(nombres_variables{1});

[num_audios, ~] = size(matriz_MFCC);

%% 2. Definición de etiquetas
etiquetas_texto = [repmat({'Sol'}, 9, 1); ...
                   repmat({'Flor'}, 9, 1); ...
                   repmat({'Agua'}, 9, 1)];
etiquetas = categorical(etiquetas_texto);

%% 3. Entrenamiento del modelo SVM
% Plantilla ECOC con estandarización Z-score
plantilla = templateSVM('Standardize', true);
modelo_SVM = fitcecoc(matriz_MFCC, etiquetas, 'Learners', plantilla);

%% 4. Evaluación 
predicciones = predict(modelo_SVM, matriz_MFCC);
precision = sum(predicciones == etiquetas) / num_audios * 100;
fprintf('Precisión base del modelo: %.2f%%\n', precision);

figure;
confusionchart(etiquetas, predicciones);
title('Matriz de Confusión - Clasificador SVM');

%% 5. Clasificación de nuevos audios
[archivo, ruta] = uigetfile('*.wav', 'Seleccione un audio');

if isequal(archivo, 0)
    disp('Operación cancelada.');
else
    ruta_completa = fullfile(ruta, archivo);
    [audio_nuevo, fs] = audioread(ruta_completa);
    
    if size(audio_nuevo, 2) > 1 
        audio_nuevo = audio_nuevo(:, 1); 
    end
    
    % VAD por energía
    energia = audio_nuevo.^2;
    umbral = max(energia) * 0.05; 
    indices_voz = find(energia > umbral);
    
    if ~isempty(indices_voz)
        audio_nuevo = audio_nuevo(indices_voz(1):indices_voz(end));
    end
    
    % Extracción y predicción
    [coeffs_nuevo, ~, ~] = mfcc(audio_nuevo, fs, 'NumCoeffs', 13);
    mfcc_promedio_nuevo = mean(coeffs_nuevo(:, 2:end), 1); 
    
    prediccion_final = predict(modelo_SVM, mfcc_promedio_nuevo);
    fprintf('Predicción: %s\n', char(prediccion_final));
end