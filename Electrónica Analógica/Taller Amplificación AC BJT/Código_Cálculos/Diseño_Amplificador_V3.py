import math

def diseñar_amplificador_ordenado(Av, RL, f, beta, Vinp, Zin_min, factor_Rc=2, factor_swamping=4):
    print("--- INICIANDO CÁLCULOS DE DISEÑO OPTIMIZADO ---")
    
    # 1. Cálculo de RC
    print("\n1) Calculo RC y Rac")
    K = Zin_min * (Av / (beta + 1))
    Rc_min = (K * RL) / (RL - K)
    Rc = Rc_min * factor_Rc
    Rac = (Rc * RL) / (Rc + RL)
    print(f"RC mínima teórica: {Rc_min:.2f} Ohms")
    print(f"RC (Tirar hacia el comercial superior): {Rc:.2f} Ohms")
    print(f"R_ac resultante: {Rac:.2f} Ohms")
    
    # 2. Requisitos de Señal y Corriente
    print("\n2) Calculo de Señal y Corriente ICQ Óptima")
    Vop = Av * Vinp
    print(f"Vop+ esperado: {Vop:.2f} V")
    
    Icq_swing = Vop / Rac
    Icq_distorsion = ((factor_swamping + 1) * 0.026 * Av) / Rac
    Icq = max(Icq_swing, Icq_distorsion) * 1.15
    print(f"Icq optimizada: {Icq*1000:.2f} mA")
    
    # 3. Resistencias Internas y RE1
    print("\n3) Calculo re y RE1")
    re = 0.026 / Icq
    RE1 = (Rac / Av) - re
    print(f"re interno: {re:.2f} Ohms")
    print(f"RE1 (Tirar hacia el comercial inferior): {RE1:.2f} Ohms")
    
    # 4. Centrado de VCEQ
    print("\n4) Centrado de VCEq")
    Vce_min = 1.0 # Margen dinámico seguro
    Vceq = (Icq * Rac) + Vce_min
    print(f"VCEq centrado en AC: {Vceq:.2f} V")
    
    # 5. Cálculo de VCC y RE2
    print("\n5) Calculo VCC y RE2")
    Ve = 1.5 # Voltaje estático de emisor optimizado
    Vcc = (Icq * Rc) + Vceq + Ve
    print(f"VCC Requerido: {Vcc:.2f} V")
    
    RE_total = Ve / Icq
    RE2 = max(0, RE_total - RE1)
    print(f"RE2 (Tirar hacia el comercial inferior): {RE2:.2f} Ohms")
    
    # 6. Red de Polarización
    print("\n6) Calculo Divisor de Base (I1, I2, RB1, RB2)")
    Ib = Icq / beta
    I2 = 10 * Ib
    I1 = I2 + Ib
    Vb = Ve + 0.7
    print(f"Corriente I1: {I1*1000:.2f} mA | Corriente I2: {I2*1000:.2f} mA")
    
    Rb2 = Vb / I2
    Rb1 = (Vcc - Vb) / I1
    print(f"RB1 (Tirar hacia el comercial superior): {Rb1:.2f} Ohms")
    print(f"RB2 (Tirar hacia el comercial inferior): {Rb2:.2f} Ohms")
    
    # 7. Capacitores
    print("\n7) Calculo de Capacitores")
    Z_int = beta * (re + RE1)
    Zin_real = 1 / ((1/Rb1) + (1/Rb2) + (1/Z_int))
    
    Cb_min = 1 / (2 * math.pi * f * Zin_real)
    Cc_min = 1 / (2 * math.pi * f * RL)
    Ce_min = 1 / (2 * math.pi * f * RE2) * 10
    
    print(f"C_base mínimo: {Cb_min*1e6:.2f} uF (Usar comercial superior)")
    print(f"C_colector mínimo: {Cc_min*1e6:.2f} uF (Usar comercial superior)")
    print(f"C_emisor mínimo: {Ce_min*1e6:.2f} uF (Usar comercial superior)")
    
    # 8. Lista de Materiales (BOM)
    print("\n==================================================")
    print("      RESUMEN DE MATERIALES PARA IMPLEMENTACIÓN   ")
    print("==================================================")
    print(f"[ ] Fuente de Alimentación : Ajustar a {Vcc:.2f} V")
    print(f"[ ] Transistor NPN         : beta aprox. {beta}")
    print("\n--- Resistencias (Buscar valor comercial cercano) ---")
    print(f"[ ] RC  : {Rc:.2f} Ohms")
    print(f"[ ] RB1 : {Rb1:.2f} Ohms")
    print(f"[ ] RB2 : {Rb2:.2f} Ohms")
    print(f"[ ] RE1 : {RE1:.2f} Ohms")
    print(f"[ ] RE2 : {RE2:.2f} Ohms")
    print(f"[ ] RL  : {RL} Ohms (Carga)")
    print("\n--- Capacitores (Electrolíticos, respeta polaridad) ---")
    print(f"[ ] Cb  : >= {Cb_min*1e6:.2f} uF")
    print(f"[ ] Cc  : >= {Cc_min*1e6:.2f} uF")
    print(f"[ ] Ce  : >= {Ce_min*1e6:.2f} uF")
    print("==================================================")

# Ejecutando con los parámetros del proyecto
diseñar_amplificador_ordenado(Av=30, RL=1000, f=500, beta=100, Vinp=0.05, Zin_min=1000)