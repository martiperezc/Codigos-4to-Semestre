import math

def diseñar_amplificador_emisor_comun(Av, RL, f, beta, Vinp, Zin_min, 
                                      factor_Rc=3, factor_Vrc=1.2, 
                                      factor_I2=15, Vseg=2.1, 
                                      factor_Rb2=1.5, factor_CE=10):
    
    print("--- INICIANDO CÁLCULOS DE DISEÑO ---")
    
    # 1. Cálculo de RC
    print("1) Calculo RC")
    K = Zin_min * (Av / (beta + 1))
    Rc_min = (K * RL) / (RL - K)
    print(f"RC minima {Rc_min:.2f} Ohms")
    Rc = Rc_min * factor_Rc # Aplicamos el multiplicador para alejarlo del límite
    print(f"RC (tirar hacia arriba): {Rc:.2f} Ohms")
    
    # 2. Cálculo de VRC
    print("2) Calculo Vop+, Vrc")
    Vop_plus = Av * Vinp
    print(f"Vop+:{Vop_plus:.2f} V")
    Vrc_min = Vop_plus * (Rc / ((RL * Rc) / (RL + Rc)))
    print(f"Vrc_min: {Vrc_min:.2f}V")
    Vrc = Vrc_min * factor_Vrc # Factor de seguridad (ej. 20% -> 1.2)
    print(f"VRC (subido): {Vrc:.2f} V")
    
    # 3. Corrientes e Impedancia interna del emisor (re)
    print("3) Calculo Corrientes")
    Ic = Vrc / Rc
    Ib = Ic / beta
    Ie = Ic # Aproximación estándar
    re = 0.026 / Ie # Usando 26mV a temperatura ambiente
    print(f"Ic: {Ic*1000:.2f} mA")
    print(f"Ib: {Ib*1000:.2f} mA")
    print(f"re: {re:.2f} Ohms")
    
    # 4. Cálculo de RE1
    print("4) Calculo RE1")
    Rc_RL_paralelo = (Rc * RL) / (Rc + RL)
    RE1 = (Rc_RL_paralelo / Av) - re
    print(f"RE1 (Tirar hacia abajo): {RE1:.2f} Ohms")
    
    # 5. Corrientes del divisor de voltaje
    print("5) Calculo I1,I2")
    I2 = factor_I2 * Ib
    I1 = I2 + Ib
    print(f"I1: {I1*1000:.2f} mA")
    print(f"I2: {I2*1000:.2f} mA")

    # 6. Voltaje Colector-Emisor (VCE)
    print("6) Calculo Vce")
    Vce = Vop_plus + Vseg + Vinp
    print(f"VCE: {Vce:.2f} V")
    
    # 7. Cálculo de RB1
    print("7) Calculo Vrb1, Rb1")
    V_Rb1 = Vrc + Vce - 0.7
    print(f"V_Rb1: {V_Rb1:.2f} V")
    Rb1 = V_Rb1 / I1
    print(f"RB1: (tirar hacia arriba) {Rb1:.2f} Ohms")
    
    # 8. Cálculo de RB2
    print("8) Calculo Rb2")
    Z_int = beta * (re + RE1)
    # 1/Rb2 < 1/Zin_min - 1/Rb1 - 1/Zint|
    termino_derecho = (1/Zin_min) - (1/Rb1) - (1/Z_int)

    Rb2_min = 1 / termino_derecho if termino_derecho > 0 else float('inf')
    print(f"RB2_min: {Rb2_min:.2f}")
    Rb2 = Rb2_min * factor_Rb2
    print(f"RB2: (Subido un 50%) {Rb2:.2f} Ohms")
    
    # 9. Cálculo de RE2
    print("9) Calculo RE2")

    V_Rb2 = I2 * Rb2
    print(f"Vrb2: {V_Rb2:.2f}")
    RE2 = ((V_Rb2 - 0.7) / Ie) - RE1
    print(f"RE2: (Tirar hacia abajo) {RE2:.2f} Ohms")
    
    # 10. Voltaje de Alimentación (VCC)
    print("10) Calculo VCC")
    Ve = Ie * (RE1 + RE2)
    Vcc = Vrc + Vce + Ve
    print(f"VCC minimo: {Vcc:.2f} V")
    
    # 11. Capacitores
    # Cb
    print("11) Calculo capactores")

    Zin_real = 1 / ((1/Rb1) + (1/Rb2) + (1/Z_int))
    Cb_min = 1 / (2 * math.pi * f * Zin_real)
    print(f"Cap base mínimo: {Cb_min*1e6:.2f} uF")
    
    # Cc
    Cc_min = 1 / (2 * math.pi * f * RL)
    print(f"Capa colector mínimo: {Cc_min*1e6:.2f} uF")

    # Ce
    Ce_min_1 = 1 / (2 * math.pi * f * RE2)
    Ce_min_2 = 1 / (2 * math.pi * f * (re + RE1))
    print(f"Ce_min: {Ce_min_2*1e6:.2f} uF")
    Ce_min = max(Ce_min_1, Ce_min_2) * factor_CE
    print(f"Capa emisor sugerido: {Ce_min*1e6:.2f} uF")

# Ejecutar con los datos del PDF
diseñar_amplificador_emisor_comun(Av=30, RL=1000, f=60, beta=100, Vinp=0.05, Zin_min=1000)