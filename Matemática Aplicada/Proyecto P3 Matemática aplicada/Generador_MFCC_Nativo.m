% Extracción de características MFCC (13 coeficientes)
clear; clc; close all;

fs_proyecto = 16000;

nombres_archivos = { ...
    'sol1_Jair.wav', 'sol1_Majo.wav', 'sol1_Martin.wav', ...
    'sol2_Jair.wav', 'sol2_Majo.wav', 'sol2_Martin.wav', ...
    'sol3_Jair.wav', 'sol3_Majo.wav', 'sol3_Martin.wav', ...
    'flor1_Jair.wav', 'flor1_Majo.wav', 'flor1_Martin.wav', ...
    'flor2_Jair.wav', 'flor2_Majo.wav', 'flor2_Martin.wav', ...
    'flor3_Jair.wav', 'flor3_Majo.wav', 'flor3_Martin.wav', ...
    'agua1_Jair.wav', 'agua1_Majo.wav', 'agua1_Martin.wav', ...
    'agua2_Jair.wav', 'agua2_Majo.wav', 'agua2_Martin.wav', ...
    'agua3_Jair.wav', 'agua3_Majo.wav', 'agua3_Martin.wav' ...
};

num_audios = length(nombres_archivos);
matriz_MFCC_pura = zeros(num_audios, 13);

for i = 1:num_audios
    try
        [audioIn, fs_real] = audioread(nombres_archivos{i});
        
        % Acondicionamiento a mono
        if size(audioIn, 2) > 1
            audioIn = audioIn(:, 1); 
        end 
        
        if fs_real ~= fs_proyecto
            warning('El archivo %s tiene %d Hz (Esperado: %d Hz).', nombres_archivos{i}, fs_real, fs_proyecto);
        end
        
        % Detección de actividad de voz (VAD) basada en energía
        energia = audioIn.^2;
        umbral = max(energia) * 0.05; 
        indices_voz = find(energia > umbral);
        
        if ~isempty(indices_voz)
            audioIn = audioIn(indices_voz(1):indices_voz(end));
        end
        
        % Extracción de coeficientes
        [coeffs, ~, ~] = mfcc(audioIn, fs_real, 'NumCoeffs', 13);
        
        % Promedio temporal (descartando la columna 1 de energía)
        matriz_MFCC_pura(i, :) = mean(coeffs(:, 2:end), 1);
        
    catch 
        warning('Error al procesar el archivo: %s', nombres_archivos{i});
    end
end

save('matriz_mfcc_corregida.mat', 'matriz_MFCC_pura');
disp('Matriz de características guardada exitosamente.');