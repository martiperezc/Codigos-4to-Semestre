% Subimos imagen
imgOriginal = imread('Sky.jpg'); % Tamaño original 3024x4032

% Para mejor visualizacion rotamos la imagen en 90 grados hacia la derecha
imgOriginal = rot90(imgOriginal, -1); 

imgGray = rgb2gray(imgOriginal); 
imgDouble = double(imgGray);

% Aplicación de SVD con tolerancia de 10^-3
[U1, S1, V1] = svdsketch(imgDouble, 1e-1); 

% Reconstruimos la matriz comprimida en 10^-3
imgComprimida = uint8(U1 * S1 * V1'); 

% Comentario agregado: compresión SVD sobre la imagen en color sin convertirla a escala de grises
imgDoubleColor = double(imgOriginal);

% Separamos los tres canales RGB para comprimirlos por separado
canalR = imgDoubleColor(:,:,1);
canalG = imgDoubleColor(:,:,2);
canalB = imgDoubleColor(:,:,3);

% Aplicación de SVD a cada canal de color
[UR, SR, VR] = svdsketch(canalR, 1e-1);
[UG, SG, VG] = svdsketch(canalG, 1e-1);
[UB, SB, VB] = svdsketch(canalB, 1e-1);

% Reconstrucción de la imagen RGB comprimida
imgComprimidaColor = cat(3, uint8(UR * SR * VR'), uint8(UG * SG * VG'), uint8(UB * SB * VB'));

% Cálculo de la eficiencia de compresión
[m, n] = size(imgGray); % Obtención de dimensiones originales
r = size(S1, 1); % Rango efectivo de la compresión
bytesOriginal = m * n; % Total de elementos originales
bytesComprimido = r * (m + n + 1); % Elementos necesarios en matrices U, S y V 
eficiencia = (bytesComprimido / bytesOriginal) * 100; % Cálculo del porcentaje final 

% Cálculo de la eficiencia para la imagen en color RGB
[mColor, nColor, ~] = size(imgOriginal);
bytesOriginalColor = mColor * nColor * 3; % Total de elementos originales en RGB
rR = size(SR, 1);
rG = size(SG, 1);
rB = size(SB, 1);
bytesComprimidoColor = (rR + rG + rB) * (mColor + nColor + 1); % Estimación teórica de almacenamiento comprimido
eficienciaColor = (bytesComprimidoColor / bytesOriginalColor) * 100; % Cálculo del porcentaje final

%Comparacion de resultados
figure(1);
subplot(1,2,1); imshow(imgComprimida); 
title(sprintf('Compresión SVD Escala de Grises (Rango %d) - Efic: %.2f%%', size(S1,1), eficiencia));

subplot(1,2,2); imshow(imgComprimidaColor); 
title(sprintf('Compresión SVD Color RGB - Efic: %.2f%%', eficienciaColor));

fprintf('La eficiencia de compresión en escala de grises es: %.2f%%\n', eficiencia); 
fprintf('La eficiencia de compresión en color RGB es: %.2f%%\n', eficienciaColor);

fprintf('Rango gris: %d\n', size(S1,1));
fprintf('Rango R: %d\n', size(SR,1));
fprintf('Rango G: %d\n', size(SG,1));
fprintf('Rango B: %d\n', size(SB,1));