% Subimos imagen
imgOriginal = imread('Sky.jpg'); % Debe ser > 640x480 

% Para mejor visualizacion rotamos la imagen en 90 grados hacia la derecha
imgOriginal = rot90(imgOriginal, -1); 

imgGray = rgb2gray(imgOriginal); 
imgDouble = double(imgGray);

% Aplicación de SVD con tolerancia de 10^-3
[U1, S1, V1] = svdsketch(imgDouble, 1e-1); 

% Reconstruimos la matriz comprimida en 10^-3
imgComprimida = uint8(U1 * S1 * V1'); 

% Ingreso de nuevos rangos
r1 = 25;
r2 = 5;

r1 = min(r1, size(S1,1));
r2 = min(r2, size(S1,1));

U_r1 = U1(:,1:r1);
S_r1 = S1(1:r1,1:r1);
V_r1 = V1(:,1:r1);
img_r1 = uint8(U_r1 * S_r1 * V_r1');

U_r2 = U1(:,1:r2);
S_r2 = S1(1:r2,1:r2);
V_r2 = V1(:,1:r2);
img_r2 = uint8(U_r2 * S_r2 * V_r2');

%Imprimimos resultados
figure(1);
subplot(2,2,1); imshow(imgGray); title('Original');
subplot(2,2,2); imshow(imgComprimida); 
title(sprintf('Compresión SVD (Rango %d)', size(S1,1)));

subplot(2,2,3); imshow(img_r1);
title(sprintf('r = %d', r1));

subplot(2,2,4); imshow(img_r2);
title(sprintf('r = %d', r2));

%Matrices que conforman la imagen original, tras su compresion
disp('Dimensiones de matrices comprimidas:');
whos U1 S1 V1