clc; clear; close all;
% 1. CALCULO SIMBOLICO DE LOS COEFICIENTES (LITERAL A)
syms x n
assume(n, 'integer'); 
f_sym = sin(x); 

disp('LITERAL A): EXPRESIONES ANALITICAS Y ARMONICO 1');
disp('Calculo simbolico general (para n > 1):');

% MATLAB resuelve las integrales algebraicamente
a0_sym = (1 / (2*pi)) * int(f_sym, x, 0, pi);
an_sym = simplify((1 / pi) * int(f_sym * cos(n*x), x, 0, pi));
bn_sym = simplify((1 / pi) * int(f_sym * sin(n*x), x, 0, pi));
cn_sym = simplify((1 / (2*pi)) * int(f_sym * exp(-1i*n*x), x, 0, pi));

disp(['a0 = ', char(a0_sym)]);
disp(['an = ', char(an_sym)]);
disp(['bn = ', char(bn_sym)]);
disp(['cn = ', char(cn_sym)]);
disp(' ');

disp('Calculo especifico para la singularidad n = 1:');
% Se integra sustituyendo directamente n=1 para evitar divisiones por cero
a1_sym = (1 / pi) * int(f_sym * cos(1*x), x, 0, pi);
b1_sym = (1 / pi) * int(f_sym * sin(1*x), x, 0, pi);
c1_sym = (1 / (2*pi)) * int(f_sym * exp(-1i*1*x), x, 0, pi);

fprintf('a1 = %.4f\n', double(a1_sym));
fprintf('b1 = %.4f\n', double(b1_sym));
fprintf('c1 = %.4f %+.4fi\n\n', real(double(c1_sym)), imag(double(c1_sym)));

% 2. CONVERSION DE SIMBOLICO A NUMERICO PARA OPTIMIZACION
N_max = 50; % Se precalculan los coeficientes hasta el armonico maximo solicitado
A = zeros(1, N_max);
B = zeros(1, N_max);
C_pos = zeros(1, N_max);

% Asignacion manual del primer armonico
A(1) = double(a1_sym);
B(1) = double(b1_sym);
C_pos(1) = double(c1_sym);

% El comando 'subs' reemplaza la 'n' por el valor de 'k' en la ecuacion simbolica
for k = 2:N_max
    A(k) = double(subs(an_sym, n, k));
    B(k) = double(subs(bn_sym, n, k));
    C_pos(k) = double(subs(cn_sym, n, k));
end

% 3. GENERACION DE GRAFICAS UTILIZANDO LOS DATOS CALCULADOS (LITERAL B)
t = linspace(-2*pi, 2*pi, 1000); 
f_ideal = max(0, sin(t)); 
armonicos = [5, 10, 25, 50];
a0_num = double(a0_sym);

figure('Name', 'Series de Fourier - Motor Simbolico');

for idx = 1:4
    N = armonicos(idx);
    
    f_trig = a0_num * ones(size(t)); 
    f_exp = a0_num * ones(size(t));  
    
    for k = 1:N
        % El bucle for extrae los coeficientes directamente de los arreglos calculados por MATLAB
        f_trig = f_trig + A(k) * cos(k * t) + B(k) * sin(k * t);
        
        C_neg = conj(C_pos(k)); 
        f_exp = f_exp + C_pos(k) * exp(1i * k * t) + C_neg * exp(-1i * k * t);
    end
    f_exp = real(f_exp); 
    
    subplot(4, 2, 2*idx - 1);
    plot(t, f_ideal, 'k', t, f_trig, 'r');
    grid on; title(['Trig. N = ', num2str(N)]);
    
    subplot(4, 2, 2*idx);
    plot(t, f_ideal, 'k', t, f_exp, 'b');
    grid on; title(['Exp. N = ', num2str(N)]);
end