clc; clear; close all;

% 1. CALCULO SIMBOLICO DE LOS COEFICIENTES (LITERAL A)
syms t n
assume(n, 'integer');

disp('LITERAL A): EXPRESIONES ANALITICAS Y ARMONICO 1 (CAD 2)');
disp('Calculo simbolico general (para n >= 1):');

% La funcion es a trozos, por lo que dividimos la integral en sus dos dominios
% Amplitud de 3 de 0 a pi/2, y amplitud de 1 de pi/2 a 2pi
a0_sym = (1 / (2*pi)) * (int(3, t, 0, pi/2) + int(1, t, pi/2, 2*pi));
an_sym = simplify((1 / pi) * (int(3*cos(n*t), t, 0, pi/2) + int(1*cos(n*t), t, pi/2, 2*pi)));
bn_sym = simplify((1 / pi) * (int(3*sin(n*t), t, 0, pi/2) + int(1*sin(n*t), t, pi/2, 2*pi)));
cn_sym = simplify((1 / (2*pi)) * (int(3*exp(-1i*n*t), t, 0, pi/2) + int(1*exp(-1i*n*t), t, pi/2, 2*pi)));

disp(['a0 = ', char(a0_sym)]);
disp(['an = ', char(an_sym)]);
disp(['bn = ', char(bn_sym)]);
disp(['cn = ', char(cn_sym)]);
disp(' ');

disp('Calculo especifico para n = 1:');
% Mantenemos la estructura para evaluar el armonico 1 por separado
a1_sym = (1 / pi) * (int(3*cos(1*t), t, 0, pi/2) + int(1*cos(1*t), t, pi/2, 2*pi));
b1_sym = (1 / pi) * (int(3*sin(1*t), t, 0, pi/2) + int(1*sin(1*t), t, pi/2, 2*pi));
c1_sym = (1 / (2*pi)) * (int(3*exp(-1i*1*t), t, 0, pi/2) + int(1*exp(-1i*1*t), t, pi/2, 2*pi));

fprintf('a1 = %.4f\n', double(a1_sym));
fprintf('b1 = %.4f\n', double(b1_sym));
fprintf('c1 = %.4f %+.4fi\n\n', real(double(c1_sym)), imag(double(c1_sym)));

% 2. CONVERSION DE SIMBOLICO A NUMERICO PARA OPTIMIZACION
N_max = 20; % Armonico maximo requerido para este ejercicio (CAD 2)
A = zeros(1, N_max);
B = zeros(1, N_max);
C_pos = zeros(1, N_max);

A(1) = double(a1_sym);
B(1) = double(b1_sym);
C_pos(1) = double(c1_sym);

% Utilizamos subs para precalcular todos los coeficientes numericos
for k = 2:N_max
    A(k) = double(subs(an_sym, n, k));
    B(k) = double(subs(bn_sym, n, k));
    C_pos(k) = double(subs(cn_sym, n, k));
end

% 3. GENERACION DE GRAFICAS UTILIZANDO LOS DATOS CALCULADOS (LITERAL B)
t_num = linspace(0, 4*pi, 1000); 
f_ideal = 1 + 2*(mod(t_num, 2*pi) < pi/2);
armonicos = [5, 10, 15, 20];
a0_num = double(a0_sym);

figure('Name', 'Series de Fourier - Motor Simbolico (CAD 2)');

for idx = 1:4
    N = armonicos(idx);
    
    f_trig = a0_num * ones(size(t_num)); 
    f_exp = a0_num * ones(size(t_num));  
    
    for k = 1:N
        % Extraccion directa desde la memoria precalculada
        f_trig = f_trig + A(k) * cos(k * t_num) + B(k) * sin(k * t_num);
        
        C_neg = conj(C_pos(k)); 
        f_exp = f_exp + C_pos(k) * exp(1i * k * t_num) + C_neg * exp(-1i * k * t_num);
    end
    f_exp = real(f_exp); 
    
    subplot(4, 2, 2*idx - 1);
    plot(t_num, f_ideal, 'k', t_num, f_trig, 'r', 'LineWidth', 1.2);
    grid on; title(['Trig. N = ', num2str(N)]);
    
    subplot(4, 2, 2*idx);
    plot(t_num, f_ideal, 'k', t_num, f_exp, 'b', 'LineWidth', 1.2);
    grid on; title(['Exp. N = ', num2str(N)]);
end