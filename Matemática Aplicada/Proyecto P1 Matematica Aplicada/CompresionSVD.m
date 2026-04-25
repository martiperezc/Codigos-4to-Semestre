% Subimos imagen
imgOriginal = imread('Sky.jpg'); % Tamaño original 3024x4032
% Para mejor visualizacion rotamos la imagen en 90 grados hacia la derecha
imgOriginal = rot90(imgOriginal, -1); 
imgGray = rgb2gray(imgOriginal); 
imgDouble = double(imgGray);
% Aplicación de SVD con tolerancia de 10^-1
[U1, S1, V1] = svdsketch(imgDouble, 1e-1); 
% Reconstruimos la matriz comprimida en 10^-1
imgComprimida = uint8(U1 * S1 * V1'); 

% Cálculo de la eficiencia de compresión
[m, n] = size(imgGray); % Obtención de dimensiones originales
r = size(S1, 1); % Rango efectivo de la compresión
bytesOriginal = m * n; % Total de elementos originales
bytesComprimido = r * (m + n + 1); % Elementos necesarios en matrices U, S y V 
eficiencia = (bytesComprimido / bytesOriginal) * 100; % Cálculo del porcentaje final 

%Comparacion de resultados
figure(1);
subplot(1,2,1); imshow(imgGray); title('Original');
subplot(1,2,2); imshow(imgComprimida); 
title(sprintf('Compresión SVD (Rango %d) - Efic: %.2f%%', size(S1,1), eficiencia));
fprintf('La eficiencia de compresión es: %.2f%%\n', eficiencia); 