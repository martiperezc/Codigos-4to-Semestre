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


%Comparamos resultados
figure(1);
subplot(1,2,1); imshow(imgGray); title('Original');
subplot(1,2,2); imshow(imgComprimida); 
title(sprintf('Compresión SVD (Rango %d)', size(S1,1)));

%Imprimimos en cola las matrices que conforman la imagen original, tras su
%compresion
disp('Dimensiones de matrices comprimidas:');
whos U1 S1 V1
