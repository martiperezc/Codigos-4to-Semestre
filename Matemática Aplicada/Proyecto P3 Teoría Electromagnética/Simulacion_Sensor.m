% Simulación de sensor híbrido (capacitivo e inductivo)
clear; clc; close all;

%% Parámetros generales y rangos de prueba
V_nom = 5;                  % Voltaje aplicado al capacitor [V]
I_nom = 1;                  % Corriente en la bobina [A]
er_rango = linspace(1, 80, 100); 
I_rango = linspace(0, 5, 100);   

% Constantes físicas
e0 = 8.854e-12;             
mu0 = 4 * pi * 1e-7;        
mu_r = 1;                   

% Geometría del sensor
a = 1e-3;                   % Radio interno capacitor [m]
b = 3e-3;                   % Radio externo capacitor [m]
L_c = 0.1;                  % Longitud capacitor [m]
R_i = 5e-3;                 % Radio espira bobina [m]
l_i = 0.05;                 % Longitud bobina [m]
N = 200;                    % Vueltas
A_i = pi * R_i^2;           

%% Modelado Matemático

% Módulo capacitivo
C_teo = (2 * pi * (e0 .* er_rango) * L_c) ./ log(b/a);
U_e_teo = 0.5 .* C_teo .* V_nom^2;

C_parasita = 0.8e-12; 
ruido_C = (0.015 .* C_teo) .* randn(size(er_rango));
C_sim = C_teo + C_parasita + ruido_C;
U_e_sim = 0.5 .* C_sim .* V_nom^2;

% Módulo inductivo
B_teo = (mu0 * mu_r * N .* I_rango) ./ l_i;
L_ind_teo = (mu0 * mu_r * N^2 * A_i) / l_i; 
U_m_teo = 0.5 .* L_ind_teo .* I_rango.^2;

factor_fuga = 0.95; 
B_sim = (B_teo .* factor_fuga) + (1e-4 .* randn(size(I_rango)));
L_ind_sim = L_ind_teo * factor_fuga;
U_m_sim = 0.5 .* L_ind_sim .* I_rango.^2 + (1e-6 .* randn(size(I_rango)));

%% Resultados en consola (valores nominales)
C_teo_nom = (2 * pi * e0 * L_c) / log(b/a);
C_sim_nom = C_teo_nom + C_parasita;
B_teo_nom = (mu0 * mu_r * N * I_nom) / l_i;
B_sim_nom = B_teo_nom * factor_fuga;

fprintf('\nResultados para er = 1 (Aire), V = %.1f V, I = %.1f A\n\n', V_nom, I_nom);

fprintf('--- Capacitor ---\n');
fprintf('Capacitancia: %.3f pF (Teórica) | %.3f pF (Simulada)\n', C_teo_nom * 1e12, C_sim_nom * 1e12);
fprintf('Energía:      %.3f pJ (Teórica) | %.3f pJ (Simulada)\n\n', ...
        (0.5 * C_teo_nom * V_nom^2) * 1e12, (0.5 * C_sim_nom * V_nom^2) * 1e12);

fprintf('--- Bobina ---\n');
fprintf('Campo Magnético: %.3f mT (Teórico) | %.3f mT (Simulado)\n', B_teo_nom * 1e3, B_sim_nom * 1e3);
fprintf('Inductancia:     %.3f uH (Teórica) | %.3f uH (Simulada)\n', L_ind_teo * 1e6, L_ind_sim * 1e6);
fprintf('Energía:         %.3f uJ (Teórica) | %.3f uJ (Simulada)\n\n', ...
        (0.5 * L_ind_teo * I_nom^2) * 1e6, (0.5 * L_ind_sim * I_nom^2) * 1e6);

%% Gráficas
figure('Name', 'Análisis del Sensor Híbrido', 'Position', [50, 50, 1000, 700]);

subplot(2,2,1);
plot(er_rango, C_teo * 1e12, 'b-', 'LineWidth', 1.5); hold on;
scatter(er_rango, C_sim * 1e12, 15, 'r', 'filled', 'MarkerEdgeAlpha', 0.6);
title('Capacitancia vs. \epsilon_r');
xlabel('\epsilon_r'); ylabel('Capacitancia (pF)');
legend('Teórico', 'Simulado', 'Location', 'best'); grid on; xlim([1 80]);

subplot(2,2,2);
plot(er_rango, U_e_teo * 1e12, 'k-', 'LineWidth', 1.5); hold on;
scatter(er_rango, U_e_sim * 1e12, 15, 'm', 'filled', 'MarkerEdgeAlpha', 0.6);
title('Energía Eléctrica vs. \epsilon_r');
xlabel('\epsilon_r'); ylabel('Energía (pJ)');
legend('Teórico', 'Simulado', 'Location', 'best'); grid on; xlim([1 10]); 

subplot(2,2,3);
plot(I_rango, B_teo * 1e3, 'b-', 'LineWidth', 1.5); hold on;
scatter(I_rango, B_sim * 1e3, 15, 'g', 'filled', 'MarkerEdgeAlpha', 0.6);
title('Campo Magnético vs. Corriente');
xlabel('Corriente (A)'); ylabel('Campo Magnético (mT)');
legend('Teórico', 'Simulado', 'Location', 'best'); grid on; xlim([0 5]);

subplot(2,2,4);
plot(I_rango, U_m_teo * 1e6, 'k-', 'LineWidth', 1.5); hold on;
scatter(I_rango, U_m_sim * 1e6, 15, 'c', 'filled', 'MarkerEdgeAlpha', 0.6);
title('Energía Magnética vs. Corriente');
xlabel('Corriente (A)'); ylabel('Energía (\muJ)');
legend('Teórico', 'Simulado', 'Location', 'best'); grid on; xlim([0 5]);